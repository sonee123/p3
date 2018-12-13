import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationEventDefination } from './rns-quotation-event-defination.model';
import { RnsQuotationEventDefinationService } from './rns-quotation-event-defination.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-event-defination',
    templateUrl: './rns-quotation-event-defination.component.html'
})
export class RnsQuotationEventDefinationComponent implements OnInit, OnDestroy {
    rnsQuotationEventDefinations: RnsQuotationEventDefination[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationEventDefinationService: RnsQuotationEventDefinationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationEventDefinationService.query().subscribe(
            (res: HttpResponse<RnsQuotationEventDefination[]>) => {
                this.rnsQuotationEventDefinations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationEventDefinations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationEventDefination) {
        return item.id;
    }
    registerChangeInRnsQuotationEventDefinations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationEventDefinationListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
