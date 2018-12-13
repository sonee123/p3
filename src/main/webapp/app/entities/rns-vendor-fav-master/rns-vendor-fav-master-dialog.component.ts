import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsVendorFavMaster } from './rns-vendor-fav-master.model';
import { RnsVendorFavMasterPopupService } from './rns-vendor-fav-master-popup.service';
import { RnsVendorFavMasterService } from './rns-vendor-fav-master.service';

@Component({
    selector: 'jhi-rns-vendor-fav-master-dialog',
    templateUrl: './rns-vendor-fav-master-dialog.component.html'
})
export class RnsVendorFavMasterDialogComponent implements OnInit {
    rnsVendorFavMaster: RnsVendorFavMaster;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsVendorFavMasterService: RnsVendorFavMasterService,
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
        if (this.rnsVendorFavMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsVendorFavMasterService.update(this.rnsVendorFavMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsVendorFavMasterService.create(this.rnsVendorFavMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsVendorFavMaster>>) {
        result.subscribe(
            (res: HttpResponse<RnsVendorFavMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsVendorFavMaster>) {
        this.eventManager.broadcast({ name: 'rnsVendorFavMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-vendor-fav-master-popup',
    template: ''
})
export class RnsVendorFavMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorFavMasterPopupService: RnsVendorFavMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsVendorFavMasterPopupService.open(RnsVendorFavMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsVendorFavMasterPopupService.open(RnsVendorFavMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
