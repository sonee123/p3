import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { NgForm } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

// D3 Chart
import * as d3 from 'd3-selection';
import * as d3Scale from 'd3-scale';
import * as d3Shape from 'd3-shape';
import * as d3Array from 'd3-array';
import * as d3Axis from 'd3-axis';
import { Auction, AuctionService } from 'app/entities/auction';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsBuyerMaster, RnsBuyerMasterService } from 'app/entities/rns-buyer-master';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { RnsUomMaster, RnsUomMasterService } from 'app/entities/rns-uom-master';
import { RnsVendorRemark, RnsVendorRemarkService } from 'app/entities/rns-vendor-remark';
import { RnsSectorMaster, RnsSectorMasterService } from 'app/entities/rns-sector-master';
import { RnsTaxTermsMaster, RnsTaxTermsMasterService } from 'app/entities/rns-tax-terms-master';
import { RnsDelTermsMaster, RnsDelTermsMasterService } from 'app/entities/rns-del-terms-master';
import { Template, TemplateService } from 'app/entities/template';
import { Currency, CurrencyService } from 'app/entities/currency';
import { RnsPayTermsMaster, RnsPayTermsMasterService } from 'app/entities/rns-pay-terms-master';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';
import { RnsDelPlaceMaster, RnsDelPlaceMasterService } from 'app/entities/rns-del-place-master';
import { VndrPrice, VndrPriceService, VndrPriceSocketService } from 'app/entities/vndr-price';
import { Account, BaseEntityVndrPrice, LoginModalService, Principal, UserService } from 'app/core';
import { AuctionPauseDetails, AuctionPauseDetailsService, AuctionPausedSocketService } from 'app/entities/auction-pause-details';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import { VariantDataService } from 'app/vendor/variant-data.service';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsmain.auctionlisting.component.html'
})
export class RnsmainAuctionListingComponent implements OnInit, OnDestroy {
    account: Account;
    auctions: Auction[];
    auction: Auction;
    subscribtion: Subscription;
    rnsCatgMasters: RnsCatgMaster[];
    rnsCatgMaster: RnsCatgMaster;
    rnsRefrDetails: RnsRefrDetails[];
    rnsBuyerMasters: RnsBuyerMaster[];
    rnsTypeMaster: RnsTypeMaster;
    rnsTypeMasters: RnsTypeMaster[];
    rnsUomMasters: RnsUomMaster[];
    rnsVendorRemarks: RnsVendorRemark[];
    rnsVendorRemark: RnsVendorRemark;
    rnsUomMaster: RnsUomMaster;
    rnsSectorMasters: RnsSectorMaster[];
    rnsSectorMaster: RnsSectorMaster;
    rnsTypeMastersResponseData: RnsTypeMaster[];
    rnsSectorMastersResponseData: RnsSectorMaster[];
    rnsTaxTermsMasters: RnsTaxTermsMaster[];
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    rnsDelTermsMasters: RnsDelTermsMaster[];
    rnsDelTermsMaster: RnsDelTermsMaster;
    templates: Template[];
    currencies: Currency[];
    rnsDelTermsMastersResponseData: RnsDelTermsMaster[];
    rnsPayTermsMasters: RnsPayTermsMaster[];
    rnsPayTermsMaster: RnsPayTermsMaster;
    rnsPayTermsMastersResponseData: RnsPayTermsMaster[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    variants: RnsQuotationVariant[];
    variantsTemp: RnsQuotationVariant[];
    variant: RnsQuotationVariant;
    vendors: RnsQuotationVendors[];
    chartData: RnsQuotationVendors[];
    rnsDelPlaceMaster: RnsDelPlaceMaster;
    rnsDelPlaceMasters: RnsDelPlaceMaster[];
    article: Object;
    routeSub: any;
    targetPcd: Date;
    saveQuotationText: string;
    variantDisplay: string;
    variantDisplaySurrogate: string;
    subcodeInput: any;
    dealtermCompletionByDp: any;
    dealtermValidUntil: any;
    varDescSpecfields: any;
    varDescSpecValues: any[];
    selectedTemplates: any;
    selectedTemplatesSelect: any;
    rnsQuotationCategory: String;
    timer: any;
    minDate: any;
    settings = {
        bigBanner: true,
        timePicker: true,
        format: 'dd/MM/yyyy hh:mm a',
        defaultOpen: false
    };
    isValidQuotationFormSubmitted: Boolean;
    rnsQuotationForm: NgForm;
    payTermsCodeCopy: any;
    delTermsCodeCopy: any;
    displaychatBody: Boolean;
    validityExpired: Boolean;
    workFlowText: String;
    isSaving: Boolean;
    vndrPrice: VndrPrice;
    vndrPrices: VndrPrice[];
    biddingStartDate: any;
    lotNames: any;

    variantStartTime: any;
    variantEndTime: any;
    variantTimeLeft: any;
    variantOverTime: any;
    variantMsg: any;
    variantTimeMsg: any;
    isAuctionClosed: Boolean;
    runningVariantId: number;

    // D3 Variables
    revisionList: BaseEntityVndrPrice[];
    revisionListCall: BaseEntityVndrPrice[];
    Temperatures: any;
    svg: any;
    margin = 50;
    g: any;
    width: number;
    height: number;
    line;
    yAxisGrid: any;

    // paused
    paused: any;
    auctionPauseDetails: AuctionPauseDetails;
    messageAlert: string;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private rnsSectorMasterService: RnsSectorMasterService,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsUomMasterService: RnsUomMasterService,
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private rnsVendorRemarkService: RnsVendorRemarkService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private rnsQuotationService: RnsQuotationService,
        private auctionService: AuctionService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private templateService: TemplateService,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        private currencyService: CurrencyService,
        private userService: UserService,
        private vndrPriceService: VndrPriceService,
        private vndrPriceSocketService: VndrPriceSocketService,
        private auctionPausedSocketService: AuctionPausedSocketService,
        private comparentchildservice: ComParentChildService,
        private auctionPauseDetailsService: AuctionPauseDetailsService,
        private variantDataService: VariantDataService,
        private notifier: NotifierService
    ) {}

