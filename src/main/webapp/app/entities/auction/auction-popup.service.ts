import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Auction } from './auction.model';
import { AuctionService } from './auction.service';

@Injectable()
export class AuctionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auctionService: AuctionService
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
                this.auctionService.find(id).subscribe(auction => {
                    // auction.publishTime = this.datePipe
                    //  .transform(auction.publishTime, 'yyyy-MM-ddTHH:mm:ss');
                    // auction.biddingStartTime = this.datePipe
                    //  .transform(auction.biddingStartTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.auctionModalRef(component, auction.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auctionModalRef(component, new Auction());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auctionModalRef(component: Component, auction: Auction): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.auction = auction;
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
