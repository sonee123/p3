import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsSourceTeamMaster } from './rns-source-team-master.model';
import { RnsSourceTeamMasterPopupService } from './rns-source-team-master-popup.service';
import { RnsSourceTeamMasterService } from './rns-source-team-master.service';

@Component({
    selector: 'jhi-rns-source-team-master-dialog',
    templateUrl: './rns-source-team-master-dialog.component.html'
})
export class RnsSourceTeamMasterDialogComponent implements OnInit {
    rnsSourceTeamMaster: RnsSourceTeamMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
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
        if (this.rnsSourceTeamMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsSourceTeamMasterService.update(this.rnsSourceTeamMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsSourceTeamMasterService.create(this.rnsSourceTeamMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsSourceTeamMaster>>) {
        result.subscribe(
            (res: HttpResponse<RnsSourceTeamMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsSourceTeamMaster>) {
        this.eventManager.broadcast({ name: 'rnsSourceTeamMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-source-team-master-popup',
    template: ''
})
export class RnsSourceTeamMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsSourceTeamMasterPopupService: RnsSourceTeamMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsSourceTeamMasterPopupService.open(RnsSourceTeamMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsSourceTeamMasterPopupService.open(RnsSourceTeamMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
