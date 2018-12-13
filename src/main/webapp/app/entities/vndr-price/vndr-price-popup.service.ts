import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VndrPrice } from './vndr-price.model';
import { VndrPriceService } from './vndr-price.service';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';

@Injectable()
export class VndrPricePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private vndrPriceService: VndrPriceService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService
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
                this.vndrPriceService.find(id).subscribe(vndrPrice => {
                    this.ngbModalRef = this.vndrPriceModalRef(component, vndrPrice.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vndrPriceModalRef(component, new VndrPrice());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    surrogate(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationVendorsService.surrogate(id).subscribe(rnsQuotationVendors => {
                    this.ngbModalRef = this.rnsQuotationVariantModalRef(component, rnsQuotationVendors.body);
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    vndrPriceModalRef(component: Component, vndrPrice: VndrPrice): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.vndrPrice = vndrPrice;
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

    rnsQuotationVariantModalRef(component: Component, rnsQuotationVendors: RnsQuotationVendors): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.variant = rnsQuotationVendors.fullDetails;
        modalRef.componentInstance.vendor = rnsQuotationVendors;
        modalRef.componentInstance.rnsQuotation = rnsQuotationVendors.fullDetails.quotationFullDetails;
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
