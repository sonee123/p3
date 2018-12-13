import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AuctionTermsBodyMaster } from './auction-terms-body-master.model';
import { AuctionTermsBodyMasterService } from './auction-terms-body-master.service';

@Injectable()
export class AuctionTermsBodyMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private auctionTermsBodyMaster: AuctionTermsBodyMaster;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auctionTermsBodyMasterService: AuctionTermsBodyMasterService
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
                this.auctionTermsBodyMasterService.find(id).subscribe(auctionTermsBodyMaster => {
                    this.auctionTermsBodyMaster = auctionTermsBodyMaster.body;
                    this.auctionTermsBodyMaster.createdDate = this.datePipe.transform(
                        this.auctionTermsBodyMaster.createdDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.ngbModalRef = this.auctionTermsBodyMasterModalRef(component, this.auctionTermsBodyMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.auctionTermsBodyMasterModalRef(component, new AuctionTermsBodyMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    auctionTermsBodyMasterModalRef(component: Component, auctionTermsBodyMaster: AuctionTermsBodyMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.auctionTermsBodyMaster = auctionTermsBodyMaster;
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
