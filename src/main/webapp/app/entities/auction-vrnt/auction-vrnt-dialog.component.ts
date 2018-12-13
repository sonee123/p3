import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuctionVrnt } from './auction-vrnt.model';
import { AuctionVrntPopupService } from './auction-vrnt-popup.service';
import { AuctionVrntService } from './auction-vrnt.service';
import { RnsQuotationVariant, RnsQuotationVariantService } from '../rns-quotation-variant';
import { RnsUomMaster } from '../rns-uom-master/rns-uom-master.model';
import { RnsUomMasterService } from '../rns-uom-master/rns-uom-master.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { RnsQuotation } from '../rns-quotation';

@Component({
    selector: 'jhi-auction-vrnt-dialog',
    templateUrl: './auction-vrnt-dialog.component.html'
})
export class AuctionVrntDialogComponent implements OnInit {
    rnsUomMasters: RnsUomMaster[];
    auctionVrnt: AuctionVrnt;
    isSaving: boolean;
    defautltvalue: any;
    oldUomOne: any;
    oldConvFactOne: any;
    rnsQuotation: RnsQuotation;

    rnsquotationvariants: RnsQuotationVariant[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auctionVrntService: AuctionVrntService,
        private rnsUomMasterService: RnsUomMasterService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsQuotationVariantService.query().subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => {
                this.rnsquotationvariants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsUomMasterService.query().subscribe(
            (res: HttpResponse<RnsUomMaster[]>) => {
                this.rnsUomMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        if (this.auctionVrnt.id === undefined) {
            this.auctionVrnt.applicable = true;
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auctionVrnt.id !== undefined) {
            this.subscribeToSaveResponse(this.auctionVrntService.update(this.auctionVrnt));
        } else {
            this.subscribeToSaveResponse(this.auctionVrntService.create(this.auctionVrnt));
        }
    }

    changeConvFact(value) {
        this.defautltvalue = value;
        if (this.defautltvalue === 'KGS') {
            this.auctionVrnt.convFactOne = 1;
        }

        console.log('auctionVrnt.convFactOne ', this.auctionVrnt.convFactOne);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AuctionVrnt>>) {
        result.subscribe((res: HttpResponse<AuctionVrnt>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<AuctionVrnt>) {
        this.eventManager.broadcast({ name: 'auctionVrntListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsQuotationVariantById(index: number, item: RnsQuotationVariant) {
        return item.id;
    }

    blankFields(event) {
        if (event.target.checked) {
            this.oldUomOne = this.auctionVrnt.uomOne;
            this.oldConvFactOne = this.auctionVrnt.convFactOne;
            this.auctionVrnt.uomOne = null;
            this.auctionVrnt.convFactOne = null;
        } else {
            this.auctionVrnt.uomOne = this.oldUomOne;
            this.auctionVrnt.convFactOne = this.oldConvFactOne;
        }
    }
}

@Component({
    selector: 'jhi-auction-vrnt-popup',
    template: ''
})
export class AuctionVrntPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionVrntPopupService: AuctionVrntPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionVrntPopupService.open(AuctionVrntDialogComponent as Component, params['id']);
            } else {
                this.auctionVrntPopupService.open(AuctionVrntDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
