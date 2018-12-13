import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsPchMaster } from './rns-pch-master.model';
import { RnsPchMasterPopupService } from './rns-pch-master-popup.service';
import { RnsPchMasterService } from './rns-pch-master.service';
import { RnsRelation, RnsRelationService } from '../rns-relation';

@Component({
    selector: 'jhi-rns-pch-master-dialog',
    templateUrl: './rns-pch-master-dialog.component.html'
})
export class RnsPchMasterDialogComponent implements OnInit {
    rnsPchMaster: RnsPchMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsPchMasterService: RnsPchMasterService,
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
        if (this.rnsPchMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsPchMasterService.update(this.rnsPchMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsPchMasterService.create(this.rnsPchMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsPchMaster>>) {
        result.subscribe((res: HttpResponse<RnsPchMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsPchMaster>) {
        this.eventManager.broadcast({ name: 'rnsPchMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-pch-master-popup',
    template: ''
})
export class RnsPchMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsPchMasterPopupService: RnsPchMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsPchMasterPopupService.open(RnsPchMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsPchMasterPopupService.open(RnsPchMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
