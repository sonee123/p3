import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsQuotationCrmRequest } from './rns-quotation-crm-request.model';
import { RnsQuotationCrmRequestService } from './rns-quotation-crm-request.service';

@Injectable()
export class RnsQuotationCrmRequestPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsQuotationCrmRequest: RnsQuotationCrmRequest;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsQuotationCrmRequestService: RnsQuotationCrmRequestService
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
                this.rnsQuotationCrmRequestService.find(id).subscribe(rnsQuotationCrmRequest => {
                    this.rnsQuotationCrmRequest = rnsQuotationCrmRequest.body;
                    if (this.rnsQuotationCrmRequest.targetPcd) {
                        this.rnsQuotationCrmRequest.targetPcd = {
                            year: this.rnsQuotationCrmRequest.targetPcd.getFullYear(),
                            month: this.rnsQuotationCrmRequest.targetPcd.getMonth() + 1,
                            day: this.rnsQuotationCrmRequest.targetPcd.getDate()
                        };
                    }
                    this.rnsQuotationCrmRequest.date = this.datePipe.transform(this.rnsQuotationCrmRequest.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsQuotationCrmRequestModalRef(component, this.rnsQuotationCrmRequest);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationCrmRequestModalRef(component, new RnsQuotationCrmRequest());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsQuotationCrmRequestModalRef(component: Component, rnsQuotationCrmRequest: RnsQuotationCrmRequest): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationCrmRequest = rnsQuotationCrmRequest;
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
