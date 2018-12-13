import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationVendors } from './rns-quotation-vendors.model';
import { RnsQuotationVendorsService } from './rns-quotation-vendors.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-vendors',
    templateUrl: './rns-quotation-vendors.component.html'
})
export class RnsQuotationVendorsComponent implements OnInit, OnDestroy {
    rnsQuotationVendors: RnsQuotationVendors[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationVendorsService.query().subscribe(
            (res: HttpResponse<RnsQuotationVendors[]>) => {
                this.rnsQuotationVendors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationVendors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationVendors) {
        return item.id;
    }
    registerChangeInRnsQuotationVendors() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationVendorsListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
