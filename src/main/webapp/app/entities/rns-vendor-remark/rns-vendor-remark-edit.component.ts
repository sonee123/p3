import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsVendorRemark } from './rns-vendor-remark.model';
import { RnsVendorRemarkPopupService } from './rns-vendor-remark-popup.service';
import { RnsVendorRemarkService } from './rns-vendor-remark.service';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';

@Component({
    selector: 'jhi-rns-vendor-remark-dialog',
    templateUrl: './rns-vendor-remark-dialog.component.html'
})
export class RnsVendorRemarkEditComponent implements OnInit {
    rnsVendorRemark: RnsVendorRemark;
    isSaving: boolean;

    rnsquotations: RnsQuotation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsVendorRemarkService: RnsVendorRemarkService,
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
        if (this.rnsVendorRemark.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsVendorRemarkService.update(this.rnsVendorRemark));
        } else {
            this.subscribeToSaveResponse(this.rnsVendorRemarkService.create(this.rnsVendorRemark));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsVendorRemark>>) {
        result.subscribe((res: HttpResponse<RnsVendorRemark>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsVendorRemark>) {
        this.eventManager.broadcast({ name: 'rnsVendorRemarkListModification', content: 'OK' });
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
    selector: 'jhi-rns-vendor-remark-popup',
    template: ''
})
export class RnsVendorRemarkEdittComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorRemarkPopupService: RnsVendorRemarkPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsVendorRemarkPopupService.open(RnsVendorRemarkEditComponent as Component, params['id']);
            } else {
                this.rnsVendorRemarkPopupService.open(RnsVendorRemarkEditComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
