import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsRfqPrice } from './rns-rfq-price.model';
import { RnsRfqPriceService } from './rns-rfq-price.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-rfq-price',
    templateUrl: './rns-rfq-price.component.html'
})
export class RnsRfqPriceComponent implements OnInit, OnDestroy {
    rnsRfqPrices: RnsRfqPrice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsRfqPriceService: RnsRfqPriceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsRfqPriceService.query().subscribe(
            (res: HttpResponse<RnsRfqPrice[]>) => {
                this.rnsRfqPrices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsRfqPrices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsRfqPrice) {
        return item.id;
    }
    registerChangeInRnsRfqPrices() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRfqPriceListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
