import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotation } from './rns-quotation.model';
import { RnsQuotationService } from './rns-quotation.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation',
    templateUrl: './rns-quotation.component.html'
})
export class RnsQuotationComponent implements OnInit, OnDestroy {
    rnsQuotations: RnsQuotation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationService: RnsQuotationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationService.query().subscribe(
            (res: HttpResponse<RnsQuotation[]>) => {
                this.rnsQuotations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotation) {
        return item.id;
    }
    registerChangeInRnsQuotations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
