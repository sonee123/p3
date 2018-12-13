import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { RnsQuotationRemarkDetailsPopupService } from './rns-quotation-remark-details-popup.service';
import { RnsQuotationRemarkDetailsService } from './rns-quotation-remark-details.service';

@Component({
    selector: 'jhi-rns-quotation-remark-details-dialog',
    templateUrl: './rns-quotation-remark-details-dialog.component.html'
})
export class RnsQuotationRemarkDetailsDialogComponent implements OnInit {
    rnsQuotationRemarkDetails: RnsQuotationRemarkDetails;
    isSaving: boolean;
    authDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationRemarkDetailsService: RnsQuotationRemarkDetailsService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotationRemarkDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationRemarkDetailsService.update(this.rnsQuotationRemarkDetails));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationRemarkDetailsService.create(this.rnsQuotationRemarkDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationRemarkDetails>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationRemarkDetails>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationRemarkDetails>) {
        this.eventManager.broadcast({ name: 'rnsQuotationRemarkDetailsListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-quotation-remark-details-popup',
    template: ''
})
export class RnsQuotationRemarkDetailsPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationRemarkDetailsPopupService: RnsQuotationRemarkDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationRemarkDetailsPopupService.open(RnsQuotationRemarkDetailsDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationRemarkDetailsPopupService.open(RnsQuotationRemarkDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
