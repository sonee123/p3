import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { JhiDateUtils } from 'ng-jhipster';

import { RnsQuotationPopupService } from './rns-quotation-popup.service';
import { RnsQuotationService } from './rns-quotation.service';
import {BiddingCompareModel} from './bidding-compare.model';
import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import 'datatables.net-fixedcolumns';

@Component({
    selector: 'jhi-rns-compare-quotation-dialog',
    templateUrl: './rns-quotation-compare-dialog.component.html',
    styleUrls: [
    ],
    styles: [`
        .DTFC_LeftBodyWrapper{
            margin-top: -6px!important;
        }
    `]
})
export class RnsQuotationCompareDialogComponent implements OnInit {

    biddingCompare: BiddingCompareModel;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationService: RnsQuotationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable( {
                scrollY:        false,
                scrollX:        true,
                scrollCollapse: true,
                paging:         false,
                'bSort':        false,
                'bFilter':      false,
                'bInfo':        false,
                fixedColumns:   {
                    leftColumns: 3
                }
            } );
        } );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-compare-quotation-popup',
    template: ''
})
export class RnsQuotationComparePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rnsQuotationPopupService: RnsQuotationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.rnsQuotationPopupService
                    .compare(RnsQuotationCompareDialogComponent as Component, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
