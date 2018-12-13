import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VndrPrice } from './vndr-price.model';
import { VndrPricePopupService } from './vndr-price-popup.service';
import { VndrPriceService } from './vndr-price.service';
import { LocalStorageService } from 'ngx-webstorage';
import { VndrRank } from './vndr-rank.model';
import { VndrPriceSocketService } from './vndr-price-socket.service';
import { RnsQuotationVendors } from 'app/entities/rns-quotation-vendors';
import { RnsQuotationVariant } from 'app/entities/rns-quotation-variant';
import { RnsQuotation } from 'app/entities/rns-quotation';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-vndr-price-select-dialog',
    templateUrl: './vndr-price-select-dialog.component.html'
})
export class VndrPriceSelectDialogComponent implements OnInit, OnDestroy {
    vndrPrice: VndrPrice;
    isSaving: boolean;
    vendor: RnsQuotationVendors;
    variant: RnsQuotationVariant;
    rnsQuotation: RnsQuotation;
    validityExpired: boolean;
    priceWrong: Boolean;
    regularRate: number;
    highestPrice: VndrRank;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private storage: LocalStorageService,
        private vndrPriceSocketService: VndrPriceSocketService,
        private vndrPriceService: VndrPriceService,
        private comparentchildservice: ComParentChildService,
        private notifier: NotifierService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.highestPrice = new VndrPrice();
        this.vndrPriceService.getHighestPricePost(this.variant.id, this.vendor.vendorCode).subscribe(price => {
            this.highestPrice = price.body;
        });
        this.vndrPriceSocketService.subscribe(this.variant.id);
        this.vndrPriceSocketService.receive().subscribe(activity => {
            activity.body.forEach(data => {
                this.highestPrice = data;
            });
        });
    }

    ngOnDestroy() {
        // this.vndrPriceSocketService.disconnect();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    historyUpdate(variant, key, value) {
        /*const totalValue =
            variant.auctionVrnt.convFactOne * this.vendor.vndrPrice.priceOne +
            variant.auctionVrnt.convFactTwo * this.vendor.vndrPrice.priceTwo +
            variant.auctionVrnt.convFactThree * this.vendor.vndrPrice.priceThree +
            variant.auctionVrnt.convFactFour * this.vendor.vndrPrice.priceFour +
            variant.auctionVrnt.convFactFive * this.vendor.vndrPrice.priceFive +
            variant.auctionVrnt.convFactSix * this.vendor.vndrPrice.priceSix +
            variant.auctionVrnt.convFactSeven * this.vendor.vndrPrice.priceSeven +
            variant.auctionVrnt.convFactEight * this.vendor.vndrPrice.priceEight +
            variant.auctionVrnt.convFactNine * this.vendor.vndrPrice.priceNine +
            variant.auctionVrnt.convFactTen * this.vendor.vndrPrice.priceTen;
        if (totalValue === 0) {
            this.notifier.notify( 'error', 'Total price can not be zero.' );
            return;
        }

        this.regularRate = Number(this.variant.orderQuantity) * totalValue;

        let itemValue = this.storage.retrieve(key);

        if (variant.vendorReguarRate) {
            if (!this.compareInputPrice(variant.vendorReguarRate, totalValue)) {
                if (this.rnsQuotation.eventType === 'AUCTION') {
                    this.notifier.notify( 'error', 'you cannot put price less than ' + variant.vendorReguarRate);
                    return;
                } else {
                    this.notifier.notify( 'error', 'you cannot put price greator than ' + variant.vendorReguarRate);
                    return;
                }
            } else {
                itemValue = [value];
                this.storage.store(key, itemValue);
                this.priceWrong = false;
            }
        } else {
            itemValue = [value];
            this.storage.store(key, itemValue);
            this.priceWrong = false;
        }*/
    }

    saveVndrPrice(variant, vendorID, rnsQuotationVendors) {
        const vndrPriceData = rnsQuotationVendors.vndrPrice;
        const totalValue =
            variant.auctionVrnt.convFactOne * rnsQuotationVendors.vndrPrice.priceOne +
            variant.auctionVrnt.convFactTwo * rnsQuotationVendors.vndrPrice.priceTwo +
            variant.auctionVrnt.convFactThree * rnsQuotationVendors.vndrPrice.priceThree +
            variant.auctionVrnt.convFactFour * rnsQuotationVendors.vndrPrice.priceFour +
            variant.auctionVrnt.convFactFive * rnsQuotationVendors.vndrPrice.priceFive +
            variant.auctionVrnt.convFactSix * rnsQuotationVendors.vndrPrice.priceSix +
            variant.auctionVrnt.convFactSeven * rnsQuotationVendors.vndrPrice.priceSeven +
            variant.auctionVrnt.convFactEight * rnsQuotationVendors.vndrPrice.priceEight +
            variant.auctionVrnt.convFactNine * rnsQuotationVendors.vndrPrice.priceNine +
            variant.auctionVrnt.convFactTen * rnsQuotationVendors.vndrPrice.priceTen;
        if (totalValue === 0) {
            this.notifier.notify('error', 'Total price can not be zero.');
            return;
        }
        if (variant.vendorReguarRate) {
            if (!this.compareInputPrice(variant.vendorReguarRate, totalValue)) {
                if (this.rnsQuotation.eventType === 'AUCTION') {
                    this.notifier.notify('error', 'you cannot put price less than ' + variant.vendorReguarRate);
                } else {
                    this.notifier.notify('error', 'you cannot put price greator than ' + variant.vendorReguarRate);
                }
            } else {
                console.log('this.highestPrice.price', this.highestPrice.price);
                if (this.highestPrice.price) {
                    let auctionAmount = false;
                    const highestallowedValue = this.highestPrice.price + this.rnsQuotation.auctionDetails.minPriceChanges;
                    const minimumallowedValue = this.highestPrice.price - this.rnsQuotation.auctionDetails.minPriceChanges;
                    // console.log('1################', highestallowedValue, totalValue);
                    if (this.rnsQuotation.eventType === 'AUCTION') {
                        if (totalValue <= this.highestPrice.price) {
                            this.notifier.notify('error', 'My bid value must not be less than My Last bid value');
                            return;
                        }
                        if (totalValue < highestallowedValue) {
                            this.notifier.notify(
                                'error',
                                'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                            );
                            return;
                        } else {
                            auctionAmount = true;
                        }
                    } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                        if (totalValue >= this.highestPrice.price) {
                            this.notifier.notify('error', 'My bid value must not be greator than My Last bid value');
                            return;
                        }
                        if (totalValue > minimumallowedValue) {
                            this.notifier.notify(
                                'error',
                                'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                            );
                            return;
                        } else {
                            auctionAmount = true;
                        }
                    }
                    if (auctionAmount) {
                        let percentage = 0;
                        if (this.rnsQuotation.eventType === 'AUCTION') {
                            percentage = (totalValue - this.highestPrice.price) * 100 / totalValue;
                        } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                            percentage = (this.highestPrice.price - totalValue) * 100 / this.highestPrice.price;
                        }
                        if (percentage > 10) {
                            if (confirm('You have improved your quote by more than 10%, are you sure you want to submit this new quote?')) {
                                this.isSaving = true;
                                vndrPriceData.variant = {
                                    id: vendorID
                                };
                                vndrPriceData.vndrQuotation = {
                                    id: rnsQuotationVendors.id
                                };
                                vndrPriceData.vendorCode = this.vendor.vendorCode;
                                vndrPriceData.surrogate = true;
                                this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                                this.isSaving = false;
                            }
                        } else {
                            this.isSaving = true;
                            vndrPriceData.variant = {
                                id: vendorID
                            };
                            vndrPriceData.vndrQuotation = {
                                id: rnsQuotationVendors.id
                            };
                            vndrPriceData.vendorCode = this.vendor.vendorCode;
                            vndrPriceData.surrogate = true;
                            this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                            this.isSaving = false;
                        }
                    }
                } else {
                    this.isSaving = true;
                    vndrPriceData.variant = {
                        id: vendorID
                    };
                    vndrPriceData.vndrQuotation = {
                        id: rnsQuotationVendors.id
                    };
                    vndrPriceData.vendorCode = this.vendor.vendorCode;
                    vndrPriceData.surrogate = true;
                    this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                    this.isSaving = false;
                }
            }
        } else {
            if (this.highestPrice.price) {
                let auctionAmount = false;
                const highestallowedValue: number = this.highestPrice.price + this.rnsQuotation.auctionDetails.minPriceChanges;
                const minimumallowedValue: number = this.highestPrice.price - this.rnsQuotation.auctionDetails.minPriceChanges;
                if (this.rnsQuotation.eventType === 'AUCTION') {
                    if (totalValue <= this.highestPrice.price) {
                        this.notifier.notify('error', 'My bid value must not be less than My Last bid value');
                        return;
                    }
                    if (totalValue < highestallowedValue) {
                        this.notifier.notify(
                            'error',
                            'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                        );
                        return;
                    } else {
                        auctionAmount = true;
                    }
                } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                    if (totalValue >= this.highestPrice.price) {
                        this.notifier.notify('error', 'My bid value must not be greator than My Last bid value');
                        return;
                    }
                    if (totalValue > minimumallowedValue) {
                        this.notifier.notify(
                            'error',
                            'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                        );
                        return;
                    } else {
                        auctionAmount = true;
                    }
                }
                if (auctionAmount) {
                    let percentage = 0;
                    if (this.rnsQuotation.eventType === 'AUCTION') {
                        percentage = (totalValue - this.highestPrice.price) * 100 / totalValue;
                    } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                        percentage = (this.highestPrice.price - totalValue) * 100 / this.highestPrice.price;
                    }
                    if (percentage > 10) {
                        if (confirm('You have improved your quote by more than 10%, are you sure you want to submit this new quote?')) {
                            this.isSaving = true;
                            vndrPriceData.variant = {
                                id: vendorID
                            };
                            vndrPriceData.vndrQuotation = {
                                id: rnsQuotationVendors.id
                            };
                            vndrPriceData.vendorCode = this.vendor.vendorCode;
                            vndrPriceData.surrogate = true;
                            this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                            this.isSaving = false;
                        }
                    } else {
                        console.log('.......', rnsQuotationVendors, vndrPriceData, vendorID);
                        this.isSaving = true;
                        vndrPriceData.variant = {
                            id: vendorID
                        };
                        vndrPriceData.vndrQuotation = {
                            id: rnsQuotationVendors.id
                        };
                        vndrPriceData.vendorCode = this.vendor.vendorCode;
                        vndrPriceData.surrogate = true;
                        this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                        this.isSaving = false;
                    }
                }
            } else {
                this.isSaving = true;
                vndrPriceData.variant = {
                    id: vendorID
                };
                vndrPriceData.vndrQuotation = {
                    id: rnsQuotationVendors.id
                };
                vndrPriceData.vendorCode = this.vendor.vendorCode;
                vndrPriceData.surrogate = true;
                this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                this.isSaving = false;
            }
        }
        this.notifier.notify('success', 'Surrogate entry save successfully!!!');
        this.comparentchildservice.publish('call-parent-auction');
        this.isSaving = false;
    }

    private compareInputPrice(regularRate, price) {
        if (this.rnsQuotation.eventType === 'AUCTION') {
            if (regularRate > price) {
                return false;
            }
        } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
            if (regularRate < price) {
                return false;
            }
        }
        return true;
    }

    save() {
        this.isSaving = true;
        /*this.subscribeToSaveResponse(
            this.rnsRfqPriceService.createrfqvendors(this.vendor.rfqPrice));*/
    }

    private subscribeToSaveResponse(result: Observable<VndrPrice>) {
        result.subscribe((res: VndrPrice) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VndrPrice) {
        this.eventManager.broadcast({ name: 'rnsRfqPriceListModification', content: 'OK' });
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-rfq-price-select-popup',
    template: ''
})
export class VndrPriceSelectPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private vndrPricePopupService: VndrPricePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.vndrPricePopupService.surrogate(VndrPriceSelectDialogComponent as Component, params['id']);
            } else {
                this.vndrPricePopupService.surrogate(VndrPriceSelectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
