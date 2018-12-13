import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsArticleMaster } from './rns-article-master.model';
import { RnsArticleMasterPopupService } from './rns-article-master-popup.service';
import { RnsArticleMasterService } from './rns-article-master.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-rns-article-master-dialog',
    templateUrl: './rns-article-master-dialog.component.html'
})
export class RnsArticleMasterDialogComponent implements OnInit {
    rnsArticleMaster: RnsArticleMaster;
    isSaving: boolean;

    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsArticleMasterService: RnsArticleMasterService,
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
        if (this.rnsArticleMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsArticleMasterService.update(this.rnsArticleMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsArticleMasterService.create(this.rnsArticleMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsArticleMaster>>) {
        result.subscribe((res: HttpResponse<RnsArticleMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsArticleMaster>) {
        this.eventManager.broadcast({ name: 'rnsArticleMasterListModification', content: 'OK' });
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
    selector: 'jhi-rns-article-master-popup',
    template: ''
})
export class RnsArticleMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsArticleMasterPopupService: RnsArticleMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsArticleMasterPopupService.open(RnsArticleMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsArticleMasterPopupService.open(RnsArticleMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
