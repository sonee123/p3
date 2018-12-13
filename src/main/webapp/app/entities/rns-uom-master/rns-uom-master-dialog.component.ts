import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsUomMaster } from './rns-uom-master.model';
import { RnsUomMasterPopupService } from './rns-uom-master-popup.service';
import { RnsUomMasterService } from './rns-uom-master.service';

@Component({
    selector: 'jhi-rns-uom-master-dialog',
    templateUrl: './rns-uom-master-dialog.component.html'
})
export class RnsUomMasterDialogComponent implements OnInit {
    rnsUomMaster: RnsUomMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsUomMasterService: RnsUomMasterService,
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

        if (this.rnsUomMaster.id !== undefined) {
            // this.rnsUomMaster.lastModifiedDate = this.convertDateTime1(this.rnsUomMaster.lastModifiedDate);
            this.subscribeToSaveResponse(this.rnsUomMasterService.update(this.rnsUomMaster));
        } else {
            // this.rnsUomMaster.createdDate = this.convertDateTime(this.rnsUomMaster.createdDate);
            this.subscribeToSaveResponse(this.rnsUomMasterService.create(this.rnsUomMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsUomMaster>>) {
        result.subscribe((res: HttpResponse<RnsUomMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsUomMaster>) {
        this.eventManager.broadcast({ name: 'rnsUomMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-uom-master-popup',
    template: ''
})
export class RnsUomMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUomMasterPopupService: RnsUomMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsUomMasterPopupService.open(RnsUomMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsUomMasterPopupService.open(RnsUomMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
