import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsSectorMaster } from './rns-sector-master.model';
import { RnsSectorMasterService } from './rns-sector-master.service';

@Injectable()
export class RnsSectorMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsSectorMasterService: RnsSectorMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsSectorMasterService.find(id).subscribe(rnsSectorMaster => {
                    this.ngbModalRef = this.rnsSectorMasterModalRef(component, rnsSectorMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsSectorMasterModalRef(component, new RnsSectorMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsSectorMasterModalRef(component: Component, rnsSectorMaster: RnsSectorMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsSectorMaster = rnsSectorMaster;
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
