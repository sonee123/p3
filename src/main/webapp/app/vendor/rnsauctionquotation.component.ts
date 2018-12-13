import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { LocalStorageService } from 'ngx-webstorage';
import { NgForm } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { RnsVendorRemark, RnsVendorRemarkService } from 'app/entities/rns-vendor-remark';
import { RnsUomMaster, RnsUomMasterService } from 'app/entities/rns-uom-master';
import { RnsSectorMaster, RnsSectorMasterService } from 'app/entities/rns-sector-master';
import { RnsDelTermsMaster, RnsDelTermsMasterService } from 'app/entities/rns-del-terms-master';
import { Template, TemplateService } from 'app/entities/template';
import { Currency, CurrencyService } from 'app/entities/currency';
import { RnsPayTermsMaster, RnsPayTermsMasterService } from 'app/entities/rns-pay-terms-master';
import { RnsTaxTermsMaster, RnsTaxTermsMasterService } from 'app/entities/rns-tax-terms-master';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';
import { RnsDelPlaceMaster, RnsDelPlaceMasterService } from 'app/entities/rns-del-place-master';
import { Auction, AuctionService } from 'app/entities/auction';
import { VndrPrice, VndrPriceService, VndrPriceSocketService, VndrRank } from 'app/entities/vndr-price';
import { Account, BaseEntityVndrPrice, LoginModalService, Principal, UserService } from 'app/core';
import { AuctionPauseDetails, AuctionPausedSocketService } from 'app/entities/auction-pause-details';
import { RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { AuctionVrntService } from 'app/entities/auction-vrnt';
import { VariantDataService } from 'app/vendor/variant-data.service';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import * as d3 from 'd3-selection';
import * as d3Axis from 'd3-axis';
import * as d3Shape from 'd3-shape';
import * as d3Array from 'd3-array';
import * as d3Scale from 'd3-scale';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsauctionquotation.component.html'
})
export class RnsAuctionQuotationComponent implements OnInit, OnDestroy {
    account: Account;
    subscribtion: Subscription;
    rnsCatgMaster: RnsCatgMaster;
    rnsTypeMaster: RnsTypeMaster;
    rnsVendorRemarks: RnsVendorRemark[];
    rnsVendorRemark: RnsVendorRemark;
    rnsUomMaster: RnsUomMaster;
    rnsSectorMaster: RnsSectorMaster;
    rnsDelTermsMaster: RnsDelTermsMaster;
    templates: Template[];
    currencies: Currency[];
    currency: string;
    rnsPayTermsMaster: RnsPayTermsMaster;
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    rnsTaxTermsMasters: RnsTaxTermsMaster[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    variants: RnsQuotationVariant[];
    variantsData: RnsQuotationVariant[];
    vendors: RnsQuotationVendors[];
    rnsDelPlaceMaster: RnsDelPlaceMaster;
    rnsDelPlaceMasters: RnsDelPlaceMaster[];
    article: Object;
    routeSub: any;
    targetPcd: Date;
    saveQuotationText: string;
    variantDisplay: string;
    subcodeInput: any;
    dealtermValidUntil: any;
    varDescSpecValues: any[];
    auction: Auction;
    historyContent: any;
    biddingStartDate: any;
    overTime: any;
    priceWrong: Boolean;
    vndrPrice: VndrPrice;
    highestPrice: VndrRank;
    isSaving: Boolean;
    lotNames: any;
    variantsTemp: RnsQuotationVariant[];

    variantStartTime: any;
    variantEndTime: any;
    variantTimeLeft: any;
    variantOverTime: any;
    variantMsg: any;
    variantTimeMsg: any;
    variantAuctionActive: Boolean;
    timer: any;
    variantAuctionApplicable: boolean;

    rfqVndrPrice: number;
    messageAlert: string;

    minDate: any;
    settings = {
        bigBanner: true,
        timePicker: true,
        format: 'dd/MM/yyyy hh:mm a',
        defaultOpen: false
    };
    isValidQuotationFormSubmitted: Boolean;
    rnsQuotationForm: NgForm;
    displaychatBody: Boolean;
    validityExpired: Boolean;
    position: number;

    // D3 Variables
    revisionList: BaseEntityVndrPrice[];
    Temperatures: any;
    svg: any;
    margin = 50;
    g: any;
    width: number;
    height: number;
    line;

    paused: any;
    auctionPauseDetails: AuctionPauseDetails;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsSectorMasterService: RnsSectorMasterService,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsUomMasterService: RnsUomMasterService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
        private rnsVendorRemarkService: RnsVendorRemarkService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private templateService: TemplateService,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        private currencyService: CurrencyService,
        private auctionVrntService: AuctionVrntService,
        private auctionService: AuctionService,
        private storage: LocalStorageService,
        private userService: UserService,
        private vndrPriceService: VndrPriceService,
        private vndrPriceSocketService: VndrPriceSocketService,
        private auctionPausedSocketService: AuctionPausedSocketService,
        private variantDataService: VariantDataService,
        private comparentchildservice: ComParentChildService,
        private notifier: NotifierService
    ) {}

