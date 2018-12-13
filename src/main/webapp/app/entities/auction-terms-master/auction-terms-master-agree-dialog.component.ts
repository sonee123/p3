import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuctionTermsMaster } from './auction-terms-master.model';
import { AuctionTermsMasterPopupService } from './auction-terms-master-popup.service';
import { AuctionTermsMasterService } from './auction-terms-master.service';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';
import { ComParentChildService } from '../../rnsmain/com-parent-child.service';

@Component({
    selector: 'jhi-auction-terms-master-agree-dialog',
    templateUrl: './auction-terms-master-agree-dialog.component.html'
})
export class AuctionTermsMasterAgreeDialogComponent implements OnInit {
    rnsQuotation: RnsQuotation;
    auctionTermsMaster: AuctionTermsMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auctionTermsMasterService: AuctionTermsMasterService,
        private rnsQuotationService: RnsQuotationService,
        private eventManager: JhiEventManager,
        private comparentchildservice: ComParentChildService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        if (this.rnsQuotation != null) {
            this.auctionTermsMaster = new AuctionTermsMaster();
            this.auctionTermsMaster.categoryId = this.rnsQuotation.rnsCatgCode.id;
            this.auctionTermsMaster.quoteTypeCode = this.rnsQuotation.quoteType;
            this.auctionTermsMaster.sourceTeam = Number(this.rnsQuotation.sourceTeam);
            console.log(this.auctionTermsMaster);
            this.auctionTermsMasterService.query().subscribe(auctionTermsMaster => {
                this.auctionTermsMaster = auctionTermsMaster.body[0];
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotation.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationService.agree(this.rnsQuotation.id));
        }
    }
    private subscribeToSaveResponse(result: Observable<HttpResponse<any>>) {
        this.eventManager.broadcast({ name: 'call-parent-auction-vendor', content: 'OK' });
        result.subscribe((res: HttpResponse<any>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<any>) {
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
    selector: 'jhi-auction-terms-master-agree-popup',
    template: ''
})
export class AuctionTermsMasterAgreePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionTermsMasterPopupService: AuctionTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionTermsMasterPopupService.agree(AuctionTermsMasterAgreeDialogComponent as Component, params['id']);
            } else {
                this.auctionTermsMasterPopupService.open(AuctionTermsMasterAgreeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
