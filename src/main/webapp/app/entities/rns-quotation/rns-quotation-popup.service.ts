import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsQuotation } from './rns-quotation.model';
import { RnsQuotationService } from './rns-quotation.service';
import { BiddingCompareModel } from './bidding-compare.model';

@Injectable()
export class RnsQuotationPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsQuotation: RnsQuotation;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsQuotationService: RnsQuotationService
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
                this.rnsQuotationService.find(id).subscribe(rnsQuotation => {
                    this.rnsQuotation = rnsQuotation.body;
                    this.rnsQuotation.validity = this.datePipe.transform(this.rnsQuotation.validity, 'yyyy-MM-ddTHH:mm:ss');
                    this.rnsQuotation.auctionValidity = this.datePipe.transform(this.rnsQuotation.auctionValidity, 'yyyy-MM-ddTHH:mm:ss');
                    this.rnsQuotation.createdOn = this.datePipe.transform(this.rnsQuotation.createdOn, 'yyyy-MM-ddTHH:mm:ss');
                    if (this.rnsQuotation.targetPcd) {
                        this.rnsQuotation.targetPcd = {
                            year: this.rnsQuotation.targetPcd.getFullYear(),
                            month: this.rnsQuotation.targetPcd.getMonth() + 1,
                            day: this.rnsQuotation.targetPcd.getDate()
                        };
                    }
                    this.rnsQuotation.date = this.datePipe.transform(this.rnsQuotation.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsQuotationModalRef(component, this.rnsQuotation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationModalRef(component, new RnsQuotation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    compare(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationService.compare(id).subscribe(biddingCompareModel => {
                    this.ngbModalRef = this.biddingCompareModalRef(component, biddingCompareModel.body);
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    rnsQuotationModalRef(component: Component, rnsQuotation: RnsQuotation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotation = rnsQuotation;
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

    biddingCompareModalRef(component: Component, biddingCompareModel: BiddingCompareModel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static', windowClass: 'modal-xxl' });
        modalRef.componentInstance.biddingCompare = biddingCompareModel;
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
