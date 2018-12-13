import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuctionTermsMaster } from './auction-terms-master.model';
import { AuctionTermsMasterService } from './auction-terms-master.service';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';

@Injectable()
export class AuctionTermsMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private auctionTermsMaster: AuctionTermsMaster;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auctionTermsMasterService: AuctionTermsMasterService,
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
                this.auctionTermsMasterService.find(id).subscribe(auctionTermsMaster => {
                    this.auctionTermsMaster = auctionTermsMaster.body;
                    this.auctionTermsMaster.createdDate = this.datePipe.transform(
                        this.auctionTermsMaster.createdDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.auctionTermsMaster.lastUpdatedDate = this.datePipe.transform(
                        this.auctionTermsMaster.lastUpdatedDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.ngbModalRef = this.auctionTermsMasterModalRef(component, this.auctionTermsMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auctionTermsMasterModalRef(component, new AuctionTermsMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    agree(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationService.find(id).subscribe(rnsQuotation => {
                    this.ngbModalRef = this.rnsQuotationModalRef(component, rnsQuotation.body);
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    auctionTermsMasterModalRef(component: Component, auctionTermsMaster: AuctionTermsMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.auctionTermsMaster = auctionTermsMaster;
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
}
