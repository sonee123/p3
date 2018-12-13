import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VndrPrice } from './vndr-price.model';
import { VndrPricePopupService } from './vndr-price-popup.service';
import { VndrPriceService } from './vndr-price.service';
import { RnsQuotationVariant, RnsQuotationVariantService } from '../rns-quotation-variant';
import { RnsQuotationVendors, RnsQuotationVendorsService } from '../rns-quotation-vendors';
import { Currency, CurrencyService } from '../currency';

@Component({
    selector: 'jhi-vndr-price-dialog',
    templateUrl: './vndr-price-dialog.component.html'
})
export class VndrPriceDialogComponent implements OnInit {
    vndrPrice: VndrPrice;
    isSaving: boolean;

    rnsquotationvariants: RnsQuotationVariant[];

    rnsquotationvendors: RnsQuotationVendors[];

    currencies: Currency[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private vndrPriceService: VndrPriceService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private currencyService: CurrencyService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsQuotationVariantService.query().subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => {
                this.rnsquotationvariants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsQuotationVendorsService.query().subscribe(
            (res: HttpResponse<RnsQuotationVendors[]>) => {
                this.rnsquotationvendors = res.body;
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
        if (this.vndrPrice.id !== undefined) {
            this.subscribeToSaveResponse(this.vndrPriceService.update(this.vndrPrice));
        } else {
            this.subscribeToSaveResponse(this.vndrPriceService.create(this.vndrPrice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VndrPrice>>) {
        result.subscribe((res: HttpResponse<VndrPrice>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<VndrPrice>) {
        this.eventManager.broadcast({ name: 'vndrPriceListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsQuotationVariantById(index: number, item: RnsQuotationVariant) {
        return item.id;
    }

    trackRnsQuotationVendorsById(index: number, item: RnsQuotationVendors) {
        return item.id;
    }

    trackCurrencyById(index: number, item: Currency) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vndr-price-popup',
    template: ''
})
export class VndrPricePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private vndrPricePopupService: VndrPricePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.vndrPricePopupService.open(VndrPriceDialogComponent as Component, params['id']);
            } else {
                this.vndrPricePopupService.open(VndrPriceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
