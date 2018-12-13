import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsUpchargeMaster } from './rns-upcharge-master.model';
import { RnsUpchargeMasterPopupService } from './rns-upcharge-master-popup.service';
import { RnsUpchargeMasterService } from './rns-upcharge-master.service';

@Component({
    selector: 'jhi-rns-upcharge-master-dialog',
    templateUrl: './rns-upcharge-master-dialog.component.html'
})
export class RnsUpchargeMasterDialogComponent implements OnInit {
    rnsUpchargeMaster: RnsUpchargeMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsUpchargeMasterService: RnsUpchargeMasterService,
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
        if (this.rnsUpchargeMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsUpchargeMasterService.update(this.rnsUpchargeMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsUpchargeMasterService.create(this.rnsUpchargeMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsUpchargeMaster>>) {
        result.subscribe((res: HttpResponse<RnsUpchargeMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsUpchargeMaster>) {
        this.eventManager.broadcast({ name: 'rnsUpchargeMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-upcharge-master-popup',
    template: ''
})
export class RnsUpchargeMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUpchargeMasterPopupService: RnsUpchargeMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsUpchargeMasterPopupService.open(RnsUpchargeMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsUpchargeMasterPopupService.open(RnsUpchargeMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
