import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { RnsRfqPrice } from './rns-rfq-price.model';
import { RnsRfqPricePopupService } from './rns-rfq-price-popup.service';
import { RnsRfqPriceService } from './rns-rfq-price.service';
import { AuctionVrntService } from 'app/entities/auction-vrnt';
import { RnsQuotationVendors } from 'app/entities/rns-quotation-vendors';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import { RnsPayTermsMaster, RnsPayTermsMasterService } from 'app/entities/rns-pay-terms-master';
import { RnsDelTermsMaster, RnsDelTermsMasterService } from 'app/entities/rns-del-terms-master';
import { Currency, CurrencyService } from 'app/entities/currency';
import { NotifierService } from 'angular-notifier';
import { Subscription } from 'rxjs';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'jhi-rns-rfq-price-select-dialog',
    templateUrl: './rns-rfq-price-select-dialog.component.html'
})
export class RnsRfqPriceSelectDialogComponent implements OnInit, OnDestroy {
    subscribtion: Subscription;
    rnsRfqPrice: RnsRfqPrice;
    isSaving: boolean;
    vendor: RnsQuotationVendors;
    variant: RnsQuotationVariant;
    rnsQuotation: RnsQuotation;
    validityExpired: boolean;
    rnsPayTermsMasters: RnsPayTermsMaster[];
    rnsDelTermsMasters: RnsDelTermsMaster[];
    currencies: Currency[];
    isWait: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsRfqPriceService: RnsRfqPriceService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private auctionVrntService: AuctionVrntService,
        private eventManager: JhiEventManager,
        private comparentchildservice: ComParentChildService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        private currencyService: CurrencyService,
        private dateUtils: JhiDateUtils,
        private notifier: NotifierService,
        private rnsQuotationService: RnsQuotationService
    ) {}

    ngOnInit() {
        this.isWait = false;
        this.isSaving = false;
        this.loadAll();
        this.rnsRfqPriceService.findVendors(this.rnsRfqPrice.vendorId).subscribe(vendor => {
            this.vendor = vendor.body;
            if (this.vendor.confDelDate != null) {
                const confDelDatedate = this.dateUtils.convertLocalDateFromServer(this.vendor.confDelDate);
                this.vendor.confDelDatedate = {
                    year: confDelDatedate.getFullYear(),
                    month: confDelDatedate.getMonth() + 1,
                    day: confDelDatedate.getDate()
                };
            }
            this.variant = vendor.body.variant;
            this.vendor.rfqPrice = this.rnsRfqPrice;
            this.rnsQuotation = vendor.body.vendorQuotation;
            this.auctionVrntService.findByVariantId(this.variant.id).subscribe(auctionvrntdata => {
                this.variant.auctionConfig = auctionvrntdata.body;
            });
            if (this.rnsQuotation.validity != null) {
                this.refreshbidTime();
            }
            console.log(this.vendor);
        });
    }

    exportFile() {
        this.isWait = true;
        this.rnsQuotationService.exportRfq(this.rnsQuotation.id).subscribe(
            res => {
                this.isWait = false;
                FileSaver.saveAs(res, `Project#${this.rnsQuotation.id}.xlsx`);
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    refreshbidTime() {
        const timer = Observable.timer(2000, 1000);
        this.subscribtion = timer.subscribe(() => {
            this.rnsQuotationService.getCurrentTime().subscribe(time => {
                const date = new Date(this.rnsQuotation.validity);
                const todaysDate = this.dateUtils.convertDateTimeFromServer(time.body);
                this.validityExpired = false;
                if (todaysDate > date) {
                    this.validityExpired = true;
                    if (this.subscribtion) {
                        this.subscribtion.unsubscribe();
                        this.subscribtion = null;
                    }
                }
            });
        });
    }

    ngOnDestroy() {
        if (this.subscribtion) {
            this.subscribtion.unsubscribe();
            this.subscribtion = null;
        }
    }

    loadAll() {
        this.rnsPayTermsMasterService.query().subscribe(
            (res: HttpResponse<RnsPayTermsMaster[]>) => {
                this.rnsPayTermsMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsDelTermsMasterService.query().subscribe(
            (res: HttpResponse<RnsDelTermsMaster[]>) => {
                this.rnsDelTermsMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.currencyService.query().subscribe(
            (res: HttpResponse<Currency[]>) => {
                this.currencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.vendor.rfqPrice.deliveryTerms = this.vendor.deliveryTerms;
        this.vendor.rfqPrice.paymentTerms = this.vendor.paymentTerms;
        this.vendor.rfqPrice.regularRate = this.vendor.regularRate;
        this.vendor.rfqPrice.currency = this.vendor.currency;
        this.vendor.rfqPrice.rfqUserType = 'S';
        if (this.vendor.confDelDatedate != null) {
            this.vendor.confDelDate = this.dateUtils.convertLocalDateToServer(this.vendor.confDelDatedate);
            this.vendor.rfqPrice.confDelDate = this.vendor.confDelDate;
        }
        this.subscribeToSaveResponse(this.rnsRfqPriceService.createrfqvendors(this.vendor.rfqPrice));
    }

    historyUpdate(variant, vendor) {
        vendor.regularRate = (
            variant.auctionConfig.convFactOne * vendor.rfqPrice.priceOne +
            variant.auctionConfig.convFactTwo * vendor.rfqPrice.priceTwo +
            variant.auctionConfig.convFactThree * vendor.rfqPrice.priceThree +
            variant.auctionConfig.convFactFour * vendor.rfqPrice.priceFour +
            variant.auctionConfig.convFactFive * vendor.rfqPrice.priceFive +
            variant.auctionConfig.convFactSix * vendor.rfqPrice.priceSix +
            variant.auctionConfig.convFactSeven * vendor.rfqPrice.priceSeven +
            variant.auctionConfig.convFactEight * vendor.rfqPrice.priceEight +
            variant.auctionConfig.convFactNine * vendor.rfqPrice.priceNine +
            variant.auctionConfig.convFactTen * vendor.rfqPrice.priceTen
        ).toFixed(2);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsRfqPrice>>) {
        result.subscribe((res: HttpResponse<RnsRfqPrice>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsRfqPrice>) {
        this.eventManager.broadcast({ name: 'rnsRfqPriceListModification', content: 'OK' });
        this.isSaving = false;
        this.notifier.notify('success', 'RFQ has been saved.');
        this.comparentchildservice.publish('call-parent');
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
export class RnsRfqPriceSelectPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRfqPricePopupService: RnsRfqPricePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsRfqPricePopupService.open(RnsRfqPriceSelectDialogComponent as Component, params['id']);
            } else {
                this.rnsRfqPricePopupService.open(RnsRfqPriceSelectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
