import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { Subscription, Observable } from 'rxjs/Rx';
import { DatePipe } from '@angular/common';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import 'datatables.net-responsive-bs4';
import * as FileSaver from 'file-saver';
import { ActivatedRoute, Router } from '@angular/router';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsQuotation, RnsQuotationSearch, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsCrmRequestMaster } from 'app/entities/rns-crm-request-master';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from 'app/entities/rns-source-team-master';
import { LoginModalService, Principal } from 'app/core';
import * as moment from 'moment';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsmain.component.html'
})
export class RnsmainComponent implements OnInit {
    account: Account;
    rnsCatgMasters: RnsCatgMaster[];
    rnsRefrDetails: RnsRefrDetails[];
    rnsQuotation: RnsQuotation;
    quotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    rnsCrmRequestMasters: RnsCrmRequestMaster[];
    variants: RnsQuotationVariant[];
    searchArray: RnsCrmRequestMaster[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    generateQuotationText: {};
    publish: {};
    isSaving: boolean;
    todaysDate: any;
    validityExpired: boolean;
    rnsTypeMaster: RnsTypeMaster;
    rnsTypeMasters: RnsTypeMaster[];
    rnsQuotationSearch: RnsQuotationSearch;
    tempFromDate: any;
    tempToDate: any;
    rnsTypeMastersResponseData: RnsTypeMaster[];
    routeSub: any;
    isWait: boolean;
    rnsSourceTeamMasters: RnsSourceTeamMaster[];

    dtOptions: any = {};

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private router: Router,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils,
        private route: ActivatedRoute,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private notifier: NotifierService
    ) {}

