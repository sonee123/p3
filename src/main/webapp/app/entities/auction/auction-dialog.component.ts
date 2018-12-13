import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Auction } from './auction.model';
import { AuctionPopupService } from './auction-popup.service';
import { AuctionService } from './auction.service';

import { RnsQuotation } from '../rns-quotation/rns-quotation.model';
import { RnsQuotationService } from '../rns-quotation/rns-quotation.service';

import { Currency } from '../currency/currency.model';
import { CurrencyService } from '../currency/currency.service';
import { RnsQuotationVariant, RnsQuotationVariantService } from '../rns-quotation-variant';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import * as _moment from 'moment';
import { DateTimeAdapter, OWL_DATE_TIME_FORMATS, OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';
import { MomentDateTimeAdapter, OWL_MOMENT_DATE_TIME_FORMATS } from 'ng-pick-datetime-moment';
import { NotifierService } from 'angular-notifier';

// See the Moment.js docs for the meaning of these formats:
// https://momentjs.com/docs/#/displaying/format/
export const MY_MOMENT_FORMATS = {
    parseInput: 'DD-MM-YYYY LT',
    fullPickerInput: 'DD-MM-YYYY LT',
    datePickerInput: 'DD-MM-YYYY',
    timePickerInput: 'LT',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY'
};

@Component({
    selector: 'jhi-auction-dialog',
    templateUrl: './auction-dialog.component.html',
    providers: [
        // `MomentDateTimeAdapter` and `OWL_MOMENT_DATE_TIME_FORMATS` can be automatically provided by importing
        // `OwlMomentDateTimeModule` in your applications root module. We provide it at the component level
        // here, due to limitations of our example generation script.
        { provide: DateTimeAdapter, useClass: MomentDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE] },
        { provide: OWL_DATE_TIME_FORMATS, useValue: MY_MOMENT_FORMATS }
    ]
})
export class AuctionDialogComponent implements OnInit {
    auction: Auction;
    isSaving: boolean;
    currency: Currency;
    currencies: Currency[];

    quotations: RnsQuotation[];
    quotation: RnsQuotation;
    disableTimeLot: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auctionService: AuctionService,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private eventManager: JhiEventManager,
        private currencyService: CurrencyService,
        private notifier: NotifierService
    ) {}

    ngOnInit() {
        this.loadAll();

        if (this.auction.id) {
        } else {
            this.auction.allowTieBids = true;
        }

        this.isSaving = false;
        this.rnsQuotationService.find(this.auction.quotationId).subscribe(res => {
            this.quotation = res.body;
        });

        this.disableTimeLot = false;
        this.rnsQuotationVariantService.getByQuotationId(this.auction.quotationId).subscribe((res: HttpResponse<RnsQuotationVariant[]>) => {
            const rnsQuotationVariants: RnsQuotationVariant[] = res.body;
            console.log('rnsQuotationVariants.length', rnsQuotationVariants.length);
            if (rnsQuotationVariants.length === 1) {
                this.auction.timeBetweenLots = 0;
                this.disableTimeLot = true;
            }
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    loadAll() {
        this.currencyService.query().subscribe(
            (res: HttpResponse<Currency[]>) => {
                this.currencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    convertDateTimePublishTime(publishTime) {
        const day = publishTime.getDate();
        const month = publishTime.getMonth() + 1; // months are zero based
        const year = publishTime.getFullYear();
        const time = publishTime.toLocaleTimeString('en-GB');
        publishTime = year + '-' + month + '-' + day + 'T' + time;
        return publishTime;
    }

    save() {
        if (this.auction.bidTimeForOvertimeStart >= this.auction.lotRunningTime) {
            this.notifier.notify(
                'error',
                '“Overtime will trigger in last ? minutes” field value cannot be greater than or equal to the “Lot Running Time(minutes)”'
            );
            return false;
        }
        this.isSaving = true;
        if (this.auction.id !== undefined) {
            // this.auction.publishTime = this.convertDateTimePublishTime(this.auction.publishTime);
            // this.auction.biddingStartTime = this.convertDateTimePublishTime(this.auction.biddingStartTime);

            this.subscribeToSaveResponse(this.auctionService.update(this.auction));
        } else {
            this.subscribeToSaveResponse(this.auctionService.create(this.auction));
        }
    }

    public setMoment(moment: any): any {
        this.auction.publishTime = moment;
    }

    public setMoment1(moment: any): any {
        this.auction.biddingStartTime = moment;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Auction>>) {
        result.subscribe((res: HttpResponse<Auction>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<Auction>) {
        this.eventManager.broadcast({ name: 'auctionListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsQuotationById(index: number, item: RnsQuotation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-auction-popup',
    template: ''
})
export class AuctionPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionPopupService: AuctionPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionPopupService.open(AuctionDialogComponent as Component, params['id']);
            } else {
                this.auctionPopupService.open(AuctionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
