import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsDelPlaceMaster } from './rns-del-place-master.model';
import { RnsDelPlaceMasterPopupService } from './rns-del-place-master-popup.service';
import { RnsDelPlaceMasterService } from './rns-del-place-master.service';

@Component({
    selector: 'jhi-rns-del-place-master-dialog',
    templateUrl: './rns-del-place-master-dialog.component.html'
})
export class RnsDelPlaceMasterDialogComponent implements OnInit {
    rnsDelPlaceMaster: RnsDelPlaceMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
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
        if (this.rnsDelPlaceMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsDelPlaceMasterService.update(this.rnsDelPlaceMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsDelPlaceMasterService.create(this.rnsDelPlaceMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsDelPlaceMaster>>) {
        result.subscribe((res: HttpResponse<RnsDelPlaceMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsDelPlaceMaster>) {
        this.eventManager.broadcast({ name: 'rnsDelPlaceMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-del-place-master-popup',
    template: ''
})
export class RnsDelPlaceMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsDelPlaceMasterPopupService: RnsDelPlaceMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsDelPlaceMasterPopupService.open(RnsDelPlaceMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsDelPlaceMasterPopupService.open(RnsDelPlaceMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
