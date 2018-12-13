import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuctionVarDetails } from './auction-var-details.model';
import { AuctionVarDetailsPopupService } from './auction-var-details-popup.service';
import { DateTimeAdapter, OWL_DATE_TIME_FORMATS, OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';
import { MomentDateTimeAdapter, OWL_MOMENT_DATE_TIME_FORMATS } from 'ng-pick-datetime-moment';
import { AuctionPauseDetails, AuctionPausedSocketService } from 'app/entities/auction-pause-details';
import { AuctionVarDetailsService } from 'app/entities/auction-var-details/auction-var-details.service';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

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
    selector: 'jhi-auction-var-details-dialog',
    templateUrl: './auction-var-details-dialog.component.html',
    providers: [
        // `MomentDateTimeAdapter` and `OWL_MOMENT_DATE_TIME_FORMATS` can be automatically provided by importing
        // `OwlMomentDateTimeModule` in your applications root module. We provide it at the component level
        // here, due to limitations of our example generation script.
        { provide: DateTimeAdapter, useClass: MomentDateTimeAdapter, deps: [OWL_DATE_TIME_LOCALE] },
        { provide: OWL_DATE_TIME_FORMATS, useValue: MY_MOMENT_FORMATS }
    ]
})
export class AuctionVarDetailsDialogComponent implements OnInit, OnDestroy {
    auctionVarDetailsList: AuctionVarDetails[];
    auctionPauseDetails: AuctionPauseDetails;
    type: string;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        public auctionVarDetailsService: AuctionVarDetailsService,
        private auctionPausedSocketService: AuctionPausedSocketService
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    ngOnDestroy(): void {
        // this.auctionPausedSocketService.disconnect();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    extendDateTime(auctionVarDetails) {
        if (confirm('Do you want to resume with revised timings?')) {
            this.subscribeToSaveResponse(this.auctionVarDetailsService.post(auctionVarDetails, this.type));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AuctionVarDetails>>) {
        result.subscribe((res: HttpResponse<AuctionVarDetails>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<AuctionVarDetails>) {
        this.isSaving = false;
        this.auctionPausedSocketService.sendReviseActivity(this.auctionPauseDetails, result.body.quotationId);
        this.activeModal.dismiss('cancel');
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-auction-var-details-popup',
    template: ''
})
export class AuctionVarDetailsPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionVarDetailsPopupService: AuctionVarDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id'] && params['type']) {
                this.auctionVarDetailsPopupService.open(AuctionVarDetailsDialogComponent as Component, params['id'], params['type']);
            } else if (params['id']) {
                this.auctionVarDetailsPopupService.open(AuctionVarDetailsDialogComponent as Component, params['id']);
            } else {
                this.auctionVarDetailsPopupService.open(AuctionVarDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
