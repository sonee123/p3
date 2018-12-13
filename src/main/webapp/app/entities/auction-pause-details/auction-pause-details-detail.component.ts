import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionPauseDetails } from './auction-pause-details.model';
import { AuctionPauseDetailsService } from './auction-pause-details.service';

@Component({
    selector: 'jhi-auction-pause-details-detail',
    templateUrl: './auction-pause-details-detail.component.html'
})
export class AuctionPauseDetailsDetailComponent implements OnInit, OnDestroy {
    auctionPauseDetails: AuctionPauseDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auctionPauseDetailsService: AuctionPauseDetailsService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInAuctionPauseDetails();
    }

    load(id) {
        this.auctionPauseDetailsService.find(id).subscribe(auctionPauseDetails => {
            this.auctionPauseDetails = auctionPauseDetails.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuctionPauseDetails() {
        this.eventSubscriber = this.eventManager.subscribe('auctionPauseDetailsListModification', response =>
            this.load(this.auctionPauseDetails.id)
        );
    }
}
