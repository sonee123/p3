import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationCrmRequest } from './rns-quotation-crm-request.model';
import { RnsQuotationCrmRequestService } from './rns-quotation-crm-request.service';

@Component({
    selector: 'jhi-rns-quotation-crm-request-detail',
    templateUrl: './rns-quotation-crm-request-detail.component.html'
})
export class RnsQuotationCrmRequestDetailComponent implements OnInit, OnDestroy {
    rnsQuotationCrmRequest: RnsQuotationCrmRequest;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationCrmRequestService: RnsQuotationCrmRequestService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationCrmRequests();
    }

    load(id) {
        this.rnsQuotationCrmRequestService.find(id).subscribe(rnsQuotationCrmRequest => {
            this.rnsQuotationCrmRequest = rnsQuotationCrmRequest.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationCrmRequests() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationCrmRequestListModification', response =>
            this.load(this.rnsQuotationCrmRequest.id)
        );
    }
}
