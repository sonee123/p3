import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { VndrPrice } from './vndr-price.model';
import { VndrPriceService } from './vndr-price.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-vndr-price',
    templateUrl: './vndr-price.component.html'
})
export class VndrPriceComponent implements OnInit, OnDestroy {
    vndrPrices: VndrPrice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private vndrPriceService: VndrPriceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.vndrPriceService.query().subscribe(
            (res: HttpResponse<VndrPrice[]>) => {
                this.vndrPrices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVndrPrices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VndrPrice) {
        return item.id;
    }
    registerChangeInVndrPrices() {
        this.eventSubscriber = this.eventManager.subscribe('vndrPriceListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
