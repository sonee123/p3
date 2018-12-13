import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationEventDefination } from './rns-quotation-event-defination.model';
import { RnsQuotationEventDefinationService } from './rns-quotation-event-defination.service';

@Component({
    selector: 'jhi-rns-quotation-event-defination-detail',
    templateUrl: './rns-quotation-event-defination-detail.component.html'
})
export class RnsQuotationEventDefinationDetailComponent implements OnInit, OnDestroy {
    rnsQuotationEventDefination: RnsQuotationEventDefination;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationEventDefinationService: RnsQuotationEventDefinationService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationEventDefinations();
    }

    load(id) {
        this.rnsQuotationEventDefinationService.find(id).subscribe(rnsQuotationEventDefination => {
            this.rnsQuotationEventDefination = rnsQuotationEventDefination.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationEventDefinations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationEventDefinationListModification', response =>
            this.load(this.rnsQuotationEventDefination.id)
        );
    }
}
