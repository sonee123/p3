import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsSectorMaster } from './rns-sector-master.model';
import { RnsSectorMasterService } from './rns-sector-master.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-sector-master',
    templateUrl: './rns-sector-master.component.html'
})
export class RnsSectorMasterComponent implements OnInit, OnDestroy {
    rnsSectorMasters: RnsSectorMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsSectorMasterService: RnsSectorMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsSectorMasterService.query().subscribe(
            (res: HttpResponse<RnsSectorMaster[]>) => {
                this.rnsSectorMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsSectorMasters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsSectorMaster) {
        return item.id;
    }
    registerChangeInRnsSectorMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsSectorMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
