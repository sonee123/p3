import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsRefrMaster } from './rns-refr-master.model';
import { RnsRefrMasterService } from './rns-refr-master.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-refr-master',
    templateUrl: './rns-refr-master.component.html'
})
export class RnsRefrMasterComponent implements OnInit, OnDestroy {
    rnsRefrMasters: RnsRefrMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsRefrMasterService: RnsRefrMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsRefrMasterService.query().subscribe(
            (res: HttpResponse<RnsRefrMaster[]>) => {
                this.rnsRefrMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsRefrMasters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsRefrMaster) {
        return item.id;
    }
    registerChangeInRnsRefrMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRefrMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
