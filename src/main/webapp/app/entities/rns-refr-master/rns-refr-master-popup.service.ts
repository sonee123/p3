import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsRefrMaster } from './rns-refr-master.model';
import { RnsRefrMasterService } from './rns-refr-master.service';

@Injectable()
export class RnsRefrMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsRefrMaster: RnsRefrMaster;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsRefrMasterService: RnsRefrMasterService
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
                this.rnsRefrMasterService.find(id).subscribe(rnsRefrMaster => {
                    this.rnsRefrMaster = rnsRefrMaster.body;
                    this.rnsRefrMaster.createdDate = this.datePipe.transform(this.rnsRefrMaster.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsRefrMasterModalRef(component, this.rnsRefrMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsRefrMasterModalRef(component, new RnsRefrMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsRefrMasterModalRef(component: Component, rnsRefrMaster: RnsRefrMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsRefrMaster = rnsRefrMaster;
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
