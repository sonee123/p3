import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsDelTermsMaster } from './rns-del-terms-master.model';
import { RnsDelTermsMasterPopupService } from './rns-del-terms-master-popup.service';
import { RnsDelTermsMasterService } from './rns-del-terms-master.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-rns-del-terms-master-dialog',
    templateUrl: './rns-del-terms-master-dialog.component.html'
})
export class RnsDelTermsMasterDialogComponent implements OnInit {
    rnsDelTermsMaster: RnsDelTermsMaster;
    isSaving: boolean;

    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
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
        if (this.rnsDelTermsMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsDelTermsMasterService.update(this.rnsDelTermsMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsDelTermsMasterService.create(this.rnsDelTermsMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsDelTermsMaster>>) {
        result.subscribe((res: HttpResponse<RnsDelTermsMaster>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsDelTermsMaster>) {
        this.eventManager.broadcast({ name: 'rnsDelTermsMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-del-terms-master-popup',
    template: ''
})
export class RnsDelTermsMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsDelTermsMasterPopupService: RnsDelTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsDelTermsMasterPopupService.open(RnsDelTermsMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsDelTermsMasterPopupService.open(RnsDelTermsMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
