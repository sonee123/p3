import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Auction } from './auction.model';
import { AuctionService } from './auction.service';

@Component({
    selector: 'jhi-auction-detail',
    templateUrl: './auction-detail.component.html'
})
export class AuctionDetailComponent implements OnInit, OnDestroy {
    auction: Auction;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private auctionService: AuctionService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInAuctions();
    }

    load(id) {
        this.auctionService.find(id).subscribe(auction => {
            this.auction = auction.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuctions() {
        this.eventSubscriber = this.eventManager.subscribe('auctionListModification', response => this.load(this.auction.id));
    }
}
