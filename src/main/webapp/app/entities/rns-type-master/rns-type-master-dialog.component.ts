import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsTypeMaster } from './rns-type-master.model';
import { RnsTypeMasterPopupService } from './rns-type-master-popup.service';
import { RnsTypeMasterService } from './rns-type-master.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-rns-type-master-dialog',
    templateUrl: './rns-type-master-dialog.component.html'
})
export class RnsTypeMasterDialogComponent implements OnInit {
    rnsTypeMaster: RnsTypeMaster;
    isSaving: boolean;
    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsCatgMasterService: RnsCatgMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsCatgMasterService.querylogin().subscribe(
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
        if (this.rnsTypeMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsTypeMasterService.update(this.rnsTypeMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsTypeMasterService.create(this.rnsTypeMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsTypeMaster>>) {
        result.subscribe((res: HttpResponse<RnsTypeMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsTypeMaster>) {
        this.eventManager.broadcast({ name: 'rnsTypeMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-type-master-popup',
    template: ''
})
export class RnsTypeMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsTypeMasterPopupService: RnsTypeMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsTypeMasterPopupService.open(RnsTypeMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsTypeMasterPopupService.open(RnsTypeMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
