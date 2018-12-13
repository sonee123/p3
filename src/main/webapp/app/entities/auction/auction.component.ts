import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Auction } from './auction.model';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AuctionService } from 'app/entities/auction';

@Component({
    selector: 'jhi-auction',
    templateUrl: './auction.component.html'
})
export class AuctionComponent implements OnInit, OnDestroy {
    auctions: Auction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private auctionService: AuctionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.auctionService.query().subscribe(
            (res: HttpResponse<Auction[]>) => {
                this.auctions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAuctions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Auction) {
        return item.id;
    }
    registerChangeInAuctions() {
        this.eventSubscriber = this.eventManager.subscribe('auctionListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
