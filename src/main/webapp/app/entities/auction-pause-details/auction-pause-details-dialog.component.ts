import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuctionPauseDetails } from './auction-pause-details.model';
import { AuctionPauseDetailsPopupService } from './auction-pause-details-popup.service';
import { AuctionPauseDetailsService } from './auction-pause-details.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-pause-details-dialog',
    templateUrl: './auction-pause-details-dialog.component.html'
})
export class AuctionPauseDetailsDialogComponent implements OnInit {
    auctionPauseDetails: AuctionPauseDetails;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auctionPauseDetailsService: AuctionPauseDetailsService,
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
        if (this.auctionPauseDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.auctionPauseDetailsService.update(this.auctionPauseDetails));
        } else {
            this.subscribeToSaveResponse(this.auctionPauseDetailsService.create(this.auctionPauseDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AuctionPauseDetails>>) {
        result.subscribe(
            (res: HttpResponse<AuctionPauseDetails>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<AuctionPauseDetails>) {
        this.eventManager.broadcast({ name: 'auctionPauseDetailsListModification', content: 'OK' });
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
    selector: 'jhi-auction-pause-details-popup',
    template: ''
})
export class AuctionPauseDetailsPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionPauseDetailsPopupService: AuctionPauseDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionPauseDetailsPopupService.open(AuctionPauseDetailsDialogComponent as Component, params['id']);
            } else {
                this.auctionPauseDetailsPopupService.open(AuctionPauseDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
