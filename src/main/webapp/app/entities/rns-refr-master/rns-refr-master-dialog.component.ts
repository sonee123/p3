import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsRefrMaster } from './rns-refr-master.model';
import { RnsRefrMasterPopupService } from './rns-refr-master-popup.service';
import { RnsRefrMasterService } from './rns-refr-master.service';
import { RnsRefrDetails, RnsRefrDetailsService } from '../rns-refr-details';

@Component({
    selector: 'jhi-rns-refr-master-dialog',
    templateUrl: './rns-refr-master-dialog.component.html'
})
export class RnsRefrMasterDialogComponent implements OnInit {
    rnsRefrMaster: RnsRefrMaster;
    isSaving: boolean;

    rnsrefrdetails: RnsRefrDetails[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsRefrMasterService: RnsRefrMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsRefrDetailsService.query().subscribe(
            (res: HttpResponse<RnsRefrDetails[]>) => {
                this.rnsrefrdetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsRefrMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsRefrMasterService.update(this.rnsRefrMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsRefrMasterService.create(this.rnsRefrMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsRefrMaster>>) {
        result.subscribe((res: HttpResponse<RnsRefrMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsRefrMaster>) {
        this.eventManager.broadcast({ name: 'rnsRefrMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsRefrDetailsById(index: number, item: RnsRefrDetails) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-refr-master-popup',
    template: ''
})
export class RnsRefrMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRefrMasterPopupService: RnsRefrMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsRefrMasterPopupService.open(RnsRefrMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsRefrMasterPopupService.open(RnsRefrMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
