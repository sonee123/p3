import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionVrnt } from './auction-vrnt.model';
import { AuctionVrntService } from './auction-vrnt.service';

@Component({
    selector: 'jhi-auction-vrnt-detail',
    templateUrl: './auction-vrnt-detail.component.html'
})
export class AuctionVrntDetailComponent implements OnInit, OnDestroy {
    auctionVrnt: AuctionVrnt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private auctionVrntService: AuctionVrntService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInAuctionVrnts();
    }

    load(id) {
        this.auctionVrntService.find(id).subscribe(auctionVrnt => {
            this.auctionVrnt = auctionVrnt.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuctionVrnts() {
        this.eventSubscriber = this.eventManager.subscribe('auctionVrntListModification', response => this.load(this.auctionVrnt.id));
    }
}
