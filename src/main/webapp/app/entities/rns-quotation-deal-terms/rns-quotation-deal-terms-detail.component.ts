import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationDealTerms } from './rns-quotation-deal-terms.model';
import { RnsQuotationDealTermsService } from './rns-quotation-deal-terms.service';

@Component({
    selector: 'jhi-rns-quotation-deal-terms-detail',
    templateUrl: './rns-quotation-deal-terms-detail.component.html'
})
export class RnsQuotationDealTermsDetailComponent implements OnInit, OnDestroy {
    rnsQuotationDealTerms: RnsQuotationDealTerms;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationDealTermsService: RnsQuotationDealTermsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationDealTerms();
    }

    load(id) {
        this.rnsQuotationDealTermsService.find(id).subscribe(rnsQuotationDealTerms => {
            this.rnsQuotationDealTerms = rnsQuotationDealTerms.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationDealTerms() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationDealTermsListModification', response =>
            this.load(this.rnsQuotationDealTerms.id)
        );
    }
}
