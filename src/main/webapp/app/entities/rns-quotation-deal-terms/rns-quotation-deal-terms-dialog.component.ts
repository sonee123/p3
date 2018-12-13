import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationDealTerms } from './rns-quotation-deal-terms.model';
import { RnsQuotationDealTermsPopupService } from './rns-quotation-deal-terms-popup.service';
import { RnsQuotationDealTermsService } from './rns-quotation-deal-terms.service';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';
import { RnsPayTermsMaster, RnsPayTermsMasterService } from '../rns-pay-terms-master';
import { RnsDelPlaceMaster, RnsDelPlaceMasterService } from '../rns-del-place-master';

@Component({
    selector: 'jhi-rns-quotation-deal-terms-dialog',
    templateUrl: './rns-quotation-deal-terms-dialog.component.html'
})
export class RnsQuotationDealTermsDialogComponent implements OnInit {
    rnsQuotationDealTerms: RnsQuotationDealTerms;
    isSaving: boolean;

    rnsquotations: RnsQuotation[];

    rnspaytermsmasters: RnsPayTermsMaster[];

    rnsdelplacemasters: RnsDelPlaceMaster[];
    completionByDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationDealTermsService: RnsQuotationDealTermsService,
        private rnsQuotationService: RnsQuotationService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsQuotationService.query().subscribe(
            (res: HttpResponse<RnsQuotation[]>) => {
                this.rnsquotations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsPayTermsMasterService.query().subscribe(
            (res: HttpResponse<RnsPayTermsMaster[]>) => {
                this.rnspaytermsmasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsDelPlaceMasterService.query().subscribe(
            (res: HttpResponse<RnsDelPlaceMaster[]>) => {
                this.rnsdelplacemasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotationDealTerms.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationDealTermsService.update(this.rnsQuotationDealTerms));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationDealTermsService.create(this.rnsQuotationDealTerms));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationDealTerms>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationDealTerms>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationDealTerms>) {
        this.eventManager.broadcast({ name: 'rnsQuotationDealTermsListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsQuotationById(index: number, item: RnsQuotation) {
        return item.id;
    }

    trackRnsPayTermsMasterById(index: number, item: RnsPayTermsMaster) {
        return item.id;
    }

    trackRnsDelPlaceMasterById(index: number, item: RnsDelPlaceMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-quotation-deal-terms-popup',
    template: ''
})
export class RnsQuotationDealTermsPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationDealTermsPopupService: RnsQuotationDealTermsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationDealTermsPopupService.open(RnsQuotationDealTermsDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationDealTermsPopupService.open(RnsQuotationDealTermsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