    loadAll() {
        this.isValidQuotationFormSubmitted = true;
    }

    ngOnInit() {
        this.paused = false;
        this.eventSubscriber = this.comparentchildservice.on('call-parent-auction').subscribe(() => this.ngOnInit());
        this.eventSubscriber = this.comparentchildservice.on('call-parent-extendreduce').subscribe(() => this.auctionExtendReduce());

        this.loadAll();
        this.checkQuotationDetails();
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.rnsQuotation = {};
        this.varDescSpecValues = [];
        this.variant = {};
        this.variants = [{ title: 'Variant 1', vendors: [], quotation: this.rnsQuotation }];
        this.variantsTemp = [];
        this.vendors = [];
        this.auction = {};
        this.revisionList = [];
        this.revisionListCall = [];
        this.rnsTaxTermsMaster = {};
        this.chartData = [];
        this.subcodeInput = {
            input1: { display: true, data: [] },
            input2: { display: true, data: [] },
            input3: { display: true, data: [] },
            input4: { display: true, data: [] },
            input5: { display: true, data: [] }
        };

        this.lotNames = this.variantDataService.fetchVariant();
    }

    ngOnDestroy() {
        // this.auctionPausedSocketService.disconnect();
        // this.vndrPriceSocketService.disconnect();
        if (this.subscribtion) {
            this.subscribtion.unsubscribe();
            this.subscribtion = null;
        }
    }

    dateformater(date: Date): string {
        const map = new Map<number, string>();
        map.set(0, 'Jan');
        map.set(1, 'Feb');
        map.set(2, 'Mar');
        map.set(3, 'Apr');
        map.set(4, 'May');
        map.set(5, 'Jun');
        map.set(6, 'Jul');
        map.set(7, 'Aug');
        map.set(8, 'Sep');
        map.set(9, 'Oct');
        map.set(10, 'Nov');
        map.set(11, 'Dec');

        let minutes: any;
        if (date.getMinutes() > 9) {
            minutes = date.getMinutes();
        } else {
            minutes = '0' + date.getMinutes();
        }

        let hours: any;
        if (date.getHours() > 9) {
            hours = date.getHours();
        } else {
            hours = '0' + date.getHours();
        }

        const dateFormat = date.getDate() + '-' + map.get(date.getMonth()) + '-' + date.getFullYear() + ' ' + hours + ':' + minutes;
        return dateFormat;
    }

