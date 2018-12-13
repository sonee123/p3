import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationVariant } from './rns-quotation-variant.model';
import { RnsQuotationVariantService } from './rns-quotation-variant.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-variant',
    templateUrl: './rns-quotation-variant.component.html'
})
export class RnsQuotationVariantComponent implements OnInit, OnDestroy {
    rnsQuotationVariants: RnsQuotationVariant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationVariantService.query().subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => {
                this.rnsQuotationVariants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationVariants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationVariant) {
        return item.id;
    }
    registerChangeInRnsQuotationVariants() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationVariantListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
