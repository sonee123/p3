import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsCatgMaster } from './rns-catg-master.model';
import { RnsCatgMasterPopupService } from './rns-catg-master-popup.service';
import { RnsCatgMasterService } from './rns-catg-master.service';
import { RnsRelation, RnsRelationService } from '../rns-relation';

@Component({
    selector: 'jhi-rns-catg-master-dialog',
    templateUrl: './rns-catg-master-dialog.component.html'
})
export class RnsCatgMasterDialogComponent implements OnInit {
    rnsCatgMaster: RnsCatgMaster;
    isSaving: boolean;

    rnsrelations: RnsRelation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsRelationService: RnsRelationService,
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
        if (this.rnsCatgMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsCatgMasterService.update(this.rnsCatgMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsCatgMasterService.create(this.rnsCatgMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsCatgMaster>>) {
        result.subscribe((res: HttpResponse<RnsCatgMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsCatgMaster>) {
        this.eventManager.broadcast({ name: 'rnsCatgMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsRelationById(index: number, item: RnsRelation) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-rns-catg-master-popup',
    template: ''
})
export class RnsCatgMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsCatgMasterPopupService: RnsCatgMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsCatgMasterPopupService.open(RnsCatgMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsCatgMasterPopupService.open(RnsCatgMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
