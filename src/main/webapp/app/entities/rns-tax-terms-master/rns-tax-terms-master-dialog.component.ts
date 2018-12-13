import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsTaxTermsMaster } from './rns-tax-terms-master.model';
import { RnsTaxTermsMasterPopupService } from './rns-tax-terms-master-popup.service';
import { RnsTaxTermsMasterService } from './rns-tax-terms-master.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-rns-tax-terms-master-dialog',
    templateUrl: './rns-tax-terms-master-dialog.component.html'
})
export class RnsTaxTermsMasterDialogComponent implements OnInit {
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    isSaving: boolean;

    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
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
        if (this.rnsTaxTermsMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsTaxTermsMasterService.update(this.rnsTaxTermsMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsTaxTermsMasterService.create(this.rnsTaxTermsMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsTaxTermsMaster>>) {
        result.subscribe((res: HttpResponse<RnsTaxTermsMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsTaxTermsMaster>) {
        this.eventManager.broadcast({ name: 'rnsTaxTermsMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-tax-terms-master-popup',
    template: ''
})
export class RnsTaxTermsMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsTaxTermsMasterPopupService: RnsTaxTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsTaxTermsMasterPopupService.open(RnsTaxTermsMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsTaxTermsMasterPopupService.open(RnsTaxTermsMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
