import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { RnsQuotationRemarkDetailsService } from './rns-quotation-remark-details.service';

@Component({
    selector: 'jhi-rns-quotation-remark-details-detail',
    templateUrl: './rns-quotation-remark-details-detail.component.html'
})
export class RnsQuotationRemarkDetailsDetailComponent implements OnInit, OnDestroy {
    rnsQuotationRemarkDetails: RnsQuotationRemarkDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationRemarkDetailsService: RnsQuotationRemarkDetailsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationRemarkDetails();
    }

    load(id) {
        this.rnsQuotationRemarkDetailsService.find(id).subscribe(rnsQuotationRemarkDetails => {
            this.rnsQuotationRemarkDetails = rnsQuotationRemarkDetails.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationRemarkDetails() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationRemarkDetailsListModification', response =>
            this.load(this.rnsQuotationRemarkDetails.id)
        );
    }
}
