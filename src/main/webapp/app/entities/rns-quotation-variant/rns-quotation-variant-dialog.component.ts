import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationVariant } from './rns-quotation-variant.model';
import { RnsQuotationVariantPopupService } from './rns-quotation-variant-popup.service';
import { RnsQuotationVariantService } from './rns-quotation-variant.service';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';

@Component({
    selector: 'jhi-rns-quotation-variant-dialog',
    templateUrl: './rns-quotation-variant-dialog.component.html'
})
export class RnsQuotationVariantDialogComponent implements OnInit {
    rnsQuotationVariant: RnsQuotationVariant;
    isSaving: boolean;

    rnsquotations: RnsQuotation[];
    dealtermCompletionByDp: any;
    dealtermValidUntilDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsQuotationService: RnsQuotationService,
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
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotationVariant.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationVariantService.update(this.rnsQuotationVariant));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationVariantService.create(this.rnsQuotationVariant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationVariant>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVariant>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationVariant>) {
        this.eventManager.broadcast({ name: 'rnsQuotationVariantListModification', content: 'OK' });
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
}

@Component({
    selector: 'jhi-rns-quotation-variant-popup',
    template: ''
})
export class RnsQuotationVariantPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationVariantPopupService: RnsQuotationVariantPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationVariantPopupService.open(RnsQuotationVariantDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationVariantPopupService.open(RnsQuotationVariantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
