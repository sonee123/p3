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
import { RnsQuotation } from '../rns-quotation';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-vrnt-dialog',
    templateUrl: './auction-vrnt-dialog.component.html'
})
export class AuctionVrntDialogGetByVariantComponent implements OnInit {
    rnsUomMasters: RnsUomMaster[];
    auctionVrnt: AuctionVrnt;
    rnsQuotation: RnsQuotation;
    isSaving: boolean;
    variantId: number;
    defautltvalue: any;
    convFactTwo2: any;
    convFactTwo3: any;
    convFactTwo4: any;
    convFactTwo5: any;
    convFactTwo6: any;
    convFactTwo7: any;
    convFactTwo8: any;
    convFactTwo9: any;
    convFactTwo10: any;
    oldUomOne: any;
    oldConvFactOne: any;

    rnsQuotationVariant: RnsQuotationVariant;

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
        this.rnsQuotationVariantService.find(this.auctionVrnt.variant.id).subscribe(
            res => {
                this.rnsQuotationVariant = res.body;
                this.auctionVrnt.variant = res.body;
                this.rnsQuotation = res.body.quotation;
                if (res.body.openCosting != null && res.body.openCosting === 'Required') {
                    this.auctionVrnt.applicable = true;
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsUomMasterService.query().subscribe(
            (res: HttpResponse<RnsUomMaster[]>) => {
                this.rnsUomMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    changeConvFact2(value) {
        this.convFactTwo2 = value;
        if (this.convFactTwo2 === 'KGS') {
            this.auctionVrnt.convFactTwo = 1;
        }

        console.log('convFactTwo2 ', this.convFactTwo2);
    }

    changeConvFact3(value) {
        this.convFactTwo3 = value;
        if (this.convFactTwo3 === 'KGS') {
            this.auctionVrnt.convFactThree = 1;
        }

        console.log('convFactTwo3 ', this.convFactTwo3);
    }

    changeConvFact4(value) {
        this.convFactTwo4 = value;
        if (this.convFactTwo4 === 'KGS') {
            this.auctionVrnt.convFactFour = 1;
        }

        console.log('convFactTwo4 ', this.convFactTwo4);
    }

    changeConvFact5(value) {
        this.convFactTwo5 = value;
        if (this.convFactTwo5 === 'KGS') {
            this.auctionVrnt.convFactFive = 1;
        }

        console.log('convFactTwo5 ', this.convFactTwo5);
    }

    changeConvFact6(value) {
        this.convFactTwo6 = value;
        if (this.convFactTwo6 === 'KGS') {
            this.auctionVrnt.convFactSix = 1;
        }

        console.log('convFactTwo6 ', this.convFactTwo6);
    }

    changeConvFact7(value) {
        this.convFactTwo7 = value;
        if (this.convFactTwo7 === 'KGS') {
            this.auctionVrnt.convFactSeven = 1;
        }

        console.log('convFactTwo7 ', this.convFactTwo7);
    }

    changeConvFact8(value) {
        this.convFactTwo8 = value;
        if (this.convFactTwo8 === 'KGS') {
            this.auctionVrnt.convFactEight = 1;
        }

        console.log('convFactTwo8 ', this.convFactTwo8);
    }

    changeConvFact9(value) {
        this.convFactTwo9 = value;
        if (this.convFactTwo9 === 'KGS') {
            this.auctionVrnt.convFactNine = 1;
        }

        console.log('convFactTwo9', this.convFactTwo9);
    }

    changeConvFact10(value) {
        this.convFactTwo10 = value;
        if (this.convFactTwo10 === 'KGS') {
            this.auctionVrnt.convFactTen = 1;
        }

        console.log('convFactTwo10 ', this.convFactTwo10);
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
}

@Component({
    selector: 'jhi-auction-vrnt-popup',
    template: ''
})
export class AuctionVrntGetByVariantPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionVrntPopupService: AuctionVrntPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionVrntPopupService.openByVariantId(AuctionVrntDialogGetByVariantComponent as Component, params['id']);
            } else {
                this.auctionVrntPopupService.open(AuctionVrntDialogGetByVariantComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
