import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionTermsMaster } from './auction-terms-master.model';
import { AuctionTermsMasterService } from './auction-terms-master.service';

@Component({
    selector: 'jhi-auction-terms-master-detail',
    templateUrl: './auction-terms-master-detail.component.html'
})
export class AuctionTermsMasterDetailComponent implements OnInit, OnDestroy {
    auctionTermsMaster: AuctionTermsMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auctionTermsMasterService: AuctionTermsMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInAuctionTermsMasters();
    }

    load(id) {
        this.auctionTermsMasterService.find(id).subscribe(auctionTermsMaster => {
            this.auctionTermsMaster = auctionTermsMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuctionTermsMasters() {
        this.eventSubscriber = this.eventManager.subscribe('auctionTermsMasterListModification', response =>
            this.load(this.auctionTermsMaster.id)
        );
    }
}
