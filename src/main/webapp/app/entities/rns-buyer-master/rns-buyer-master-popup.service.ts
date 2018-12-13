import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsBuyerMaster } from './rns-buyer-master.model';
import { RnsBuyerMasterService } from './rns-buyer-master.service';

@Injectable()
export class RnsBuyerMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsBuyerMasterService: RnsBuyerMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsBuyerMasterService.find(id).subscribe(rnsBuyerMaster => {
                    this.ngbModalRef = this.rnsBuyerMasterModalRef(component, rnsBuyerMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsBuyerMasterModalRef(component, new RnsBuyerMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsBuyerMasterModalRef(component: Component, rnsBuyerMaster: RnsBuyerMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsBuyerMaster = rnsBuyerMaster;
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
