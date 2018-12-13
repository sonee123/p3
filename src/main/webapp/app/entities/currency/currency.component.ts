import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Currency, CurrencyService } from 'app/entities/currency';

@Component({
    selector: 'jhi-currency',
    templateUrl: './currency.component.html'
})
export class CurrencyComponent implements OnInit, OnDestroy {
    currencies: Currency[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private currencyService: CurrencyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.currencyService.query().subscribe(
            (res: HttpResponse<Currency[]>) => {
                this.currencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCurrencies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Currency) {
        return item.id;
    }
    registerChangeInCurrencies() {
        this.eventSubscriber = this.eventManager.subscribe('currencyListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
