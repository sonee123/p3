import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuctionVarDetails } from './auction-var-details.model';
import { AuctionVarDetailsService } from './auction-var-details.service';
import { HttpResponse } from '@angular/common/http';
import { AuctionPauseDetails, AuctionPauseDetailsService } from 'app/entities/auction-pause-details';

@Injectable()
export class AuctionVarDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auctionVarDetailsService: AuctionVarDetailsService,
        private auctionPauseDetailsService: AuctionPauseDetailsService
    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any, type?: string | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }
            if (id && type) {
                const auctionVarDetails: AuctionVarDetails[] = [];
                this.auctionVarDetailsService.findByVariantId(id).subscribe((res: HttpResponse<AuctionVarDetails>) => {
                    auctionVarDetails.push(res.body);
                    this.ngbModalRef = this.auctionVarDetailsModalRef(component, auctionVarDetails, null, type);
                    resolve(this.ngbModalRef);
                });
            } else if (id) {
                this.auctionVarDetailsService.find(id).subscribe((res: HttpResponse<AuctionVarDetails[]>) => {
                    this.auctionPauseDetailsService.queryQuote(id).subscribe((resPaused: HttpResponse<AuctionPauseDetails>) => {
                        this.ngbModalRef = this.auctionVarDetailsModalRef(component, res.body, resPaused.body, type);
                    });
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    auctionVarDetailsModalRef(
        component: Component,
        auctionVarDetails: AuctionVarDetails[],
        auctionPauseDetails: AuctionPauseDetails,
        type?: string | any
    ): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.auctionVarDetailsList = auctionVarDetails;
        modalRef.componentInstance.auctionPauseDetails = auctionPauseDetails;
        modalRef.componentInstance.type = type;
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
