import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionTermsBodyMaster } from './auction-terms-body-master.model';
import { AuctionTermsBodyMasterService } from './auction-terms-body-master.service';

@Component({
    selector: 'jhi-auction-terms-body-master-detail',
    templateUrl: './auction-terms-body-master-detail.component.html'
})
export class AuctionTermsBodyMasterDetailComponent implements OnInit, OnDestroy {
    auctionTermsBodyMaster: AuctionTermsBodyMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private auctionTermsBodyMasterService: AuctionTermsBodyMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInAuctionTermsBodyMasters();
    }

    load(id) {
        this.auctionTermsBodyMasterService.find(id).subscribe(auctionTermsBodyMaster => {
            this.auctionTermsBodyMaster = auctionTermsBodyMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAuctionTermsBodyMasters() {
        this.eventSubscriber = this.eventManager.subscribe('auctionTermsBodyMasterListModification', response =>
            this.load(this.auctionTermsBodyMaster.id)
        );
    }
}
