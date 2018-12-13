import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AuctionPauseDetails } from './auction-pause-details.model';
import { AuctionPauseDetailsService } from './auction-pause-details.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-pause-details',
    templateUrl: './auction-pause-details.component.html'
})
export class AuctionPauseDetailsComponent implements OnInit, OnDestroy {
    auctionPauseDetails: AuctionPauseDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private auctionPauseDetailsService: AuctionPauseDetailsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.auctionPauseDetailsService.query().subscribe(
            (res: HttpResponse<[AuctionPauseDetails]>) => {
                this.auctionPauseDetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAuctionPauseDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuctionPauseDetails) {
        return item.id;
    }
    registerChangeInAuctionPauseDetails() {
        this.eventSubscriber = this.eventManager.subscribe('auctionPauseDetailsListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
