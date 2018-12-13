import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsForwardTypeMaster } from './rns-forward-type-master.model';
import { RnsForwardTypeMasterPopupService } from './rns-forward-type-master-popup.service';
import { RnsForwardTypeMasterService } from './rns-forward-type-master.service';

@Component({
    selector: 'jhi-rns-forward-type-master-dialog',
    templateUrl: './rns-forward-type-master-dialog.component.html'
})
export class RnsForwardTypeMasterDialogComponent implements OnInit {
    rnsForwardTypeMaster: RnsForwardTypeMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsForwardTypeMasterService: RnsForwardTypeMasterService,
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
        if (this.rnsForwardTypeMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsForwardTypeMasterService.update(this.rnsForwardTypeMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsForwardTypeMasterService.create(this.rnsForwardTypeMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsForwardTypeMaster>>) {
        result.subscribe(
            (res: HttpResponse<RnsForwardTypeMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsForwardTypeMaster>) {
        this.eventManager.broadcast({ name: 'rnsForwardTypeMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-forward-type-master-popup',
    template: ''
})
export class RnsForwardTypeMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsForwardTypeMasterPopupService: RnsForwardTypeMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsForwardTypeMasterPopupService.open(RnsForwardTypeMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsForwardTypeMasterPopupService.open(RnsForwardTypeMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
