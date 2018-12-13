import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsQuotationVariant } from './rns-quotation-variant.model';
import { RnsQuotationVariantService } from './rns-quotation-variant.service';

@Injectable()
export class RnsQuotationVariantPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsQuotationVariant: RnsQuotationVariant;

    constructor(private modalService: NgbModal, private router: Router, private rnsQuotationVariantService: RnsQuotationVariantService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationVariantService.find(id).subscribe(rnsQuotationVariant => {
                    this.rnsQuotationVariant = rnsQuotationVariant.body;
                    if (this.rnsQuotationVariant.dealtermCompletionBy) {
                        this.rnsQuotationVariant.dealtermCompletionBy = {
                            year: this.rnsQuotationVariant.dealtermCompletionBy.getFullYear(),
                            month: this.rnsQuotationVariant.dealtermCompletionBy.getMonth() + 1,
                            day: this.rnsQuotationVariant.dealtermCompletionBy.getDate()
                        };
                    }
                    this.ngbModalRef = this.rnsQuotationVariantModalRef(component, this.rnsQuotationVariant);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationVariantModalRef(component, new RnsQuotationVariant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsQuotationVariantModalRef(component: Component, rnsQuotationVariant: RnsQuotationVariant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationVariant = rnsQuotationVariant;
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
