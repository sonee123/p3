import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AuctionVarDetails } from './auction-var-details.model';
import { AuctionVarDetailsService } from './auction-var-details.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-var-details',
    templateUrl: './auction-var-details.component.html'
})
export class AuctionVarDetailsComponent implements OnInit, OnDestroy {
    auctionVarDetails: AuctionVarDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private auctionVarDetailsService: AuctionVarDetailsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.auctionVarDetailsService.query().subscribe(
            (res: HttpResponse<AuctionVarDetails[]>) => {
                this.auctionVarDetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAuctionVarDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuctionVarDetails) {
        return item.id;
    }
    registerChangeInAuctionVarDetails() {
        this.eventSubscriber = this.eventManager.subscribe('auctionVarDetailsListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
