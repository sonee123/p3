import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsPayTermsMaster } from './rns-pay-terms-master.model';
import { RnsPayTermsMasterPopupService } from './rns-pay-terms-master-popup.service';
import { RnsPayTermsMasterService } from './rns-pay-terms-master.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-rns-pay-terms-master-dialog',
    templateUrl: './rns-pay-terms-master-dialog.component.html'
})
export class RnsPayTermsMasterDialogComponent implements OnInit {
    rnsPayTermsMaster: RnsPayTermsMaster;
    isSaving: boolean;

    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private rnsCatgMasterService: RnsCatgMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsCatgMasterService.query().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnscatgmasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsPayTermsMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsPayTermsMasterService.update(this.rnsPayTermsMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsPayTermsMasterService.create(this.rnsPayTermsMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsPayTermsMaster>>) {
        result.subscribe((res: HttpResponse<RnsPayTermsMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsPayTermsMaster>) {
        this.eventManager.broadcast({ name: 'rnsPayTermsMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsCatgMasterById(index: number, item: RnsCatgMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-pay-terms-master-popup',
    template: ''
})
export class RnsPayTermsMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsPayTermsMasterPopupService: RnsPayTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsPayTermsMasterPopupService.open(RnsPayTermsMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsPayTermsMasterPopupService.open(RnsPayTermsMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
