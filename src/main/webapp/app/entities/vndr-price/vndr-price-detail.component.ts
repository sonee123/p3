import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VndrPrice } from './vndr-price.model';
import { VndrPriceService } from './vndr-price.service';

@Component({
    selector: 'jhi-vndr-price-detail',
    templateUrl: './vndr-price-detail.component.html'
})
export class VndrPriceDetailComponent implements OnInit, OnDestroy {
    vndrPrice: VndrPrice;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private vndrPriceService: VndrPriceService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInVndrPrices();
    }

    load(id) {
        this.vndrPriceService.find(id).subscribe(vndrPrice => {
            this.vndrPrice = vndrPrice.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVndrPrices() {
        this.eventSubscriber = this.eventManager.subscribe('vndrPriceListModification', response => this.load(this.vndrPrice.id));
    }
}
