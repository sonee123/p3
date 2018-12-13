import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsRfqPrice } from './rns-rfq-price.model';
import { RnsRfqPriceService } from './rns-rfq-price.service';

@Injectable()
export class RnsRfqPricePopupService {
    private ngbModalRef: NgbModalRef;
    private rnsRfqPrice: RnsRfqPrice;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsRfqPriceService: RnsRfqPriceService
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
                this.rnsRfqPriceService.popup(id).subscribe(rnsRfqPrice => {
                    if (rnsRfqPrice.body == null) {
                        this.ngbModalRef = this.rnsRfqPriceModalRef(component, new RnsRfqPrice());
                        resolve(this.ngbModalRef);
                    } else {
                        this.rnsRfqPrice = rnsRfqPrice.body;
                        if (this.rnsRfqPrice.createdDate) {
                            this.rnsRfqPrice.createdDate = this.datePipe.transform(this.rnsRfqPrice.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                        }
                        this.ngbModalRef = this.rnsRfqPriceModalRef(component, this.rnsRfqPrice);
                        resolve(this.ngbModalRef);
                    }
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsRfqPriceModalRef(component, new RnsRfqPrice());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsRfqPriceModalRef(component: Component, rnsRfqPrice: RnsRfqPrice): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsRfqPrice = rnsRfqPrice;
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
