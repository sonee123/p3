import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsVendorMaster } from './rns-vendor-master.model';
import { RnsVendorMasterService } from './rns-vendor-master.service';

@Injectable()
export class RnsVendorMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsVendorMasterService: RnsVendorMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsVendorMasterService.find(id).subscribe(rnsVendorMaster => {
                    this.ngbModalRef = this.rnsVendorMasterModalRef(component, rnsVendorMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsVendorMasterModalRef(component, new RnsVendorMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    type(component: Component, id?: string): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }
            const rnsVendorMaster = new RnsVendorMaster();
            rnsVendorMaster.vendorCode = id;
            this.ngbModalRef = this.rnsVendorMasterModalRef(component, rnsVendorMaster);
            resolve(this.ngbModalRef);
        });
    }

    rnsVendorMasterModalRef(component: Component, rnsVendorMaster: RnsVendorMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static', windowClass: 'modal-xxl' });
        modalRef.componentInstance.rnsVendorMaster = rnsVendorMaster;
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
