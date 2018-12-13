import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsSourceTeamDtl } from './rns-source-team-dtl.model';
import { RnsSourceTeamDtlPopupService } from './rns-source-team-dtl-popup.service';
import { RnsSourceTeamDtlService } from './rns-source-team-dtl.service';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from '../rns-source-team-master';
import { User, UserService } from 'app/core';

@Component({
    selector: 'jhi-rns-source-team-dtl-dialog',
    templateUrl: './rns-source-team-dtl-dialog.component.html'
})
export class RnsSourceTeamDtlDialogComponent implements OnInit {
    rnsSourceTeamDtl: RnsSourceTeamDtl;
    isSaving: boolean;
    empCode: User;

    rnsSourceTeamMasters: RnsSourceTeamMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsSourceTeamDtlService: RnsSourceTeamDtlService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private eventManager: JhiEventManager,
        private userService: UserService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.registerChangeInSearcUser();
        this.rnsSourceTeamMasterService.query().subscribe(rnsSourceTeamMasters => {
            this.rnsSourceTeamMasters = rnsSourceTeamMasters.body;
        });
        if (this.rnsSourceTeamDtl.id !== undefined) {
            this.empCode = this.rnsSourceTeamDtl.teamUser;
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsSourceTeamDtl.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsSourceTeamDtlService.update(this.rnsSourceTeamDtl));
        } else {
            this.subscribeToSaveResponse(this.rnsSourceTeamDtlService.create(this.rnsSourceTeamDtl));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsSourceTeamDtl>>) {
        result.subscribe((res: HttpResponse<RnsSourceTeamDtl>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    registerChangeInSearcUser() {
        this.eventManager.subscribe('selectedUserLinkCreation', message => {
            const userDetails = message.content;
            this.empCode = userDetails;
            this.rnsSourceTeamDtl.teamUser = this.empCode;
        });
    }

    private onSaveSuccess(result: HttpResponse<RnsSourceTeamDtl>) {
        this.eventManager.broadcast({ name: 'rnsSourceTeamDtlListModification', content: 'OK' });
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
    selector: 'jhi-rns-source-team-dtl-popup',
    template: ''
})
export class RnsSourceTeamDtlPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsSourceTeamDtlPopupService: RnsSourceTeamDtlPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsSourceTeamDtlPopupService.open(RnsSourceTeamDtlDialogComponent as Component, params['id']);
            } else {
                this.rnsSourceTeamDtlPopupService.open(RnsSourceTeamDtlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
