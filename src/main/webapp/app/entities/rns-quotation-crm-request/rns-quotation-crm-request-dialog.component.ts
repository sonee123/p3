import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationCrmRequest } from './rns-quotation-crm-request.model';
import { RnsQuotationCrmRequestPopupService } from './rns-quotation-crm-request-popup.service';
import { RnsQuotationCrmRequestService } from './rns-quotation-crm-request.service';
import { RnsBuyerMaster, RnsBuyerMasterService } from '../rns-buyer-master';
import { RnsPchMaster, RnsPchMasterService } from '../rns-pch-master';
import { DateTimeAdapter, OWL_DATE_TIME_FORMATS, OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';
import { MomentDateTimeAdapter, OWL_MOMENT_DATE_TIME_FORMATS } from 'ng-pick-datetime-moment';

// See the Moment.js docs for the meaning of these formats:
// https://momentjs.com/docs/#/displaying/format/
export const MY_MOMENT_FORMATS = {
    parseInput: 'DD-MM-YYYY LT',
    fullPickerInput: 'DD-MM-YYYY LT',
    datePickerInput: 'DD-MM-YYYY',
    timePickerInput: 'LT',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY'
};

@Component({
    selector: 'jhi-rns-quotation-crm-request-dialog',
    templateUrl: './rns-quotation-crm-request-dialog.component.html',
    providers: [
        // `MomentDateTimeAdapter` and `OWL_MOMENT_DATE_TIME_FORMATS` can be automatically provided by importing
        // `OwlMomentDateTimeModule` in your applications root module. We provide it at the component level
        // here, due to limitations of our example generation script.
        { provide: DateTimeAdapter, useClass: MomentDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE] },
        { provide: OWL_DATE_TIME_FORMATS, useValue: MY_MOMENT_FORMATS }
    ]
})
export class RnsQuotationCrmRequestDialogComponent implements OnInit {
    rnsQuotationCrmRequest: RnsQuotationCrmRequest;
    isSaving: boolean;

    rnsbuyermasters: RnsBuyerMaster[];

    rnspchmasters: RnsPchMaster[];
    targetPcdDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationCrmRequestService: RnsQuotationCrmRequestService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private rnsPchMasterService: RnsPchMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsBuyerMasterService.query().subscribe(
            (res: HttpResponse<RnsBuyerMaster[]>) => {
                this.rnsbuyermasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsPchMasterService.query().subscribe(
            (res: HttpResponse<RnsPchMaster[]>) => {
                this.rnspchmasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    convertDateTime(date) {
        const day = date.getDate();
        const month = date.getMonth() + 1; // months are zero based
        const year = date.getFullYear();
        const time = date.toLocaleTimeString().replace(/(.*)\D\d+/, '$1');
        date = year + '-' + month + '-' + day + 'T' + time;
        return date;
    }

    save() {
        this.isSaving = true;
        // this.rnsQuotationCrmRequest.date = this.convertDateTime(this.rnsQuotationCrmRequest.date);
        if (this.rnsQuotationCrmRequest.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationCrmRequestService.update(this.rnsQuotationCrmRequest));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationCrmRequestService.create(this.rnsQuotationCrmRequest));
        }
    }

    public setMoment(moment: any): any {
        this.rnsQuotationCrmRequest.date = moment;
        console.log('check rnsQuotationCrmRequest.date :::::::::::::::::::::', this.rnsQuotationCrmRequest.date);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationCrmRequest>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationCrmRequest>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationCrmRequest>) {
        this.eventManager.broadcast({ name: 'rnsQuotationCrmRequestListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsBuyerMasterById(index: number, item: RnsBuyerMaster) {
        return item.id;
    }

    trackRnsPchMasterById(index: number, item: RnsPchMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-quotation-crm-request-popup',
    template: ''
})
export class RnsQuotationCrmRequestPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationCrmRequestPopupService: RnsQuotationCrmRequestPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationCrmRequestPopupService.open(RnsQuotationCrmRequestDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationCrmRequestPopupService.open(RnsQuotationCrmRequestDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
