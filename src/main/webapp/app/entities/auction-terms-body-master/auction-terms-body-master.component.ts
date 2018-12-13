import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AuctionTermsBodyMaster } from './auction-terms-body-master.model';
import { AuctionTermsBodyMasterService } from './auction-terms-body-master.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-terms-body-master',
    templateUrl: './auction-terms-body-master.component.html'
})
export class AuctionTermsBodyMasterComponent implements OnInit, OnDestroy {
    auctionTermsBodyMasters: AuctionTermsBodyMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private auctionTermsBodyMasterService: AuctionTermsBodyMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.auctionTermsBodyMasterService.query().subscribe(
            (res: HttpResponse<AuctionTermsBodyMaster[]>) => {
                this.auctionTermsBodyMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAuctionTermsBodyMasters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AuctionTermsBodyMaster) {
        return item.id;
    }
    registerChangeInAuctionTermsBodyMasters() {
        this.eventSubscriber = this.eventManager.subscribe('auctionTermsBodyMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
