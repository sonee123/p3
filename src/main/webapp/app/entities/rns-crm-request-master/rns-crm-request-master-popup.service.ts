import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsCrmRequestMaster } from './rns-crm-request-master.model';
import { RnsCrmRequestMasterService } from './rns-crm-request-master.service';

@Injectable()
export class RnsCrmRequestMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsCrmRequestMaster: RnsCrmRequestMaster;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsCrmRequestMasterService: RnsCrmRequestMasterService
    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsCrmRequestMasterService.find(id).subscribe(rnsCrmRequestMaster => {
                    this.rnsCrmRequestMaster = rnsCrmRequestMaster.body;
                    this.ngbModalRef = this.rnsCrmRequestMasterModalRef(component, this.rnsCrmRequestMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsCrmRequestMasterModalRef(component, new RnsCrmRequestMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsCrmRequestMasterModalRef(component: Component, rnsCrmRequestMaster: RnsCrmRequestMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static', windowClass: 'modal-xxl' });
        modalRef.componentInstance.rnsCrmRequestMaster = rnsCrmRequestMaster;
        modalRef.result.then(
            result => {
                this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
                this.ngbModalRef = null;
            },
            reason => {
                this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
                this.ngbModalRef = null;
            }
        );
        return modalRef;
    }
}
