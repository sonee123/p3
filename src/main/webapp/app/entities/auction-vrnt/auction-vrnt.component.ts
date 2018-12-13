import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AuctionVrnt } from './auction-vrnt.model';
import { AuctionVrntService } from './auction-vrnt.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-vrnt',
    templateUrl: './auction-vrnt.component.html'
})
export class AuctionVrntComponent implements OnInit, OnDestroy {
    auctionVrnts: AuctionVrnt[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private auctionVrntService: AuctionVrntService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.auctionVrntService.query().subscribe(
            (res: HttpResponse<AuctionVrnt[]>) => {
                this.auctionVrnts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAuctionVrnts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuctionVrnt) {
        return item.id;
    }
    registerChangeInAuctionVrnts() {
        this.eventSubscriber = this.eventManager.subscribe('auctionVrntListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
