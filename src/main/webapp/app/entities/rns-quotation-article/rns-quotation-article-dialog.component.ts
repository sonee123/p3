import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationArticle } from './rns-quotation-article.model';
import { RnsQuotationArticlePopupService } from './rns-quotation-article-popup.service';
import { RnsQuotationArticleService } from './rns-quotation-article.service';
import { RnsBuyerMaster, RnsBuyerMasterService } from '../rns-buyer-master';

@Component({
    selector: 'jhi-rns-quotation-article-dialog',
    templateUrl: './rns-quotation-article-dialog.component.html'
})
export class RnsQuotationArticleDialogComponent implements OnInit {
    rnsQuotationArticle: RnsQuotationArticle;
    isSaving: boolean;

    rnsbuyermasters: RnsBuyerMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationArticleService: RnsQuotationArticleService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsBuyerMasterService.query().subscribe(
            (res: HttpResponse<RnsBuyerMaster[]>) => {
                this.rnsbuyermasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotationArticle.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationArticleService.update(this.rnsQuotationArticle));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationArticleService.create(this.rnsQuotationArticle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationArticle>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationArticle>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationArticle>) {
        this.eventManager.broadcast({ name: 'rnsQuotationArticleListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsBuyerMasterById(index: number, item: RnsBuyerMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-quotation-article-popup',
    template: ''
})
export class RnsQuotationArticlePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationArticlePopupService: RnsQuotationArticlePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationArticlePopupService.open(RnsQuotationArticleDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationArticlePopupService.open(RnsQuotationArticleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
