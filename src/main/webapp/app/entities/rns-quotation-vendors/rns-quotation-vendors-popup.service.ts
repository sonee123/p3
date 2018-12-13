import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsQuotationVendors } from './rns-quotation-vendors.model';
import { RnsQuotationVendorsService } from './rns-quotation-vendors.service';

@Injectable()
export class RnsQuotationVendorsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsQuotationVendorsService: RnsQuotationVendorsService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationVendorsService.find(id).subscribe(rnsQuotationVendors => {
                    this.ngbModalRef = this.rnsQuotationVendorsModalRef(component, rnsQuotationVendors.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationVendorsModalRef(component, new RnsQuotationVendors());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsQuotationVendorsModalRef(component: Component, rnsQuotationVendors: RnsQuotationVendors): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationVendors = rnsQuotationVendors;
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
