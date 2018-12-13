import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsCrmRequestMaster } from './rns-crm-request-master.model';
import { RnsCrmRequestMasterPopupService } from './rns-crm-request-master-popup.service';
import { RnsCrmRequestMasterService } from './rns-crm-request-master.service';
import { RnsPchMaster, RnsPchMasterService } from '../rns-pch-master';
import { RnsArticleMaster, RnsArticleMasterService } from '../rns-article-master';
import { RnsBuyerMaster, RnsBuyerMasterService } from '../rns-buyer-master';
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
    selector: 'jhi-rns-crm-request-master-dialog',
    templateUrl: './rns-crm-request-master-dialog.component.html',
    providers: [
        // `MomentDateTimeAdapter` and `OWL_MOMENT_DATE_TIME_FORMATS` can be automatically provided by importing
        // `OwlMomentDateTimeModule` in your applications root module. We provide it at the component level
        // here, due to limitations of our example generation script.
        { provide: DateTimeAdapter, useClass: MomentDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE] },
        { provide: OWL_DATE_TIME_FORMATS, useValue: MY_MOMENT_FORMATS }
    ]
})
export class RnsCrmRequestMasterDialogComponent implements OnInit {
    rnsCrmRequestMaster: RnsCrmRequestMaster;
    isSaving: boolean;
    rnspchmasters: RnsPchMaster[];
    rnsarticlemasters: RnsArticleMaster[];
    rnsbuyermasters: RnsBuyerMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsCrmRequestMasterService: RnsCrmRequestMasterService,
        private rnsPchMasterService: RnsPchMasterService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private rnsArticleMasterService: RnsArticleMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsPchMasterService.query().subscribe(
            (res: HttpResponse<RnsPchMaster[]>) => {
                this.rnspchmasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsArticleMasterService.query().subscribe(
            (res: HttpResponse<RnsArticleMaster[]>) => {
                this.rnsarticlemasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsBuyerMasterService.query().subscribe(
            (res: HttpResponse<RnsBuyerMaster[]>) => {
                this.rnsbuyermasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;

        if (this.rnsCrmRequestMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsCrmRequestMasterService.update(this.rnsCrmRequestMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsCrmRequestMasterService.create(this.rnsCrmRequestMaster));
        }
    }

    public setMoment(moment: any): any {
        this.rnsCrmRequestMaster.date = moment;
        console.log('check rnsQuotationCrmRequest.date :::::::::::::::::::::', this.rnsCrmRequestMaster.date);
    }

    public setMomentTargetPcd(moment: any): any {
        this.rnsCrmRequestMaster.targetPcd = moment;
        console.log('check rnsQuotationCrmRequest.targetPcd :::::::::::::::::::::', this.rnsCrmRequestMaster.targetPcd);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsCrmRequestMaster>>) {
        result.subscribe(
            (res: HttpResponse<RnsCrmRequestMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsCrmRequestMaster>) {
        this.eventManager.broadcast({ name: 'rnsCrmRequestMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsPchMasterById(index: number, item: RnsPchMaster) {
        return item.id;
    }

    trackRnsArticleMasterById(index: number, item: RnsArticleMaster) {
        return item.id;
    }

    trackRnsBuyerMasterById(index: number, item: RnsBuyerMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-crm-request-master-popup',
    template: ''
})
export class RnsCrmRequestMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsCrmRequestMasterPopupService: RnsCrmRequestMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsCrmRequestMasterPopupService.open(RnsCrmRequestMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsCrmRequestMasterPopupService.open(RnsCrmRequestMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
