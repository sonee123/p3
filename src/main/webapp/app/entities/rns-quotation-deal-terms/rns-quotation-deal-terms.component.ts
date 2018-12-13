import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationDealTerms } from './rns-quotation-deal-terms.model';
import { RnsQuotationDealTermsService } from './rns-quotation-deal-terms.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-deal-terms',
    templateUrl: './rns-quotation-deal-terms.component.html'
})
export class RnsQuotationDealTermsComponent implements OnInit, OnDestroy {
    rnsQuotationDealTerms: RnsQuotationDealTerms[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationDealTermsService: RnsQuotationDealTermsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationDealTermsService.query().subscribe(
            (res: HttpResponse<RnsQuotationDealTerms[]>) => {
                this.rnsQuotationDealTerms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationDealTerms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationDealTerms) {
        return item.id;
    }
    registerChangeInRnsQuotationDealTerms() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationDealTermsListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
