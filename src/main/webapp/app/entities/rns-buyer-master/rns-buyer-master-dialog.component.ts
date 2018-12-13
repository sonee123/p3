import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsBuyerMaster } from './rns-buyer-master.model';
import { RnsBuyerMasterPopupService } from './rns-buyer-master-popup.service';
import { RnsBuyerMasterService } from './rns-buyer-master.service';

@Component({
    selector: 'jhi-rns-buyer-master-dialog',
    templateUrl: './rns-buyer-master-dialog.component.html'
})
export class RnsBuyerMasterDialogComponent implements OnInit {
    rnsBuyerMaster: RnsBuyerMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
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
        if (this.rnsBuyerMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsBuyerMasterService.update(this.rnsBuyerMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsBuyerMasterService.create(this.rnsBuyerMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsBuyerMaster>>) {
        result.subscribe((res: HttpResponse<RnsBuyerMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsBuyerMaster>) {
        this.eventManager.broadcast({ name: 'rnsBuyerMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-buyer-master-popup',
    template: ''
})
export class RnsBuyerMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsBuyerMasterPopupService: RnsBuyerMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsBuyerMasterPopupService.open(RnsBuyerMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsBuyerMasterPopupService.open(RnsBuyerMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
