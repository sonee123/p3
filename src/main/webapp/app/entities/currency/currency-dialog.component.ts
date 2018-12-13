import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Currency } from './currency.model';
import { CurrencyPopupService } from './currency-popup.service';
import { CurrencyService } from './currency.service';

@Component({
    selector: 'jhi-currency-dialog',
    templateUrl: './currency-dialog.component.html'
})
export class CurrencyDialogComponent implements OnInit {
    currency: Currency;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private currencyService: CurrencyService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.currency.id !== undefined) {
            this.subscribeToSaveResponse(this.currencyService.update(this.currency));
        } else {
            this.subscribeToSaveResponse(this.currencyService.create(this.currency));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Currency>>) {
        result.subscribe((res: HttpResponse<Currency>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<Currency>) {
        this.eventManager.broadcast({ name: 'currencyListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-currency-popup',
    template: ''
})
export class CurrencyPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private currencyPopupService: CurrencyPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.currencyPopupService.open(CurrencyDialogComponent as Component, params['id']);
            } else {
                this.currencyPopupService.open(CurrencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
