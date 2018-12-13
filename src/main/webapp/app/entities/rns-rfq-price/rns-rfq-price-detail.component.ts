import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRfqPrice } from './rns-rfq-price.model';
import { RnsRfqPriceService } from './rns-rfq-price.service';

@Component({
    selector: 'jhi-rns-rfq-price-detail',
    templateUrl: './rns-rfq-price-detail.component.html'
})
export class RnsRfqPriceDetailComponent implements OnInit, OnDestroy {
    rnsRfqPrice: RnsRfqPrice;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsRfqPriceService: RnsRfqPriceService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsRfqPrices();
    }

    load(id) {
        this.rnsRfqPriceService.find(id).subscribe(rnsRfqPrice => {
            this.rnsRfqPrice = rnsRfqPrice.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsRfqPrices() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRfqPriceListModification', response => this.load(this.rnsRfqPrice.id));
    }
}
