import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsEmpMaster } from './rns-emp-master.model';
import { RnsEmpMasterPopupService } from './rns-emp-master-popup.service';
import { RnsEmpMasterService } from './rns-emp-master.service';

@Component({
    selector: 'jhi-rns-emp-master-dialog',
    templateUrl: './rns-emp-master-dialog.component.html'
})
export class RnsEmpMasterDialogComponent implements OnInit {
    rnsEmpMaster: RnsEmpMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsEmpMasterService: RnsEmpMasterService,
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
        if (this.rnsEmpMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsEmpMasterService.update(this.rnsEmpMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsEmpMasterService.create(this.rnsEmpMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsEmpMaster>>) {
        result.subscribe((res: HttpResponse<RnsEmpMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsEmpMaster>) {
        this.eventManager.broadcast({ name: 'rnsEmpMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-emp-master-popup',
    template: ''
})
export class RnsEmpMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsEmpMasterPopupService: RnsEmpMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsEmpMasterPopupService.open(RnsEmpMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsEmpMasterPopupService.open(RnsEmpMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
