import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionVarDetails } from './auction-var-details.model';
import { AuctionVarDetailsService } from './auction-var-details.service';

@Component({
    selector: 'jhi-auction-var-details-detail',
    templateUrl: './auction-var-details-detail.component.html'
})
export class AuctionVarDetailsDetailComponent implements OnInit, OnDestroy {
    auctionVarDetails: AuctionVarDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auctionVarDetailsService: AuctionVarDetailsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInAuctionVarDetails();
    }

    load(id) {
        /*this.auctionVarDetailsService.find(id).subscribe(auctionVarDetails => {
            this.auctionVarDetails = auctionVarDetails;
        });*/
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuctionVarDetails() {
        this.eventSubscriber = this.eventManager.subscribe('auctionVarDetailsListModification', response =>
            this.load(this.auctionVarDetails.id)
        );
    }
}
