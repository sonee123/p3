import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationArticle } from './rns-quotation-article.model';
import { RnsQuotationArticlePopupService } from './rns-quotation-article-popup.service';
import { RnsQuotationArticleService } from './rns-quotation-article.service';

@Component({
    selector: 'jhi-rns-quotation-article-delete-dialog',
    templateUrl: './rns-quotation-article-delete-dialog.component.html'
})
export class RnsQuotationArticleDeleteDialogComponent {
    rnsQuotationArticle: RnsQuotationArticle;

    constructor(
        private rnsQuotationArticleService: RnsQuotationArticleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationArticleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationArticleListModification',
                content: 'Deleted an rnsQuotationArticle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-article-delete-popup',
    template: ''
})
export class RnsQuotationArticleDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationArticlePopupService: RnsQuotationArticlePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationArticlePopupService.open(RnsQuotationArticleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
