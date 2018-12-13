import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationEventDefination } from './rns-quotation-event-defination.model';
import { RnsQuotationEventDefinationPopupService } from './rns-quotation-event-defination-popup.service';
import { RnsQuotationEventDefinationService } from './rns-quotation-event-defination.service';
import { RnsTypeMaster, RnsTypeMasterService } from '../rns-type-master';

@Component({
    selector: 'jhi-rns-quotation-event-defination-dialog',
    templateUrl: './rns-quotation-event-defination-dialog.component.html'
})
export class RnsQuotationEventDefinationDialogComponent implements OnInit {
    rnsQuotationEventDefination: RnsQuotationEventDefination;
    isSaving: boolean;

    rnstypemasters: RnsTypeMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationEventDefinationService: RnsQuotationEventDefinationService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsTypeMasterService.query().subscribe(
            (res: HttpResponse<RnsTypeMaster[]>) => {
                this.rnstypemasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotationEventDefination.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationEventDefinationService.update(this.rnsQuotationEventDefination));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationEventDefinationService.create(this.rnsQuotationEventDefination));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationEventDefination>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationEventDefination>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationEventDefination>) {
        this.eventManager.broadcast({ name: 'rnsQuotationEventDefinationListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsTypeMasterById(index: number, item: RnsTypeMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-quotation-event-defination-popup',
    template: ''
})
export class RnsQuotationEventDefinationPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationEventDefinationPopupService: RnsQuotationEventDefinationPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationEventDefinationPopupService.open(RnsQuotationEventDefinationDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationEventDefinationPopupService.open(RnsQuotationEventDefinationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
