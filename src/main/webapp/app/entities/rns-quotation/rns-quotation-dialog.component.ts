import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotation } from './rns-quotation.model';
import { RnsQuotationPopupService } from './rns-quotation-popup.service';
import { RnsQuotationService } from './rns-quotation.service';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';

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
    selector: 'jhi-rns-quotation-dialog',
    templateUrl: './rns-quotation-dialog.component.html',
    providers: [
        // `MomentDateTimeAdapter` and `OWL_MOMENT_DATE_TIME_FORMATS` can be automatically provided by importing
        // `OwlMomentDateTimeModule` in your applications root module. We provide it at the component level
        // here, due to limitations of our example generation script.
        { provide: DateTimeAdapter, useClass: MomentDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE] },
        { provide: OWL_DATE_TIME_FORMATS, useValue: MY_MOMENT_FORMATS }
    ]
})
export class RnsQuotationDialogComponent implements OnInit {
    rnsQuotation: RnsQuotation;
    isSaving: boolean;
    momentValue: any;

    rnscatgmasters: RnsCatgMaster[];
    targetPcdDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationService: RnsQuotationService,
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

    convertDateTime(validity) {
        const day = validity.getDate();
        const month = validity.getMonth() + 1; // months are zero based
        const year = validity.getFullYear();
        const time = validity.toLocaleTimeString('en-GB');
        validity = year + '-' + month + '-' + day + 'T' + time;
        return validity;
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotation.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationService.update(this.rnsQuotation));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationService.create(this.rnsQuotation));
        }
    }

    public setMoment(moment: any): any {
        this.rnsQuotation.validity = moment;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotation>>) {
        result.subscribe((res: HttpResponse<RnsQuotation>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotation>) {
        this.eventManager.broadcast({ name: 'rnsQuotationupdateValidatity', content: result.body });
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
    selector: 'jhi-rns-quotation-popup',
    template: ''
})
export class RnsQuotationPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationPopupService: RnsQuotationPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationPopupService.open(RnsQuotationDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationPopupService.open(RnsQuotationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
