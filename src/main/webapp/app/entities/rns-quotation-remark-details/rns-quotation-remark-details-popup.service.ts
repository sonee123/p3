import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { RnsQuotationRemarkDetailsService } from './rns-quotation-remark-details.service';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';

@Injectable()
export class RnsQuotationRemarkDetailsPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsQuotationRemarkDetails: RnsQuotationRemarkDetails;
    private rnsQuotation: RnsQuotation;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationRemarkDetailsService: RnsQuotationRemarkDetailsService
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
                this.rnsQuotationRemarkDetailsService.find(id).subscribe(rnsQuotationRemarkDetails => {
                    this.rnsQuotationRemarkDetails = rnsQuotationRemarkDetails.body;
                    if (this.rnsQuotationRemarkDetails.authDate) {
                        this.rnsQuotationRemarkDetails.authDate = {
                            year: this.rnsQuotationRemarkDetails.authDate.getFullYear(),
                            month: this.rnsQuotationRemarkDetails.authDate.getMonth() + 1,
                            day: this.rnsQuotationRemarkDetails.authDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.rnsQuotationRemarkDetailsModalRef(component, this.rnsQuotationRemarkDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationRemarkDetailsModalRef(component, new RnsQuotationRemarkDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    popup(component: Component, id?: number | any, type?: any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationService.find(id).subscribe(rnsQuotation => {
                    this.rnsQuotation = rnsQuotation.body;
                });
                this.rnsQuotationRemarkDetailsService.popup(id, type).subscribe(rnsQuotationRemarkDetails => {
                    this.rnsQuotationRemarkDetails = rnsQuotationRemarkDetails.body;
                    if (this.rnsQuotationRemarkDetails.authDate) {
                        this.rnsQuotationRemarkDetails.authDate = {
                            year: this.rnsQuotationRemarkDetails.authDate.getFullYear(),
                            month: this.rnsQuotationRemarkDetails.authDate.getMonth() + 1,
                            day: this.rnsQuotationRemarkDetails.authDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.rnsQuotationRemarkDetailsModalRefFlow(
                        component,
                        this.rnsQuotationRemarkDetails,
                        this.rnsQuotation,
                        type
                    );
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationRemarkDetailsModalRef(component, new RnsQuotationRemarkDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsQuotationRemarkDetailsModalRef(component: Component, rnsQuotationRemarkDetails: RnsQuotationRemarkDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationRemarkDetails = rnsQuotationRemarkDetails;
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

    rnsQuotationRemarkDetailsModalRefFlow(
        component: Component,
        rnsQuotationRemarkDetails: RnsQuotationRemarkDetails,
        rnsQuotation: RnsQuotation,
        flowType: any
    ): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationRemarkDetails = rnsQuotationRemarkDetails;
        modalRef.componentInstance.rnsQuotation = rnsQuotation;
        modalRef.componentInstance.flowType = flowType;
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
