import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsVendorFavMaster } from './rns-vendor-fav-master.model';
import { RnsVendorFavMasterService } from './rns-vendor-fav-master.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-vendor-fav-master',
    templateUrl: './rns-vendor-fav-master.component.html'
})
export class RnsVendorFavMasterComponent implements OnInit, OnDestroy {
    rnsVendorFavMasters: RnsVendorFavMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsVendorFavMasterService: RnsVendorFavMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsVendorFavMasterService.query().subscribe(
            (res: HttpResponse<RnsVendorFavMaster[]>) => {
                this.rnsVendorFavMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsVendorFavMasters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsVendorFavMaster) {
        return item.id;
    }
    registerChangeInRnsVendorFavMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorFavMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