    checkQuotationDetails() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                const id = params['id'];
                this.auctionPausedSocketService.subscribe(id);
                this.auctionPausedSocketService.receive().subscribe(activity => {
                    this.auctionPauseDetails = activity.body;
                    if (this.auctionPauseDetails && this.auctionPauseDetails.pauseEndDate === null) {
                        this.paused = true;
                        if (this.auctionPauseDetails.createdBy === this.account.login) {
                            this.messageAlert = 'Bidding Paused Successfully!!!';
                            this.notifier.notify('success', this.messageAlert);
                        }
                    } else if (this.auctionPauseDetails) {
                        this.paused = false;
                        if (this.auctionPauseDetails.createdBy === this.account.login) {
                            this.messageAlert = 'Bidding Resume Successfully!!!';
                            this.notifier.notify('success', this.messageAlert);
                        }
                        this.callOvertime();
                    } else {
                        this.messageAlert = 'Time Revised in lots!!!';
                        this.notifier.notify('success', this.messageAlert);
                        this.callOvertime();
                    }
                });
                this.rnsQuotationService.findFullAuctionDetails(id).subscribe((rnsQuotationdata: HttpResponse<RnsQuotation>) => {
                    this.biddingStartDate = this.dateUtils.convertDateTimeFromServer(rnsQuotationdata.body.auctionDetails.biddingStartTime);
                    rnsQuotationdata.body.validity = this.datePipe.transform(rnsQuotationdata.body.validity, 'yyyy-MM-ddTHH:mm:ss');
                    rnsQuotationdata.body.createdOn = this.datePipe.transform(rnsQuotationdata.body.createdOn, 'yyyy-MM-ddTHH:mm:ss');
                    rnsQuotationdata.body.date = this.datePipe.transform(rnsQuotationdata.body.date, 'yyyy-MM-ddTHH:mm:ss');

                    if (rnsQuotationdata.body.targetPcd) {
                        this.targetPcd = rnsQuotationdata.body.targetPcd;
                        rnsQuotationdata.body.targetPcd = {
                            year: rnsQuotationdata.body.targetPcd.getFullYear(),
                            month: rnsQuotationdata.body.targetPcd.getMonth() + 1,
                            day: rnsQuotationdata.body.targetPcd.getDate()
                        };
                    }
                    this.rnsQuotation = rnsQuotationdata.body;
                    this.paused = rnsQuotationdata.body.paused;
                    this.auctionPauseDetails = rnsQuotationdata.body.auctionPauseDetails;

                    this.variants = rnsQuotationdata.body.variants;
                    if (this.variantDisplaySurrogate) {
                        this.displayVariant(this.variantDisplaySurrogate);
                    } else {
                        this.displayVariant(rnsQuotationdata.body.displayVariant);
                    }
                    if (this.rnsQuotation.auctionClose === true) {
                    } else {
                        this.refreshBidTime();
                    }
                    const startRefresh = 0;
                    this.variants.forEach(variant => {
                        variant.vendorActive = true;
                        if (variant.dealtermCompletionBy) {
                            variant.dealtermCompletionBy = {
                                year: variant.dealtermCompletionBy.getFullYear(),
                                month: variant.dealtermCompletionBy.getMonth() + 1,
                                day: variant.dealtermCompletionBy.getDate()
                            };
                        }
                        if (variant.dealtermValidUntil) {
                            variant.dealtermValidUntil = {
                                year: variant.dealtermValidUntil.getFullYear(),
                                month: variant.dealtermValidUntil.getMonth() + 1,
                                day: variant.dealtermValidUntil.getDate()
                            };
                        }

                        this.vndrPriceSocketService.subscribe(variant.id);
                        this.vndrPriceSocketService.receive().subscribe(activity => {
                            let callov = 0;
                            activity.body.forEach(data => {
                                if (callov === 0) {
                                    ++callov;
                                    this.callOvertime();
                                }
                                let incr = 0;
                                this.vendors.forEach(vendor => {
                                    incr++;
                                    if (data.vendorCode === vendor.vendorCode) {
                                        vendor.rank = data.rank;
                                        vendor.noRevision = data.revision;
                                        vendor.regularRate = data.price;
                                        vendor.createdOn = data.createdOn;
                                    }
                                    if (incr === this.vendors.length) {
                                        this.rnsQuotationVendorsService
                                            .getRevisionByQuotationId(id)
                                            .subscribe((res: HttpResponse<BaseEntityVndrPrice[]>) => {
                                                this.revisionList = res.body;
                                                this.revisionListCall = [];
                                                this.revisionList.forEach(val => {
                                                    this.vendors.forEach(vendort => {
                                                        if (
                                                            val.title === vendort.variant.title &&
                                                            val.vendorCode === vendort.vendorCode &&
                                                            vendort.vendorActive === true
                                                        ) {
                                                            val.vendorName = vendort.vendor.vendorName;
                                                            this.revisionListCall.push(val);
                                                        }
                                                    });
                                                });
                                                this.subscribeChart();
                                            });
                                    }
                                });
                            });
                        });
                    });
                    this.rnsQuotationVendorsService.getRevisionByQuotationId(id).subscribe((res: HttpResponse<BaseEntityVndrPrice[]>) => {
                        this.revisionList = res.body;
                        this.revisionListCall = [];
                        this.revisionList.forEach(val => {
                            this.vendors.forEach(vendor => {
                                if (
                                    val.title === vendor.variant.title &&
                                    val.vendorCode === vendor.vendorCode &&
                                    vendor.vendorActive === true
                                ) {
                                    val.vendorName = vendor.vendor.vendorName;
                                    this.revisionListCall.push(val);
                                }
                            });
                        });
                        this.subscribeChart();
                    });
                    if (rnsQuotationdata.body.validity != null) {
                        const date = new Date(rnsQuotationdata.body.validity);
                        const todaysDate = new Date();
                        // console.log(date < todaysDate, date > todaysDate)
                        this.validityExpired = false;
                        if (todaysDate > date) {
                            this.validityExpired = true;
                        }
                        this.minDate = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    } else {
                        this.validityExpired = false;
                        this.minDate = new Date();
                    }
                });

