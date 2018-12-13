import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { RnsQuotationRemarkDetailsService } from './rns-quotation-remark-details.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-remark-details',
    templateUrl: './rns-quotation-remark-details.component.html'
})
export class RnsQuotationRemarkDetailsComponent implements OnInit, OnDestroy {
    rnsQuotationRemarkDetails: RnsQuotationRemarkDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationRemarkDetailsService: RnsQuotationRemarkDetailsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationRemarkDetailsService.query().subscribe(
            (res: HttpResponse<RnsQuotationRemarkDetails[]>) => {
                this.rnsQuotationRemarkDetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationRemarkDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationRemarkDetails) {
        return item.id;
    }
    registerChangeInRnsQuotationRemarkDetails() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationRemarkDetailsListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