    loadAll() {
        this.isValidQuotationFormSubmitted = true;

        this.currencyService.query().subscribe(
            (res: HttpResponse<Currency[]>) => {
                this.currencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.paused = false;
        this.registerAuthenticationSuccess();
        this.registerAgreeSuccess();
        this.loadAll();
        this.checkQuotationDetails();
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.currency = 'INR';
        this.rnsQuotation = {};
        this.varDescSpecValues = [];
        this.variantsData = [];

        this.variants = [];
        this.variantsTemp = [];
        this.vendors = [];
        this.revisionList = [];
        this.subcodeInput = {
            input1: { display: true, data: [] },
            input2: { display: true, data: [] },
            input3: { display: true, data: [] },
            input4: { display: true, data: [] },
            input5: { display: true, data: [] }
        };
        this.lotNames = this.variantDataService.fetchVariant();
    }

    rejectInvite(rnsQuotation) {
        if (confirm('Do you want to reject invitation!!!')) {
            this.subscribeToSaveResponse(this.rnsQuotationService.disagree(rnsQuotation.id));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<any>>) {
        this.eventManager.broadcast({ name: 'call-parent-auction-vendor', content: 'OK' });
        result.subscribe((res: HttpResponse<any>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<any>) {
        this.ngOnInit();
        this.notifier.notify('success', 'Your rejection request saved!!!');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    ngOnDestroy() {
        // this.vndrPriceSocketService.disconnect();
        // this.auctionPausedSocketService.disconnect();
        console.log('subscribtion', this.subscribtion);
        if (this.subscribtion !== null || this.subscribtion !== undefined) {
            this.subscribtion.unsubscribe();
            this.subscribtion = null;
        }
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
                    } else if (this.auctionPauseDetails) {
                        this.paused = false;
                        this.callOvertime();
                    } else {
                        this.callOvertime();
                    }
                });
                this.rnsQuotationService.findAuctionDetails(id).subscribe(rnsQuotationdata => {
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

                    this.rnsQuotationVendorsService.getRevisionByVendorId(id).subscribe((res: HttpResponse<BaseEntityVndrPrice[]>) => {
                        this.revisionList = res.body;
                        this.subscribeChart();
                    });

                    this.variants = rnsQuotationdata.body.variants;
                    this.displayVariant(rnsQuotationdata.body.displayVariant);

                    let startRefresh = 0;
                    this.variants.forEach(variant => {
                        this.vndrPriceSocketService.subscribe(variant.id);
                        this.vndrPriceSocketService.receive().subscribe(activity => {
                            let ctr = 0;
                            let callov = 0;
                            activity.body.forEach(data => {
                                if (data.vendorCode === this.account.login) {
                                    if (callov === 0) {
                                        ++callov;
                                        this.callOvertime();
                                    }
                                    this.highestPrice = data;
                                    if (
                                        data.cvendorCode != null &&
                                        data.cvendorCode === this.account.login &&
                                        data.message != null &&
                                        ctr === 0
                                    ) {
                                        ++ctr;
                                        this.rnsQuotationVendorsService
                                            .getRevisionByVendorId(id)
                                            .subscribe((res: HttpResponse<BaseEntityVndrPrice[]>) => {
                                                this.revisionList = res.body;
                                                this.subscribeChart();
                                            });
                                        this.messageAlert = data.message;
                                        this.notifier.notify('success', this.messageAlert);
                                    }
                                    this.isSaving = false;
                                }
                            });
                        });

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

                        variant.auctionConfig = variant.auctionVrnt;
                        if (variant.auctionConfig) {
                            const vndrPrice = new BaseEntityVndrPrice();
                            vndrPrice.priceOne = variant.auctionConfig.priceOne;
                            vndrPrice.priceTwo = variant.auctionConfig.priceTwo;
                            vndrPrice.priceThree = variant.auctionConfig.priceThree;
                            vndrPrice.priceFour = variant.auctionConfig.priceFour;
                            vndrPrice.priceFive = variant.auctionConfig.priceFive;
                            vndrPrice.priceSix = variant.auctionConfig.priceSix;
                            vndrPrice.priceSeven = variant.auctionConfig.priceSeven;
                            vndrPrice.priceEight = variant.auctionConfig.priceEight;
                            vndrPrice.priceNine = variant.auctionConfig.priceNine;
                            vndrPrice.priceTen = variant.auctionConfig.priceTen;
                            variant.vndrPrice = vndrPrice;
                        }
                        variant.vendors.forEach((vendor, vendorkey) => {
                            variant.vendorReguarRate = vendor.regularRate;
                            if (vendor.confDelDate) {
                                const confDelDatedate = this.dateUtils.convertLocalDateFromServer(vendor.confDelDate);

                                vendor.confDelDatedate = {
                                    year: confDelDatedate.getFullYear(),
                                    month: confDelDatedate.getMonth() + 1,
                                    day: confDelDatedate.getDate()
                                };
                            }
                            startRefresh++;
                            if (startRefresh >= this.variants.length) {
                                this.variants = this.variants.sort(function(a, b) {
                                    return a.id - b.id;
                                });
                                if (this.rnsQuotation.auctionClose === true) {
                                } else {
                                    this.refreshbidTime();
                                }
                            }
                        });
                    });
                });
            } else {
                console.log('No id Found..........');
            }
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;

                this.rnsVendorRemark = {
                    vendorEmail: this.account.email,
                    quotation: {
                        id: this.rnsQuotation.id
                    },
                    read: false
                };
            });
        });
    }
    registerAgreeSuccess() {
        this.eventSubscriber = this.eventManager.subscribe('call-parent-auction-vendor', response => {
            this.ngOnInit();
        });
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
                    variant.msg = data.msg;
                    variant.timeMsg = data.timeMsg;
                    variant.isAuctionPending = data.isAuctionPending;

                    if (this.variantDisplay === variant.title) {
                        this.variantEndTime = variant.endTime;
                        this.variantStartTime = variant.startTime;
                        this.variantTimeLeft = variant.timeLeft;
                        this.variantOverTime = variant.overTime;
                        this.variantMsg = variant.msg;
                        this.variantTimeMsg = variant.timeMsg;
                        this.variantAuctionActive = variant.isAuctionActive;
                        this.variantAuctionApplicable = variant.vendors[0].auctionApplicable;
                    }
                });
            });
        });
    }

    refreshbidTime() {
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
                        variant.msg = data.msg;
                        variant.timeMsg = data.timeMsg;
                        variant.isAuctionPending = data.isAuctionPending;

                        if (this.variantDisplay === variant.title) {
                            this.variantEndTime = variant.endTime;
                            this.variantStartTime = variant.startTime;
                            this.variantTimeLeft = variant.timeLeft;
                            this.variantOverTime = variant.overTime;
                            this.variantMsg = variant.msg;
                            this.variantTimeMsg = variant.timeMsg;
                            this.variantAuctionActive = variant.isAuctionActive;
                            this.variantAuctionApplicable = variant.vendors[0].auctionApplicable;
                        }
                    });
                });
            });
        });
    }

    displayVariant(variantName) {
        this.variantDisplay = variantName;
        this.position = 0;
        this.highestPrice = new VndrPrice();
        this.variants.forEach(variant => {
            if (this.variantDisplay === variant.title) {
                this.variantEndTime = variant.endTime;
                this.variantStartTime = variant.startTime;
                this.variantTimeLeft = variant.timeLeft;
                this.variantOverTime = variant.overTime;
                this.variantAuctionActive = variant.isAuctionActive;
                this.variantAuctionApplicable = variant.vendors[0].auctionApplicable;

                this.timerSingle();
                this.getHighestPrice(variant.id);
            }
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    historyUpdate(variant, key, value) {
        /*const totalValue =
            variant.auctionConfig.convFactOne * variant.vndrPrice.priceOne +
            variant.auctionConfig.convFactTwo * variant.vndrPrice.priceTwo +
            variant.auctionConfig.convFactThree * variant.vndrPrice.priceThree +
            variant.auctionConfig.convFactFour * variant.vndrPrice.priceFour +
            variant.auctionConfig.convFactFive * variant.vndrPrice.priceFive +
            variant.auctionConfig.convFactSix * variant.vndrPrice.priceSix +
            variant.auctionConfig.convFactSeven * variant.vndrPrice.priceSeven +
            variant.auctionConfig.convFactEight * variant.vndrPrice.priceEight +
            variant.auctionConfig.convFactNine * variant.vndrPrice.priceNine +
            variant.auctionConfig.convFactTen * variant.vndrPrice.priceTen;

        let itemValue = this.storage.retrieve(key);
        if (variant.vendorReguarRate) {
            if (!this.compareInputPrice(variant.vendorReguarRate, totalValue)) {
                this.notifier.notify( 'error', 'you cannot put price less than ' + variant.vendorReguarRate );
            } else {
                itemValue = [value];
                this.storage.store(key, itemValue);
                this.priceWrong = false;
            }
        } else {
            itemValue = [value];
            this.storage.store(key, itemValue);
            this.priceWrong = false;
        }*/
    }

    getHistory(key) {
        const itemValue = this.storage.retrieve(key);
        this.historyContent = itemValue;
    }

    getHighestPrice(id) {
        this.vndrPriceService.getHighestPrice(id).subscribe(price => {
            this.highestPrice = price.body;
        });
    }

    callOvertime() {
        this.rnsQuotationService.findAuctionDetailVariants(this.rnsQuotation.id).subscribe(rnsQuotationvariantsdata => {
            this.variantsTemp = rnsQuotationvariantsdata.body;
            this.variantsTemp.forEach(variantTemp => {
                this.variants.forEach(variant => {
                    const variantId = variant.id;
                    if (variantId === variantTemp.id) {
                        variant.overTime = variantTemp.overTime;
                        variant.lotStartTime = variantTemp.lotStartTime;
                        variant.lotEndTime = variantTemp.lotEndTime;
                    }
                });
            });
        });
    }

    saveVndrPrice(variant, vendorID, rnsQuotationVendors) {
        const vndrPriceData = variant.vndrPrice;

        const totalValue =
            variant.auctionConfig.convFactOne * variant.vndrPrice.priceOne +
            variant.auctionConfig.convFactTwo * variant.vndrPrice.priceTwo +
            variant.auctionConfig.convFactThree * variant.vndrPrice.priceThree +
            variant.auctionConfig.convFactFour * variant.vndrPrice.priceFour +
            variant.auctionConfig.convFactFive * variant.vndrPrice.priceFive +
            variant.auctionConfig.convFactSix * variant.vndrPrice.priceSix +
            variant.auctionConfig.convFactSeven * variant.vndrPrice.priceSeven +
            variant.auctionConfig.convFactEight * variant.vndrPrice.priceEight +
            variant.auctionConfig.convFactNine * variant.vndrPrice.priceNine +
            variant.auctionConfig.convFactTen * variant.vndrPrice.priceTen;
        if (totalValue === 0) {
            this.notifier.notify('error', 'Total price can not be zero.');
            return;
        }
        if (variant.bidStartPrice) {
            if (this.rnsQuotation.eventType === 'AUCTION') {
                if (totalValue < variant.bidStartPrice) {
                    this.notifier.notify('error', 'you cannot put price less than ' + variant.bidStartPrice);
                    return;
                }
            } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                if (totalValue > variant.bidStartPrice) {
                    this.notifier.notify('error', 'you cannot put greator less than ' + variant.bidStartPrice);
                    return;
                }
            }
        }
        if (variant.vendorReguarRate) {
            if (!this.compareInputPrice(variant.vendorReguarRate, totalValue)) {
                if (this.rnsQuotation.eventType === 'AUCTION') {
                    this.notifier.notify('error', 'you cannot put price less than ' + variant.vendorReguarRate);
                    return;
                } else {
                    this.notifier.notify('error', 'you cannot put price greator than ' + variant.vendorReguarRate);
                    return;
                }
            } else {
                if (this.highestPrice.price) {
                    let auctionAmount = false;
                    const highestallowedValue: number = this.highestPrice.price + this.rnsQuotation.auctionDetails.minPriceChanges;
                    const minimumallowedValue: number = this.highestPrice.price - this.rnsQuotation.auctionDetails.minPriceChanges;
                    if (this.rnsQuotation.eventType === 'AUCTION') {
                        if (totalValue <= this.highestPrice.price) {
                            this.notifier.notify('error', 'My bid value must not be less than My Last bid value');
                            return;
                        }
                        if (totalValue < highestallowedValue) {
                            this.notifier.notify(
                                'error',
                                'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                            );
                            return;
                        } else {
                            auctionAmount = true;
                        }
                    } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                        if (totalValue >= this.highestPrice.price) {
                            this.notifier.notify('error', 'My bid value must not be greator than My Last bid value');
                            return;
                        }
                        if (totalValue > minimumallowedValue) {
                            this.notifier.notify(
                                'error',
                                'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                            );
                            return;
                        } else {
                            auctionAmount = true;
                        }
                    }
                    if (auctionAmount) {
                        let percentage = 0;
                        if (this.rnsQuotation.eventType === 'AUCTION') {
                            percentage = (totalValue - this.highestPrice.price) * 100 / totalValue;
                        } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                            percentage = (this.highestPrice.price - totalValue) * 100 / this.highestPrice.price;
                        }
                        if (percentage > 10) {
                            if (confirm('You have improved your quote by more than 10%, are you sure you want to submit this new quote?')) {
                                this.isSaving = true;
                                vndrPriceData.variant = {
                                    id: vendorID
                                };
                                vndrPriceData.vndrQuotation = {
                                    id: rnsQuotationVendors.id
                                };
                                vndrPriceData.vendorCode = this.account.login;
                                this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                                this.isSaving = false;
                            }
                        } else {
                            this.isSaving = true;
                            vndrPriceData.variant = {
                                id: vendorID
                            };
                            vndrPriceData.vndrQuotation = {
                                id: rnsQuotationVendors.id
                            };
                            vndrPriceData.vendorCode = this.account.login;
                            // if (vndrPriceData.id !== undefined) {
                            // this.subscribeToSaveVndrPriceResponse(
                            this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                            this.isSaving = false;
                            // this.vndrPriceService.update(vndrPriceData), vndrPriceData);
                            // } else {
                            //      this.vndrPriceSocketService.sendActivity(vndrPriceData);
                            //      this.isSaving = false;
                            //     // this.subscribeToSaveVndrPriceResponse(
                            //     //     this.vndrPriceService.create(vndrPriceData), vndrPriceData);
                            // }
                        }
                    }
                } else {
                    this.isSaving = true;
                    vndrPriceData.variant = {
                        id: vendorID
                    };
                    vndrPriceData.vndrQuotation = {
                        id: rnsQuotationVendors.id
                    };
                    vndrPriceData.vendorCode = this.account.login;
                    // if (vndrPriceData.id !== undefined) {
                    // this.subscribeToSaveVndrPriceResponse(
                    this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                    this.isSaving = false;
                }
            }
        } else {
            if (this.highestPrice.price) {
                let auctionAmount = false;
                const highestallowedValue: number = this.highestPrice.price + this.rnsQuotation.auctionDetails.minPriceChanges;
                const minimumallowedValue: number = this.highestPrice.price - this.rnsQuotation.auctionDetails.minPriceChanges;
                if (this.rnsQuotation.eventType === 'AUCTION') {
                    if (totalValue <= this.highestPrice.price) {
                        this.notifier.notify('error', 'My bid value must not be less than My Last bid value');
                        return;
                    }
                    if (totalValue < highestallowedValue) {
                        this.notifier.notify(
                            'error',
                            'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                        );
                        return;
                    } else {
                        auctionAmount = true;
                    }
                } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                    if (totalValue >= this.highestPrice.price) {
                        this.notifier.notify('error', 'My bid value must not be greator than My Last bid value');
                        return;
                    }
                    if (totalValue > minimumallowedValue) {
                        this.notifier.notify(
                            'error',
                            'My bid value must be improved by minimum ' + this.rnsQuotation.auctionDetails.minPriceChanges
                        );
                        return;
                    } else {
                        auctionAmount = true;
                    }
                }
                if (auctionAmount) {
                    let percentage = 0;
                    if (this.rnsQuotation.eventType === 'AUCTION') {
                        percentage = (totalValue - this.highestPrice.price) * 100 / totalValue;
                    } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
                        percentage = (this.highestPrice.price - totalValue) * 100 / this.highestPrice.price;
                    }
                    if (percentage > 10) {
                        if (confirm('You have improved your quote by more than 10%, are you sure you want to submit this new quote?')) {
                            this.isSaving = true;
                            vndrPriceData.variant = {
                                id: vendorID
                            };
                            vndrPriceData.vndrQuotation = {
                                id: rnsQuotationVendors.id
                            };
                            vndrPriceData.vendorCode = this.account.login;
                            this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                        }
                    } else {
                        this.isSaving = true;
                        vndrPriceData.variant = {
                            id: vendorID
                        };
                        vndrPriceData.vndrQuotation = {
                            id: rnsQuotationVendors.id
                        };
                        vndrPriceData.vendorCode = this.account.login;
                        // if (vndrPriceData.id !== undefined) {
                        // this.subscribeToSaveVndrPriceResponse(
                        this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
                        // this.vndrPriceService.update(vndrPriceData), vndrPriceData);
                        // } else {
                        //     //this.subscribeToSaveVndrPriceResponse(
                        //         this.vndrPriceSocketService.sendActivity(vndrPriceData);
                        //        // this.vndrPriceService.create(vndrPriceData), vndrPriceData);
                        // }
                    }
                }
            } else {
                this.isSaving = true;
                vndrPriceData.variant = {
                    id: vendorID
                };
                vndrPriceData.vndrQuotation = {
                    id: rnsQuotationVendors.id
                };
                vndrPriceData.vendorCode = this.account.login;
                // if (vndrPriceData.id !== undefined) {
                // this.subscribeToSaveVndrPriceResponse(
                this.vndrPriceSocketService.sendActivity(vndrPriceData, variant.id);
            }
        }
    }

    compareInputPrice(regularRate, price) {
        if (this.rnsQuotation.eventType === 'AUCTION') {
            if (regularRate > price) {
                return false;
            }
        } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
            if (regularRate < price) {
                return false;
            }
        }
        return true;
    }

    diff_minutes(dt1original, startTime, endTime, lotTime, overTime = 0): Promise<any> {
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
                msg: 'Bidding has closed.',
                startTime: dt2,
                endTime: dt3,
                lotTime: lotTime,
                overTime: overTime,
                timeLeft: 0
            });
        }
    }

    subscribeChart(refresh = false) {
        this.variants.forEach(variant => {
            let ids: string = 'area' + variant.id;
            ids = ids.replace(/\s/g, '');
            const chartDataTemp: RnsQuotationVendors[] = [];
            let incr = 0;
            const set = new Set();
            this.revisionList.forEach(val => {
                set.add(val.vendorCode);
            });
            set.forEach(vCode => {
                incr++;
                const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                rnsQuotationVendors.vendorCode = vCode;
                const revisionListTemp: BaseEntityVndrPrice[] = [];
                this.revisionList.forEach(val => {
                    if (val.title === variant.title && vCode === val.vendorCode) {
                        val.date = new Date(this.datePipe.transform(val.date, 'yyyy-MM-dd HH:mm:ss'));
                        revisionListTemp.push(val);
                    }
                });
                if (revisionListTemp.length > 0) {
                    rnsQuotationVendors.revisionList = revisionListTemp;
                    chartDataTemp.push(rnsQuotationVendors);
                }
            });
            if (chartDataTemp.length > 0 && set.size === incr) {
                this.renderChart(ids, chartDataTemp, refresh);
            }
        });
    }

    renderChart(ids, chartDataTemp: RnsQuotationVendors[], refresh = false) {
        // let data:any = chartDataTemp.map((v) => v.revisionList.map((v) => new Date(this.datePipe.transform(v.date, 'yyyy-MM-dd HH:mm:ss')) ))[0];
        const dates = [];
        chartDataTemp.forEach(val => {
            val.revisionList.forEach(revval => {
                dates.push(new Date(this.datePipe.transform(revval.date, 'yyyy-MM-dd HH:mm:ss')));
            });
        });
        const data: any = [new Date(Math.min.apply(null, dates)), new Date(Math.max.apply(null, dates))];
        if (typeof data !== 'undefined') {
            let firstDate = data[0];
            firstDate = firstDate.getTime() - 1 * 30000;
            data[0] = firstDate;
            // let lastDate = data[data.length - 1];

            /*lastDate = lastDate.getTime() + 2 * 60000;
            data[0] = firstDate;
            data[data.length - 1] = lastDate;*/

            this.initChart(ids, chartDataTemp, data, refresh);
        }
    }
    private initChart(ids, chartData: RnsQuotationVendors[], data, refresh = false): void {
        if (chartData.length > 0 && data.length > 0) {
            this.width = 500;
            this.height = 300;

            /* Scale */
            const x: any = d3Scale.scaleTime().range([0, this.width - this.margin]);

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
            // if(refresh == false) {
            this.drawAxis(x, y);
            // }
            this.drawPath(chartData, x, y, z);
        }
    }

    private drawAxis(x, y): void {
        const xAxis = d3Axis.axisBottom(x).ticks(5);
        const yAxis = d3Axis.axisLeft(y).ticks(5);

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
                    .style('fill', z(i))
                    .text(d.vendorCode)
                    .attr('text-anchor', 'middle')
                    .attr('x', 560 / 2)
                    .attr('y', 5);
            })
            .on('mouseout', function(d) {
                city.select('.title-text').remove();
            });
        city
            .append('path')
            .attr('class', 'line')
            .attr('d', d => this.line(d.revisionList))
            .style('stroke', (d, i) => z(i))
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

        /* Add circles in the line */
        const circleGroup = this.g
            .selectAll('circle-group')
            .data(chartData)
            .enter()
            .append('g')
            .style('fill', (d, i) => z(i));

        circleGroup
            .selectAll('circle')
            .data(d => d.revisionList)
            .enter()
            .append('g')
            .attr('class', 'circle')
            .on('mouseover', function(d) {
                d3
                    .select(this)
                    .style('cursor', 'pointer')
                    .append('text')
                    .attr('class', 'text')
                    .text(`${d.value}`)
                    .attr('x', x(d.date) + 5)
                    .attr('y', y(d.value) - 10);
            })
            .on('mouseout', function(d) {
                d3
                    .select(this)
                    .style('cursor', 'none')
                    .selectAll('.text')
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
}
