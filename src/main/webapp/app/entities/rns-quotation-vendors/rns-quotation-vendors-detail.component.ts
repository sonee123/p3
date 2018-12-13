import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationVendors } from './rns-quotation-vendors.model';
import { RnsQuotationVendorsService } from './rns-quotation-vendors.service';

@Component({
    selector: 'jhi-rns-quotation-vendors-detail',
    templateUrl: './rns-quotation-vendors-detail.component.html'
})
export class RnsQuotationVendorsDetailComponent implements OnInit, OnDestroy {
    rnsQuotationVendors: RnsQuotationVendors;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationVendors();
    }

    load(id) {
        this.rnsQuotationVendorsService.find(id).subscribe(rnsQuotationVendors => {
            this.rnsQuotationVendors = rnsQuotationVendors.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationVendors() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationVendorsListModification', response =>
            this.load(this.rnsQuotationVendors.id)
        );
    }
}
