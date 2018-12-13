import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotation } from './rns-quotation.model';
import { RnsQuotationService } from './rns-quotation.service';

@Component({
    selector: 'jhi-rns-quotation-detail',
    templateUrl: './rns-quotation-detail.component.html'
})
export class RnsQuotationDetailComponent implements OnInit, OnDestroy {
    rnsQuotation: RnsQuotation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    constructor(private eventManager: JhiEventManager, private rnsQuotationService: RnsQuotationService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotations();
    }

    load(id) {
        this.rnsQuotationService.find(id).subscribe(rnsQuotation => {
            this.rnsQuotation = rnsQuotation.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationListModification', response => this.load(this.rnsQuotation.id));
    }
}
