import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsSectorMaster } from './rns-sector-master.model';
import { RnsSectorMasterPopupService } from './rns-sector-master-popup.service';
import { RnsSectorMasterService } from './rns-sector-master.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-rns-sector-master-dialog',
    templateUrl: './rns-sector-master-dialog.component.html'
})
export class RnsSectorMasterDialogComponent implements OnInit {
    rnsSectorMaster: RnsSectorMaster;
    isSaving: boolean;

    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsSectorMasterService: RnsSectorMasterService,
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
        if (this.rnsSectorMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsSectorMasterService.update(this.rnsSectorMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsSectorMasterService.create(this.rnsSectorMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsSectorMaster>>) {
        result.subscribe((res: HttpResponse<RnsSectorMaster>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsSectorMaster>) {
        this.eventManager.broadcast({ name: 'rnsSectorMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-sector-master-popup',
    template: ''
})
export class RnsSectorMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsSectorMasterPopupService: RnsSectorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsSectorMasterPopupService.open(RnsSectorMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsSectorMasterPopupService.open(RnsSectorMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
