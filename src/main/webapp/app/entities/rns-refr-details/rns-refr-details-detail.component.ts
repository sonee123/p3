import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRefrDetails } from './rns-refr-details.model';
import { RnsRefrDetailsService } from './rns-refr-details.service';

@Component({
    selector: 'jhi-rns-refr-details-detail',
    templateUrl: './rns-refr-details-detail.component.html'
})
export class RnsRefrDetailsDetailComponent implements OnInit, OnDestroy {
    rnsRefrDetails: RnsRefrDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsRefrDetails();
    }

    load(id) {
        this.rnsRefrDetailsService.find(id).subscribe(rnsRefrDetails => {
            this.rnsRefrDetails = rnsRefrDetails.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsRefrDetails() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRefrDetailsListModification', response => this.load(this.rnsRefrDetails.id));
    }
}