    loadAll() {
        this.todaysDate = new Date();
        this.rnsCatgMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnsCatgMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsSourceTeamMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsSourceTeamMaster[]>) => {
                this.rnsSourceTeamMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.isWait = true;
        const now = new Date();
        const since = new Date();
        since.setDate(since.getDate() - 30);
        this.rnsQuotationSearch.dateFrom = { year: since.getFullYear(), month: since.getMonth() + 1, day: since.getDate() };
        this.rnsQuotationSearch.dateTo = { year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate() };
        this.rnsQuotationSearch.rfqStatus = 'A';
        this.rnsQuotationSearch.rfbStatus = 'A';
        this.rnsQuotationSearch.workflowStatus = 'A';
        this.tempFromDate = this.rnsQuotationSearch.dateFrom;
        this.tempToDate = this.rnsQuotationSearch.dateTo;
        this.rnsQuotationService.querysearch(this.rnsQuotationSearch).subscribe(
            (res: HttpResponse<RnsQuotation[]>) => {
                this.rnsQuotations = res.body;
                this.rnsQuotations.forEach(quotation => {
                    if (quotation.validity != null) {
                        const date = new Date(quotation.validity);
                        const todaysDate = new Date();
                        // console.log(date < todaysDate, date > todaysDate);
                        quotation.validityExpired = false;
                        if (todaysDate > date) {
                            quotation.validityExpired = true;
                        }
                    }
                });
                this.isWait = false;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
        this.rnsQuotationSearch.dateFrom = this.tempFromDate;
        this.rnsQuotationSearch.dateTo = this.tempToDate;
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                responsive: false,
                paging: true,
                bInfo: false,
                bFilter: false,
                bAutoWidth: false,
                bLengthChange: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [0, 'desc']
            });
            $('#exampleFixed').removeClass('collapsed');
        });
    }

    rfqFilter(type) {
        this.rnsQuotationSearch.rfqStatus = type;
    }

    rfbFilter(type) {
        this.rnsQuotationSearch.rfbStatus = type;
    }

    workFilter(type) {
        this.rnsQuotationSearch.workflowStatus = type;
    }

    generateReport(rnsQuotation) {
        this.isWait = true;
        this.rnsQuotationService.downloadPdf(rnsQuotation.id).subscribe(
            res => {
                /*var binaryData = [];
            binaryData.push(res);
            window.URL.createObjectURL(new Blob(binaryData, {type: 'application/pdf'}))
            */
                console.log(res);
                FileSaver.saveAs(res, rnsQuotation.id + '.pdf');
                this.isWait = false;
                /*const url = window.URL.createObjectURL(res);
            const a = document.createElement('a');
            document.body.appendChild(a);
            a.setAttribute('style', 'display: none');
            a.href = url;
            a.download = res.filename;
            a.click();
            window.URL.revokeObjectURL(url);
            a.remove(); // remove the element*/
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    changeCategory() {
        if (this.rnsQuotationSearch.catgCode != null) {
            const code = this.rnsQuotationSearch.catgCode.id;
            this.rnsTypeMasterService.query().subscribe(
                (res: HttpResponse<RnsTypeMaster[]>) => {
                    this.rnsTypeMastersResponseData = res.body;
                    this.rnsTypeMasters = [];
                    this.routeSub = this.route.params.subscribe(params => {
                        for (const item in this.rnsTypeMastersResponseData) {
                            if (this.rnsTypeMastersResponseData[item].rnsCatgCode.id === code) {
                                this.rnsTypeMasters.push(this.rnsTypeMastersResponseData[item]);
                            }
                        }
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    setStyles() {
        const styles = {
            // CSS property names
            visibility: this.isWait ? 'visible' : 'hidden'
        };
        return styles;
    }

    clickAlert(msg) {
        if (msg !== null) {
            this.notifier.notify('error', msg);
        }
    }

    searchQuery() {
        console.log(this.rnsQuotationSearch.dateFrom);
        if (this.rnsQuotationSearch.dateFrom == null) {
            this.notifier.notify('error', 'Please choose Date From...');
            return false;
        }
        if (this.rnsQuotationSearch.dateTo == null) {
            this.notifier.notify('error', 'Please choose Date To...');
            return false;
        }
        this.isWait = true;
        this.tempFromDate = this.rnsQuotationSearch.dateFrom;
        this.tempToDate = this.rnsQuotationSearch.dateTo;
        this.rnsQuotationService.querysearch(this.rnsQuotationSearch).subscribe(
            (res: HttpResponse<RnsQuotation[]>) => {
                (<any>$('#exampleFixed')).DataTable().destroy();
                this.rnsQuotations = res.body;
                console.log('this.rnsQuotations', this.rnsQuotations);
                this.rnsQuotations.forEach(quotation => {
                    if (quotation.validity != null) {
                        const date = new Date(quotation.validity);
                        const todaysDate = new Date();
                        quotation.validityExpired = false;
                        if (todaysDate > date) {
                            quotation.validityExpired = true;
                        }
                    }
                });
                this.isWait = false;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
        this.rnsQuotationSearch.dateFrom = this.tempFromDate;
        this.rnsQuotationSearch.dateTo = this.tempToDate;
    }

    ngOnInit() {
        this.rnsQuotationSearch = new RnsQuotationSearch();
        this.loadAll();
        this.isWait = false;
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.rnsQuotation = {};
        this.variants = [{ title: 'Variant 1', vendors: [], quotation: this.rnsQuotation }];
        this.isSaving = true;
        this.generateQuotationText = 'New';
        this.registerAuthenticationSuccess();
        this.registerChangeInRnsCatgMasters();
        this.registerChangeInRnsRefrDetails();

        this.eventManager.subscribe('rnsQuotationupdateValidatity', response => {
            this.rnsQuotations.forEach(quotation => {
                if (quotation.id === response.content.id) {
                    quotation.validity = response.content.validity;
                    if (quotation.validity != null) {
                        this.rnsQuotationService.getCurrentTime().subscribe(time => {
                            const todaysDate = this.dateUtils.convertDateTimeFromServer(time.body);
                            const date = new Date(quotation.validity);
                            if (todaysDate > date) {
                                quotation.validityExpired = true;
                                quotation.rfqStatus = 'btn-success';
                            } else {
                                quotation.validityExpired = false;
                                quotation.rfqStatus = 'btn-danger';
                            }
                        });
                    }
                }
            });
        });
        this.registerChangeInRnsQuotations();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    registerChangeInRnsCatgMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsCatgMasterListModification', response => this.searchQuery());
    }

    registerChangeInRnsRefrDetails() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRefrDetailsListModification', response => this.searchQuery());
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    generateQuotation() {
        console.log('Category Code check', this.rnsQuotation.rnsCatgCode);
        if (this.rnsQuotation.rnsCatgCode) {
            if (this.isSaving) {
                console.log('Generating Quotation ...');
                this.generateQuotationText = 'Generating Quotation ...';
                this.router.navigateByUrl('/quotation/' + this.rnsQuotation.rnsCatgCode.id + '/new');
                // this.subscribeToSaveResponse(
                // this.rnsQuotationService.create(this.rnsQuotation));
            }
        } else {
            this.notifier.notify('error', 'Please Select category type.');
        }
    }

    private updateVariants(rnsQuotation) {
        this.variants.forEach(variant => {
            variant.quotation = rnsQuotation;
        });
        this.subscribeToSaveVariantResponse(this.rnsQuotationVariantService.updateMultiple(this.variants));
    }

    publishQuotation(rnsQuotationData, eventData) {
        console.log(rnsQuotationData);
        rnsQuotationData.ispublishing = true;
        const publishUpdates = {
            id: rnsQuotationData.id,
            published: true
        };
        this.subscribeToSavePublishResponse(this.rnsQuotationService.publish(publishUpdates), rnsQuotationData);
    }

    private subscribeToSavePublishResponse(result: Observable<HttpResponse<RnsQuotation>>, rnsQuotationData) {
        result.subscribe(
            (res: HttpResponse<RnsQuotation>) => this.onSavePublishSuccess(res, rnsQuotationData),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSavePublishSuccess(result: HttpResponse<RnsQuotation>, rnsQuotationData) {
        rnsQuotationData.ispublishing = false;
    }

    private subscribeToSaveVariantResponse(result: Observable<HttpResponse<RnsQuotationVariant[]>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => this.quotationVariantSavedVariant(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private quotationSaved(result: HttpResponse<RnsQuotation>) {
        this.quotation = result.body;
        const copy = this.convert(result.body);
        if (this.variants.length > 0) {
            this.updateVariants(copy);
        } else {
            this.generateQuotationText = 'Generate Quotation';
        }
    }

    private quotationVariantSavedVariant(result: HttpResponse<RnsQuotationVariant[]>) {
        const copy = this.convert(this.rnsQuotation);

        const quotation_id = this.quotation.id;
        this.generateQuotationText = 'Quotation Generated, Redirecting ...';
        this.router.navigateByUrl('/quotation/' + quotation_id + '/edit');
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotation>) {
        // console.log('Response', Response);
        this.eventManager.broadcast({ name: 'rnsQuotationListModification', content: 'OK' });
        this.notifier.notify('success', result.body.errorMessage);
        this.isSaving = false;

        if (result.body.id) {
            const quotation_id = result.body.id;
            // this.generateQuotationText = 'Quotation Generated, Redirecting ...';
            this.searchQuery();
            // this.router.navigateByUrl('home');
        } else {
            this.generateQuotationText = 'Generate Quotation';
            this.notifier.notify('error', 'Some Error occured Please contact Administrator');
        }
    }

    private onSaveError() {
        this.isSaving = false;
        console.log('Error while saving');
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    updateRfq(rnsQuotation) {
        const date = new Date(rnsQuotation.validity);
        const todaysDate = new Date();
        this.validityExpired = false;
        if (todaysDate > date) {
            this.validityExpired = true;
            this.notifier.notify('error', 'Please Enter RFQ Valid Date!!!');
        } else {
            if (confirm('Are you sure to publish RFQ for quotation# ' + rnsQuotation.id + ' ?')) {
                console.log('rnsQuotation', rnsQuotation);
                this.subscribeToSaveRFQResponse(this.rnsQuotationService.rfq(rnsQuotation), rnsQuotation);
            }
        }
    }

    private subscribeToSaveRFQResponse(result: Observable<HttpResponse<RnsQuotation>>, rnsQuotation) {
        result.subscribe(
            (res: HttpResponse<RnsQuotation>) => this.onRFQSaveSuccess(res, rnsQuotation),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotation>>) {
        result.subscribe((res: HttpResponse<RnsQuotation>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onRFQSaveSuccess(result: HttpResponse<RnsQuotation>, rnsQuotation) {
        this.eventManager.broadcast({ name: 'rnsQuotationListModification', content: 'OK' });
        this.isSaving = false;
        this.notifier.notify('success', result.body.errorMessage);
        if (result.body.errorMessage.startsWith('RFQ published successfully')) {
            rnsQuotation.rfq = true;
        }
    }

    updateAuction(rnsQuotation) {
        if (rnsQuotation.auctionValidate === false) {
            this.notifier.notify('error', 'Cannot Activate Auction, Auction  not Entered.');
        } else {
            if (confirm('Are you sure to publish Aution for quotation# ' + rnsQuotation.id + ' ?')) {
                this.subscribeToSaveResponse(this.rnsQuotationService.auction(rnsQuotation));
            }
        }
    }

    /**
     * Convert a RnsQuotation to a JSON which can be sent to the server.
     */
    private convert(rnsQuotation: RnsQuotation): RnsQuotation {
        const copy: RnsQuotation = Object.assign({}, rnsQuotation);

        // copy.validity = this.dateUtils.toDate(rnsQuotation.validity);
        // copy.createdOn = this.dateUtils.toDate(rnsQuotation.createdOn);
        // copy.date = this.dateUtils.toDate(rnsQuotation.date);

        copy.validity = rnsQuotation.validity != null ? moment(rnsQuotation.validity) : null;
        copy.createdOn = rnsQuotation.createdOn != null ? moment(rnsQuotation.createdOn) : null;
        copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotation.targetPcd);
        copy.date = rnsQuotation.date != null ? moment(rnsQuotation.date) : null;
        return copy;
    }

    registerChangeInRnsQuotations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsQuotationListModification', response => this.searchQuery());
    }

    public dateFrom() {}

    public dateTo() {}
}
