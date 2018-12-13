import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationCrmRequest } from './rns-quotation-crm-request.model';
import { RnsQuotationCrmRequestService } from './rns-quotation-crm-request.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-crm-request',
    templateUrl: './rns-quotation-crm-request.component.html'
})
export class RnsQuotationCrmRequestComponent implements OnInit, OnDestroy {
    rnsQuotationCrmRequests: RnsQuotationCrmRequest[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationCrmRequestService: RnsQuotationCrmRequestService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationCrmRequestService.query().subscribe(
            (res: HttpResponse<RnsQuotationCrmRequest[]>) => {
                this.rnsQuotationCrmRequests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationCrmRequests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationCrmRequest) {
        return item.id;
    }
    registerChangeInRnsQuotationCrmRequests() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationCrmRequestListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
