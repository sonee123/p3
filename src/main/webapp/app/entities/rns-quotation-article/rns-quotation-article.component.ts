import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationArticle } from './rns-quotation-article.model';
import { RnsQuotationArticleService } from './rns-quotation-article.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-quotation-article',
    templateUrl: './rns-quotation-article.component.html'
})
export class RnsQuotationArticleComponent implements OnInit, OnDestroy {
    rnsQuotationArticles: RnsQuotationArticle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsQuotationArticleService: RnsQuotationArticleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsQuotationArticleService.query().subscribe(
            (res: HttpResponse<RnsQuotationArticle[]>) => {
                this.rnsQuotationArticles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsQuotationArticles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsQuotationArticle) {
        return item.id;
    }
    registerChangeInRnsQuotationArticles() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationArticleListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
