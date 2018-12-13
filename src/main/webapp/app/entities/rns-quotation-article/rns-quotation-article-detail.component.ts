import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationArticle } from './rns-quotation-article.model';
import { RnsQuotationArticleService } from './rns-quotation-article.service';

@Component({
    selector: 'jhi-rns-quotation-article-detail',
    templateUrl: './rns-quotation-article-detail.component.html'
})
export class RnsQuotationArticleDetailComponent implements OnInit, OnDestroy {
    rnsQuotationArticle: RnsQuotationArticle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsQuotationArticleService: RnsQuotationArticleService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsQuotationArticles();
    }

    load(id) {
        this.rnsQuotationArticleService.find(id).subscribe(rnsQuotationArticle => {
            this.rnsQuotationArticle = rnsQuotationArticle.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsQuotationArticles() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationArticleListModification', response =>
            this.load(this.rnsQuotationArticle.id)
        );
    }
}
