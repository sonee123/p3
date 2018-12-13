import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsQuotationDealTerms } from './rns-quotation-deal-terms.model';
import { RnsQuotationDealTermsService } from './rns-quotation-deal-terms.service';

@Injectable()
export class RnsQuotationDealTermsPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsQuotationDealTerms: RnsQuotationDealTerms;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private rnsQuotationDealTermsService: RnsQuotationDealTermsService
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
                this.rnsQuotationDealTermsService.find(id).subscribe(rnsQuotationDealTerms => {
                    this.rnsQuotationDealTerms = rnsQuotationDealTerms.body;
                    if (this.rnsQuotationDealTerms.completionBy) {
                        this.rnsQuotationDealTerms.completionBy = {
                            year: this.rnsQuotationDealTerms.completionBy.getFullYear(),
                            month: this.rnsQuotationDealTerms.completionBy.getMonth() + 1,
                            day: this.rnsQuotationDealTerms.completionBy.getDate()
                        };
                    }
                    this.ngbModalRef = this.rnsQuotationDealTermsModalRef(component, this.rnsQuotationDealTerms);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationDealTermsModalRef(component, new RnsQuotationDealTerms());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsQuotationDealTermsModalRef(component: Component, rnsQuotationDealTerms: RnsQuotationDealTerms): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationDealTerms = rnsQuotationDealTerms;
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
