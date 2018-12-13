import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationVariant } from './rns-quotation-variant.model';
import { RnsQuotationVariantService } from './rns-quotation-variant.service';

@Component({
    selector: 'jhi-rns-quotation-variant-detail',
    templateUrl: './rns-quotation-variant-detail.component.html'
})
export class RnsQuotationVariantDetailComponent implements OnInit, OnDestroy {
    rnsQuotationVariant: RnsQuotationVariant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationVariants();
    }

    load(id) {
        this.rnsQuotationVariantService.find(id).subscribe(rnsQuotationVariant => {
            this.rnsQuotationVariant = rnsQuotationVariant.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationVariants() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationVariantListModification', response =>
            this.load(this.rnsQuotationVariant.id)
        );
    }
}