                this.rnsQuotationVendorsService.getByQuotationpriceId(id).subscribe(
                    (res: HttpResponse<RnsQuotationVendors[]>) => {
                        this.vendors = res.body;

                        this.vendors.forEach(vendor => {
                            if (vendor.expDelDate != null) {
                                const expDelDatedate = this.dateUtils.convertLocalDateFromServer(vendor.expDelDate);
                                vendor.expDelDatedate = {
                                    year: expDelDatedate.getFullYear(),
                                    month: expDelDatedate.getMonth() + 1,
                                    day: expDelDatedate.getDate()
                                };
                            }
                            vendor.vendorActive = true;
                            if (vendor.currency) {
                            } else {
                                vendor.currency = 'INR';
                            }
                        });
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            } else {
                // console.log('No id FOund..........')
            }
        });
    }

    subscribeChart(refresh = false) {
        // console.log('variantsssssssssssss',this.rnsQuotation);
        this.variants.forEach(variant => {
            let ids: string = 'area' + variant.title;
            ids = ids.replace(/\s/g, '');
            // console.log(ids);
            const chartDataTemp: RnsQuotationVendors[] = [];
            let incr = 0;
            const set = new Set();
            const map = new Map();
            this.revisionList.forEach(val => {
                this.vendors.forEach(vendor => {
                    if (variant.title === vendor.variant.title && vendor.vendorCode === val.vendorCode && vendor.vendorActive === true) {
                        set.add(val.vendorCode);
                        map.set(val.vendorCode, vendor.vendor.vendorName);
                    }
                });
            });
            set.forEach(vCode => {
                incr++;
                const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                rnsQuotationVendors.vendorCode = map.get(vCode);
                const revisionListTemp: BaseEntityVndrPrice[] = [];
                this.revisionList.forEach(val => {
                    if (val.title === variant.title && vCode === val.vendorCode) {
                        val.vendorCode = map.get(val.vendorCode);
                        val.date = new Date(this.datePipe.transform(val.date, 'yyyy-MM-dd HH:mm:ss'));
                        val.dateformated = this.dateformater(val.date);
                        revisionListTemp.push(val);
                    }
                });
                if (revisionListTemp.length > 0) {
                    rnsQuotationVendors.revisionList = revisionListTemp;
                    chartDataTemp.push(rnsQuotationVendors);
                }
            });
            if (chartDataTemp.length > 0 && set.size === incr) {
                this.renderChart(ids, chartDataTemp, refresh, variant);
            } else {
                d3.select('#' + ids + ' > *').remove();
            }
        });
    }

    pausedAuction(variantDisplay) {
        this.variants.forEach((variant, index) => {
            if (variant.title === variantDisplay) {
                if (confirm('Do you want to pause bidding??')) {
                    const auctionPauseDetails = new AuctionPauseDetails();
                    auctionPauseDetails.variantId = variant.id;
                    auctionPauseDetails.quotationId = this.rnsQuotation.id;
                    this.auctionPausedSocketService.sendActivity(auctionPauseDetails, this.rnsQuotation.id);
                    // this.subscribeToSavePausedResponse(this.auctionPauseDetailsService.create(auctionPauseDetails));
                }
            }
        });
    }

    resumeAuction(auctionPauseDetails) {
        if (confirm('Do you want to resume bidding??')) {
            this.auctionPausedSocketService.sendActivity(auctionPauseDetails, this.rnsQuotation.id);
            // this.subscribeToSavePausedResponse(this.auctionPauseDetailsService.update(auctionPauseDetails));
        }
    }

    private auctionExtendReduce() {
        this.auctionPauseDetails.timeAllow = true;
        this.auctionPausedSocketService.sendActivity(this.auctionPauseDetails, this.rnsQuotation.id);
    }

    private subscribeToSavePausedResponse(result: Observable<AuctionPauseDetails>) {
        result.subscribe(
            (res: AuctionPauseDetails) => this.quotationPausedSaved(AuctionPauseDetails),
            (res: Response) => this.onError(Response)
        );
    }

    private quotationPausedSaved(auctionPauseDetails) {
        if (auctionPauseDetails) {
            this.messageAlert = 'Auction Paused Successfully!!!';
            this.notifier.notify('success', this.messageAlert);
        }
    }

    private timerSingle() {
        this.rnsQuotationService.getCurrentTime().subscribe(time => {
            const timeNow = time.body;
            this.timer = timeNow;
            this.variants.forEach((variant, index) => {
                this.diff_minutes(
                    this.dateUtils.convertDateTimeFromServer(timeNow),
                    this.dateUtils.convertDateTimeFromServer(variant.lotStartTime),
                    this.dateUtils.convertDateTimeFromServer(variant.lotEndTime),
                    this.rnsQuotation.auctionDetails.lotRunningTime,
                    variant.overTime
                ).then(data => {
                    variant.startTime = variant.lotStartTime;
                    variant.endTime = data.endTime;
                    variant.lotTime = data.lotTime;
                    variant.overTime = data.overTime;
                    variant.timeLeft = data.timeLeft;
                    variant.isAuctionActive = data.isAuctionActive;
                    variant.isAuctionClosed = data.isAuctionClosed;
                    variant.msg = data.msg;
                    variant.timeMsg = data.timeMsg;
                    variant.paused = data.paused;
                    variant.isAuctionPending = data.isAuctionPending;

                    if (this.variantDisplay === variant.title) {
                        this.variantEndTime = variant.endTime;
                        this.variantStartTime = variant.startTime;
                        this.variantTimeLeft = variant.timeLeft;
                        this.variantOverTime = variant.overTime;
                        this.variantMsg = variant.msg;
                        this.variantTimeMsg = variant.timeMsg;
                        this.isAuctionClosed = variant.isAuctionClosed;
                        this.runningVariantId = variant.id;
                    }
                });
            });
        });
    }

    refreshBidTime() {
        const timer = Observable.timer(2000, 1000);
        this.subscribtion = timer.subscribe(() => {
            this.rnsQuotationService.getCurrentTime().subscribe(time => {
                const timeNow = time.body;
                this.timer = timeNow;
                this.variants.forEach((variant, index) => {
                    this.diff_minutes(
                        this.dateUtils.convertDateTimeFromServer(timeNow),
                        this.dateUtils.convertDateTimeFromServer(variant.lotStartTime),
                        this.dateUtils.convertDateTimeFromServer(variant.lotEndTime),
                        this.rnsQuotation.auctionDetails.lotRunningTime,
                        variant.overTime
                    ).then(data => {
                        variant.startTime = variant.lotStartTime;
                        variant.endTime = data.endTime;
                        variant.lotTime = data.lotTime;
                        variant.overTime = data.overTime;
                        variant.timeLeft = data.timeLeft;
                        variant.isAuctionActive = data.isAuctionActive;
                        variant.isAuctionClosed = data.isAuctionClosed;
                        variant.msg = data.msg;
                        variant.timeMsg = data.timeMsg;
                        variant.paused = data.paused;
                        variant.isAuctionPending = data.isAuctionPending;

                        if (this.variantDisplay === variant.title) {
                            this.variantEndTime = variant.endTime;
                            this.variantStartTime = variant.startTime;
                            this.variantTimeLeft = variant.timeLeft;
                            this.variantOverTime = variant.overTime;
                            this.variantMsg = variant.msg;
                            this.variantTimeMsg = variant.timeMsg;
                            this.isAuctionClosed = variant.isAuctionClosed;
                            this.runningVariantId = variant.id;
                        }
                    });
                });
            });
        });
    }

    private diff_minutes(dt1original, startTime, endTime, lotTime, overTime = 0): Promise<any> {
        const dt1 = new Date(dt1original.getTime());
        const dt2 = new Date(startTime.getTime());
        const dt3 = new Date(endTime.getTime());

        if (dt3.getTime() - dt1.getTime() > 0) {
            if (dt2.getTime() - dt1.getTime() <= 0) {
                const t = dt3.getTime() - dt1.getTime();
                const sec = Math.floor((t / 1000) % 60);
                const min = Math.floor((t / 1000 / 60) % 60);
                const hour = Math.floor((t / (1000 * 60 * 60)) % 24);
                const day = Math.floor(t / (1000 * 60 * 60 * 24));

                if (overTime > 0) {
                    return Promise.resolve({
                        isAuctionActive: true,
                        isAuctionPending: false,
                        isAuctionClosed: false,
                        msg: 'Bidding running in overtime. Pending to Close',
                        startTime: dt2,
                        endTime: dt3,
                        timeMsg: 'Time Remaining:',
                        timeLeft: day + 'D : ' + hour + 'H : ' + min + 'M : ' + sec + 'S',
                        lotTime: lotTime,
                        overTime: overTime
                    });
                } else {
                    return Promise.resolve({
                        isAuctionActive: true,
                        isAuctionPending: false,
                        isAuctionClosed: false,
                        msg: 'Bidding started. Pending to Close',
                        startTime: dt2,
                        endTime: dt3,
                        timeMsg: 'Time Remaining:',
                        timeLeft: day + 'D : ' + hour + 'H : ' + min + 'M : ' + sec + 'S',
                        lotTime: lotTime,
                        overTime: overTime
                    });
                }
            } else {
                const t = dt2.getTime() - dt1.getTime();
                const sec = Math.floor((t / 1000) % 60);
                const min = Math.floor((t / 1000 / 60) % 60);
                const hour = Math.floor((t / (1000 * 60 * 60)) % 24);
                const day = Math.floor(t / (1000 * 60 * 60 * 24));

                return Promise.resolve({
                    isAuctionActive: false,
                    isAuctionPending: true,
                    isAuctionClosed: true,
                    msg: 'Bidding pending to Start',
                    startTime: dt2,
                    endTime: dt3,
                    lotTime: lotTime,
                    overTime: overTime,
                    timeMsg: 'Lot Open in:',
                    timeLeft: day + 'D : ' + hour + 'H : ' + min + 'M : ' + sec + 'S'
                });
            }
        } else {
            return Promise.resolve({
                isAuctionActive: false,
                isAuctionClosed: true,
                msg: 'Bidding has closed.',
                startTime: dt2,
                endTime: dt3,
                lotTime: lotTime,
                overTime: overTime,
                timeLeft: 0
            });
        }
    }

    displayVariantSurrogate(variantTitle) {
        this.variantDisplaySurrogate = variantTitle;
    }

    deleteLastBidVariant(variant, vendor) {
        if (
            confirm(
                'Do you want to delete last bid for ' +
                    vendor.vendor.vendorName +
                    '(' +
                    vendor.vendor.firstName +
                    ' ' +
                    vendor.vendor.lastName +
                    ')'
            )
        ) {
            const vndrPriceData = new VndrPrice();
            vndrPriceData.variant = variant;
            vndrPriceData.vendorCode = vendor.vendorCode;
            this.vndrPriceSocketService.sendDeleteActivity(vndrPriceData, variant.id);
            this.notifier.notify(
                'success',
                'Last bid deleted for Party ' +
                    vendor.vendor.vendorName +
                    '(' +
                    vendor.vendor.firstName +
                    ' ' +
                    vendor.vendor.lastName +
                    ')'
            );
        }
    }

    callOvertime() {
        this.rnsQuotationService.findAuctionDetailVariants(this.rnsQuotation.id).subscribe(rnsQuotationvariantsdata => {
            this.variantsTemp = rnsQuotationvariantsdata.body;
            this.variantsTemp.forEach(variantTemp => {
                this.variants.forEach(variant => {
                    if (variant.id === variantTemp.id) {
                        variant.overTime = variantTemp.overTime;
                        variant.lotStartTime = variantTemp.lotStartTime;
                        variant.lotEndTime = variantTemp.lotEndTime;
                    }
                });
            });
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    displayVariant(variantName) {
        this.variantDisplay = variantName;
        this.variantDisplay = variantName;

        this.variants.forEach((variant, index) => {
            if (this.variantDisplay === variant.title) {
                this.variantEndTime = variant.endTime;
                this.variantStartTime = variant.startTime;
                this.variantTimeLeft = variant.timeLeft;
                this.variantOverTime = variant.overTime;
                this.runningVariantId = variant.id;
                this.timerSingle();
            }
        });
    }

    noSubmit() {
        return false;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private generateQuoation() {
        // console.log('Generating Quotation ...');
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    renderChart(ids, chartDataTemp: RnsQuotationVendors[], refresh = false, variant: RnsQuotationVariant) {
        // var data:any = chartDataTemp.map((v) => v.revisionList.map((v) => new Date(this.datePipe.transform(v.date, 'yyyy-MM-dd HH:mm:ss')) ))[0];
        const dates = [];
        chartDataTemp.forEach(val => {
            val.revisionList.forEach(revval => {
                dates.push(new Date(this.datePipe.transform(revval.date, 'yyyy-MM-dd HH:mm:ss')));
            });
        });
        const data: any = [new Date(Math.min.apply(null, dates)), new Date(Math.max.apply(null, dates))];
        // console.log('dataaaaaaaaaaaaa',data);
        if (typeof data !== 'undefined') {
            let firstDate = data[0];
            firstDate = firstDate.getTime() - 1 * 30000;
            data[0] = new Date(firstDate);

            if (variant.historicalPrice) {
                const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                rnsQuotationVendors.vendorCode = 'Historical Price (' + variant.historicalPrice + ' ' + variant.currency + ')';
                const revisionListTemp: BaseEntityVndrPrice[] = [];

                const revisionMin: BaseEntityVndrPrice = new BaseEntityVndrPrice();
                revisionMin.vendorCode = 'Historical Price';
                revisionMin.date = data[0];
                console.log('sonyyyyyyyyyyyyy', data[0]);
                revisionMin.dateformated = this.dateformater(data[0]);
                revisionMin.value = variant.historicalPrice;
                revisionListTemp.push(revisionMin);

                const revisionMax: BaseEntityVndrPrice = new BaseEntityVndrPrice();
                revisionMax.vendorCode = 'Historical Price';
                revisionMax.date = data[1];
                revisionMax.dateformated = this.dateformater(data[1]);
                revisionMax.value = variant.historicalPrice;
                revisionListTemp.push(revisionMax);
                rnsQuotationVendors.revisionList = revisionListTemp;

                chartDataTemp.push(rnsQuotationVendors);
            }
            this.initChart(ids, chartDataTemp, data, refresh, variant);
        }
    }

    private initChart(ids, chartData: RnsQuotationVendors[], data, refresh = false, variant: RnsQuotationVariant): void {
        // console.log('Line 1194---');
        if (chartData.length > 0 && data.length > 0) {
            // console.log('Line 1196---',ids);

            this.width = 500;
            this.height = 300;

            this.yAxisGrid = d3Scale.scaleLinear().range([this.height, 0]);

            /* Scale */
            const x: any = d3Scale.scaleTime().range([0, this.width - this.margin]);

            const x2: any = d3Scale.scaleTime().range([0, this.width - this.margin]);

            const y: any = d3Scale.scaleLinear().range([this.height - this.margin, 0]);

            const z: any = d3Scale.scaleOrdinal(d3Scale.schemeCategory10);

            /* Remove SVG */
            d3.select('#' + ids + ' > *').remove();

            /* Add SVG */
            this.svg = d3
                .select('#' + ids)
                .attr('width', this.width + this.margin + 'px')
                .attr('height', this.height + this.margin + 'px');

            this.g = this.svg.append('g').attr('transform', 'translate(' + this.margin + ',' + this.margin + ')');

            this.line = d3Shape
                .line()
                // .curve(d3Shape.curveBasis)
                .x((d: any) => x(new Date(this.datePipe.transform(d.date, 'yyyy-MM-dd HH:mm:ss'))))
                .y((d: any) => y(d.value));

            x.domain(d3Array.extent(data, (d: Date) => d));

            y.domain([
                d3Array.min(chartData, function(c) {
                    return d3Array.min(c.revisionList, function(d) {
                        return d.value;
                    });
                }) - 2,
                d3Array.max(chartData, function(c) {
                    return d3Array.max(c.revisionList, function(d) {
                        return d.value;
                    });
                })
            ]);

            z.domain(
                chartData.map(function(c) {
                    return c.id;
                })
            );
            // console.log('Line 1190---');
            // if(refresh === false) {
            this.drawAxis(x, y, x2, variant);
            // }
            this.drawPath(chartData, x, y, z);
        }
    }

    private make_y_gridlines() {
        return d3Axis.axisLeft(this.yAxisGrid).ticks(5);
    }

    private drawAxis(x, y, x2, variant: RnsQuotationVariant): void {
        const lineOpacity = '0.25';
        const lineOpacityHover = '0.85';
        const lineStroke = '1.5px';
        const lineStrokeHover = '2.5px';

        const xAxis = d3Axis.axisBottom(x).ticks(5);
        const yAxis = d3Axis.axisLeft(y).ticks(5);
        const x2Axis = d3Axis.axisBottom(x2).ticks(0);

        this.g
            .append('g')
            .attr('class', 'x axis')
            .attr('transform', 'translate(0, ' + (this.height - this.margin) + ')')
            .call(xAxis);

        this.g
            .append('g')
            .attr('class', 'y axis')
            .call(yAxis)
            .append('text')
            .attr('y', 15)
            .attr('transform', 'rotate(-90)')
            .attr('fill', '#000')
            .text('Rates, INR');
    }

    private drawPath(chartData: RnsQuotationVendors[], x, y, z): void {
        const lineOpacity = '0.25';
        const lineOpacityHover = '0.85';
        const otherLinesOpacityHover = '0.1';
        const lineStroke = '1.5px';
        const lineStrokeHover = '2.5px';
        const circleOpacity = '0.85';
        const circleOpacityOnLineHover = '0.25';
        const circleRadius = 3;
        const circleRadiusHover = 6;
        const duration = 250;

        let tooltip = d3
            .select('body')
            .append('div')
            .attr('class', 'tooltip')
            .style('opacity', 0);
        const city = this.g
            .selectAll('.line-group')
            .data(chartData)
            .enter()
            .append('g')
            .attr('class', 'line-group')
            .style('fill', 'white')
            .on('mouseover', function(d, i) {
                city
                    .append('text')
                    .attr('class', 'title-text')
                    .style('fill', h => {
                        if (h.vendorCode != null && h.vendorCode.startsWith('Historical Price')) {
                            return '#7C0A02';
                        } else {
                            return z(i);
                        }
                    })
                    .text(d.vendorCode)
                    .attr('text-anchor', 'middle')
                    .attr('x', 560 / 2)
                    .attr('y', -10);
            })
            .on('mouseout', function(d) {
                city.select('.title-text').remove();
            });
        // console.log('-----------------------2', chartData, city.data());
        city
            .append('path')
            .attr('class', 'line')
            .attr('d', d => this.line(d.revisionList))
            .style('stroke', (d, i) => {
                if (d.vendorCode != null && d.vendorCode.startsWith('Historical Price')) {
                    return '#7C0A02';
                } else {
                    return z(i);
                }
            })
            .style('opacity', lineOpacity)
            .on('mouseover', function(d) {
                d3.selectAll('.line').style('opacity', otherLinesOpacityHover);
                d3.selectAll('.circle').style('opacity', circleOpacityOnLineHover);
                d3
                    .select(this)
                    .style('opacity', lineOpacityHover)
                    .style('stroke-width', lineStrokeHover)
                    .style('cursor', 'pointer');
            })
            .on('mouseout', function(d) {
                d3.selectAll('.line').style('opacity', lineOpacity);
                d3.selectAll('.circle').style('opacity', circleOpacity);
                d3
                    .select(this)
                    .style('stroke-width', lineStroke)
                    .style('cursor', 'none');
            });
        // console.log('-----------------------3');

        /* Add circles in the line */
        const circleGroup = this.g
            .selectAll('circle-group')
            .data(chartData)
            .enter()
            .append('g')
            .style('fill', (d, i) => {
                if (d.vendorCode != null && d.vendorCode.startsWith('Historical Price')) {
                    return '#7C0A02';
                } else {
                    return z(i);
                }
            });
        tooltip = d3.select('body');
        circleGroup
            .selectAll('circle')
            .data(d => d.revisionList)
            .enter()
            .append('g')
            .attr('class', 'circle')
            .on('mouseover', function(d) {
                tooltip
                    .style('cursor', 'pointer')
                    .append('div')
                    .attr('class', 'tooltip')
                    .style('opacity', 0)
                    .html(
                        '<strong> Party:&nbsp;</strong>' +
                            d.vendorCode +
                            '<br>' +
                            '<strong> Rate:&nbsp;</strong>' +
                            d.value +
                            '<br>' +
                            '<strong> Time:&nbsp;</strong>' +
                            d.dateformated
                    )
                    .style('background-color', ' rgba(255,255,255,1)')
                    .style('border', '1px solid #ddd')
                    .style('box-shadow', '4px 4px 12px rgba(0,0,0,.5')
                    .style('border-radius', '15px')
                    .style('padding', '5px 8px 5px 8px')
                    .style('font-family', 'Arial')
                    .style('font-size', '13px')
                    .style('top', d3.event.pageY + 10 + 'px')
                    .style('left', d3.event.pageX + 10 + 'px')
                    .style('opacity', 1);
            })
            .on('mouseout', function(d) {
                tooltip
                    .style('cursor', 'default')
                    .selectAll('.tooltip')
                    .remove();
            })
            .append('circle')
            .attr('cx', function(d) {
                return x(d.date);
            })
            .attr('cy', function(d) {
                return y(d.value);
            })
            .attr('r', circleRadius)
            .on('mouseover', function(d) {
                d3
                    .select(this)
                    .style('cursor', 'pointer')
                    .attr('r', circleRadiusHover);
            })
            .on('mouseout', function(d) {
                d3
                    .select(this)
                    .style('cursor', 'none')
                    .attr('r', circleRadius);
            });
    }

    onCheckBoxSelectAll(variant) {
        console.log(variant);
        if (variant.vendorActive === true) {
            variant.vendorActive = false;
        } else {
            variant.vendorActive = true;
        }
        console.log(variant);
        this.variants.forEach(variantd => {
            this.vendors.forEach(vendor => {
                if (variantd.vendorActive === true && variantd.title === vendor.variant.title) {
                    vendor.vendorActive = true;
                    // console.log(vendor.vendorActive);
                } else if (variantd.title === vendor.variant.title) {
                    vendor.vendorActive = false;
                    // console.log(vendor.vendorActive);
                }
            });
        });
        console.log(this.rnsQuotation.id);
        this.rnsQuotationVendorsService
            .getRevisionByQuotationId(this.rnsQuotation.id)
            .subscribe((res: HttpResponse<BaseEntityVndrPrice[]>) => {
                this.revisionList = res.body;
                this.revisionListCall = [];
                console.log(this.revisionListCall);
                this.revisionList.forEach(val => {
                    this.vendors.forEach(vendor => {
                        if (val.title === vendor.variant.title && val.vendorCode === vendor.vendorCode && vendor.vendorActive === true) {
                            val.vendorName = vendor.vendor.vendorName;
                            this.revisionListCall.push(val);
                        }
                    });
                });
                console.log(this.revisionListCall);
                this.subscribeChart();
            });
    }
    public onCheckBoxClick(vendor) {
        if (vendor.vendorActive === true) {
            vendor.vendorActive = false;
        } else {
            vendor.vendorActive = true;
        }
        this.rnsQuotationVendorsService
            .getRevisionByQuotationId(this.rnsQuotation.id)
            .subscribe((res: HttpResponse<BaseEntityVndrPrice[]>) => {
                this.revisionList = res.body;
                this.revisionListCall = [];
                this.revisionList.forEach(val => {
                    this.vendors.forEach(vendord => {
                        if (val.title === vendord.variant.title && val.vendorCode === vendord.vendorCode && vendord.vendorActive === true) {
                            val.vendorName = vendord.vendor.vendorName;
                            this.revisionListCall.push(val);
                        }
                    });
                });
                this.subscribeChart();
            });
    }

    public changeCollapse(variant) {
        if (!variant.isCollapse || variant.isCollapse === false) {
            variant.isCollapse = true;
        } else {
            variant.isCollapse = false;
        }
    }
}
