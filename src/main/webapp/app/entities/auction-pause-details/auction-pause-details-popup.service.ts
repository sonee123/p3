import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuctionPauseDetails } from './auction-pause-details.model';
import { AuctionPauseDetailsService } from './auction-pause-details.service';

@Injectable()
export class AuctionPauseDetailsPopupService {
    private ngbModalRef: NgbModalRef;
    private auctionPauseDetails: AuctionPauseDetails;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auctionPauseDetailsService: AuctionPauseDetailsService
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
                this.auctionPauseDetailsService.find(id).subscribe(auctionPauseDetails => {
                    this.auctionPauseDetails = auctionPauseDetails.body;
                    this.auctionPauseDetails.pauseStartDate = this.datePipe.transform(
                        this.auctionPauseDetails.pauseStartDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.auctionPauseDetails.pauseEndDate = this.datePipe.transform(
                        this.auctionPauseDetails.pauseEndDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.auctionPauseDetails.createdDate = this.datePipe.transform(
                        this.auctionPauseDetails.createdDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.auctionPauseDetails.updatedDate = this.datePipe.transform(
                        this.auctionPauseDetails.updatedDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.ngbModalRef = this.auctionPauseDetailsModalRef(component, this.auctionPauseDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auctionPauseDetailsModalRef(component, new AuctionPauseDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auctionPauseDetailsModalRef(component: Component, auctionPauseDetails: AuctionPauseDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.auctionPauseDetails = auctionPauseDetails;
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
