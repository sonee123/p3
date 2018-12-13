import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { RnsUpchargeDtlService } from './rns-upcharge-dtl.service';
import { RnsQuotationVendors, RnsQuotationVendorsService } from '../rns-quotation-vendors';

@Injectable()
export class RnsUpchargeDtlPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsUpchargeDtl: RnsUpchargeDtl;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsUpchargeDtlService: RnsUpchargeDtlService,
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
                this.rnsUpchargeDtlService.find(id).subscribe(rnsUpchargeDtl => {
                    this.rnsUpchargeDtl = rnsUpchargeDtl.body;
                    this.rnsUpchargeDtl.createdDate = this.datePipe.transform(this.rnsUpchargeDtl.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsUpchargeDtlModalRef(component, this.rnsUpchargeDtl);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsUpchargeDtlModalRef(component, new RnsUpchargeDtl());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    select(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsUpchargeDtlService.select(id).subscribe(rnsUpchargeDtl => {
                    this.rnsUpchargeDtl = rnsUpchargeDtl.body;
                    this.rnsUpchargeDtl.createdDate = this.datePipe.transform(this.rnsUpchargeDtl.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsUpchargeDtlModalRef(component, this.rnsUpchargeDtl);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsUpchargeDtlModalRef(component, new RnsUpchargeDtl());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    selectvendor(component: Component, id?: number | any): Promise<NgbModalRef> {
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
            }
        });
    }

    rnsUpchargeDtlModalRef(component: Component, rnsUpchargeDtl: RnsUpchargeDtl): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsUpchargeDtl = rnsUpchargeDtl;
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
