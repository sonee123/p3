import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsVendorRemark } from './rns-vendor-remark.model';
import { RnsVendorRemarkService } from './rns-vendor-remark.service';

@Injectable()
export class RnsVendorRemarkPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsVendorRemarkService: RnsVendorRemarkService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsVendorRemarkService.find(id).subscribe(rnsVendorRemark => {
                    this.ngbModalRef = this.rnsVendorRemarkModalRef(component, rnsVendorRemark.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsVendorRemarkModalRef(component, new RnsVendorRemark());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsVendorRemarkModalRef(component: Component, rnsVendorRemark: RnsVendorRemark): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsVendorRemark = rnsVendorRemark;
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
