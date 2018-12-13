import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsVendorRemark } from './rns-vendor-remark.model';
import { RnsVendorRemarkService } from './rns-vendor-remark.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-vendor-remark',
    templateUrl: './rns-vendor-remark.component.html'
})
export class RnsVendorRemarkComponent implements OnInit, OnDestroy {
    rnsVendorRemarks: RnsVendorRemark[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsVendorRemarkService: RnsVendorRemarkService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsVendorRemarkService.query().subscribe(
            (res: HttpResponse<RnsVendorRemark[]>) => {
                this.rnsVendorRemarks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsVendorRemarks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsVendorRemark) {
        return item.id;
    }

    registerChangeInRnsVendorRemarks() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorRemarkListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
