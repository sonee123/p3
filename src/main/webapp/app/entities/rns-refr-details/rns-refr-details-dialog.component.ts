import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsRefrDetails } from './rns-refr-details.model';
import { RnsRefrDetailsPopupService } from './rns-refr-details-popup.service';
import { RnsRefrDetailsService } from './rns-refr-details.service';

@Component({
    selector: 'jhi-rns-refr-details-dialog',
    templateUrl: './rns-refr-details-dialog.component.html'
})
export class RnsRefrDetailsDialogComponent implements OnInit {
    rnsRefrDetails: RnsRefrDetails;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
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
        if (this.rnsRefrDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsRefrDetailsService.update(this.rnsRefrDetails));
        } else {
            this.subscribeToSaveResponse(this.rnsRefrDetailsService.create(this.rnsRefrDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsRefrDetails>>) {
        result.subscribe((res: HttpResponse<RnsRefrDetails>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsRefrDetails>) {
        this.eventManager.broadcast({ name: 'rnsRefrDetailsListModification', content: 'OK' });
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
    selector: 'jhi-rns-refr-details-popup',
    template: ''
})
export class RnsRefrDetailsPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRefrDetailsPopupService: RnsRefrDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsRefrDetailsPopupService.open(RnsRefrDetailsDialogComponent as Component, params['id']);
            } else {
                this.rnsRefrDetailsPopupService.open(RnsRefrDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
