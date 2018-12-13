import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { RnsUpchargeDtlPopupService } from './rns-upcharge-dtl-popup.service';
import { RnsUpchargeDtlService } from './rns-upcharge-dtl.service';

@Component({
    selector: 'jhi-rns-upcharge-dtl-dialog',
    templateUrl: './rns-upcharge-dtl-dialog.component.html'
})
export class RnsUpchargeDtlDialogComponent implements OnInit {
    rnsUpchargeDtl: RnsUpchargeDtl;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsUpchargeDtlService: RnsUpchargeDtlService,
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
        if (this.rnsUpchargeDtl.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsUpchargeDtlService.update(this.rnsUpchargeDtl));
        } else {
            this.subscribeToSaveResponse(this.rnsUpchargeDtlService.create(this.rnsUpchargeDtl));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsUpchargeDtl>>) {
        result.subscribe((res: HttpResponse<RnsUpchargeDtl>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsUpchargeDtl>) {
        this.eventManager.broadcast({ name: 'rnsUpchargeDtlListModification', content: 'OK' });
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
    selector: 'jhi-rns-upcharge-dtl-popup',
    template: ''
})
export class RnsUpchargeDtlPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUpchargeDtlPopupService: RnsUpchargeDtlPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsUpchargeDtlPopupService.open(RnsUpchargeDtlDialogComponent as Component, params['id']);
            } else {
                this.rnsUpchargeDtlPopupService.open(RnsUpchargeDtlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
