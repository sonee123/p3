import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuctionTermsBodyMaster } from './auction-terms-body-master.model';
import { AuctionTermsBodyMasterPopupService } from './auction-terms-body-master-popup.service';
import { AuctionTermsBodyMasterService } from './auction-terms-body-master.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-terms-body-master-dialog',
    templateUrl: './auction-terms-body-master-dialog.component.html'
})
export class AuctionTermsBodyMasterDialogComponent implements OnInit {
    auctionTermsBodyMaster: AuctionTermsBodyMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auctionTermsBodyMasterService: AuctionTermsBodyMasterService,
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
        if (this.auctionTermsBodyMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.auctionTermsBodyMasterService.update(this.auctionTermsBodyMaster));
        } else {
            this.subscribeToSaveResponse(this.auctionTermsBodyMasterService.create(this.auctionTermsBodyMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AuctionTermsBodyMaster>>) {
        result.subscribe(
            (res: HttpResponse<AuctionTermsBodyMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<AuctionTermsBodyMaster>) {
        this.eventManager.broadcast({ name: 'auctionTermsBodyMasterListModification', content: 'OK' });
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
    selector: 'jhi-auction-terms-body-master-popup',
    template: ''
})
export class AuctionTermsBodyMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionTermsBodyMasterPopupService: AuctionTermsBodyMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionTermsBodyMasterPopupService.open(AuctionTermsBodyMasterDialogComponent as Component, params['id']);
            } else {
                this.auctionTermsBodyMasterPopupService.open(AuctionTermsBodyMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
