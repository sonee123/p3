import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuctionVrnt } from './auction-vrnt.model';
import { AuctionVrntService } from './auction-vrnt.service';
import { RnsQuotationVariant } from '../rns-quotation-variant/rns-quotation-variant.model';

@Injectable()
export class AuctionVrntPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private auctionVrntService: AuctionVrntService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.auctionVrntService.find(id).subscribe(auctionVrnt => {
                    this.ngbModalRef = this.auctionVrntModalRef(component, auctionVrnt.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auctionVrntModalRef(component, new AuctionVrnt());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    openByVariantId(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.auctionVrntService.findByVariantId(id).subscribe(
                    auctionVrnt => {
                        this.ngbModalRef = this.auctionVrntModalRef(component, auctionVrnt.body);
                        resolve(this.ngbModalRef);
                    },
                    err => {
                        // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                        setTimeout(() => {
                            const auctionVariant = new AuctionVrnt();
                            auctionVariant.variant = new RnsQuotationVariant();
                            auctionVariant.variant.id = Number(id);
                            this.ngbModalRef = this.auctionVrntModalRef(component, auctionVariant);
                            resolve(this.ngbModalRef);
                        }, 0);
                    }
                );
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auctionVrntModalRef(component, new AuctionVrnt());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auctionVrntModalRef(component: Component, auctionVrnt: AuctionVrnt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.auctionVrnt = auctionVrnt;
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
