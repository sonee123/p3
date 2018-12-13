import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs/Rx';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiDateUtils } from 'ng-jhipster';
import { NgForm } from '@angular/forms';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { RnsUomMaster, RnsUomMasterService } from 'app/entities/rns-uom-master';
import { RnsVendorRemark, RnsVendorRemarkService, RnsVendorRemarkSocketService } from 'app/entities/rns-vendor-remark';
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
import { Account, LoginModalService, Principal, UserService } from 'app/core';
import { AuctionVrntService } from 'app/entities/auction-vrnt';
import { RnsRfqPrice, RnsRfqPriceService } from 'app/entities/rns-rfq-price';
import { VariantDataService } from 'app/vendor/variant-data.service';
import * as moment from 'moment';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsquotation.component.html'
})
export class RnsQuotationComponent implements OnInit, OnDestroy {
    subscribtion: Subscription;
    account: Account;
    rnsCatgMasters: RnsCatgMaster[];
    rnsCatgMaster: RnsCatgMaster;
    rnsRefrDetails: RnsRefrDetails[];
    rnsTypeMaster: RnsTypeMaster;
    rnsTypeMasters: RnsTypeMaster[];
    rnsUomMasters: RnsUomMaster[];
    rnsVendorRemarks: RnsVendorRemark[];
    remarksIdContainer: Map<number, number>;
    rnsVendorRemarkPost: RnsVendorRemark;
    rnsUomMaster: RnsUomMaster;
    rnsSectorMasters: RnsSectorMaster[];
    rnsSectorMaster: RnsSectorMaster;
    rnsTypeMastersResponseData: RnsTypeMaster[];
    rnsSectorMastersResponseData: RnsSectorMaster[];
    rnsDelTermsMasters: RnsDelTermsMaster[];
    rnsDelTermsMaster: RnsDelTermsMaster;
    templates: Template[];
    currencies: Currency[];
    rnsDelTermsMastersResponseData: RnsDelTermsMaster[];
    rnsPayTermsMasters: RnsPayTermsMaster[];
    rnsPayTermsMaster: RnsPayTermsMaster;
    rnsPayTermsMastersResponseData: RnsPayTermsMaster[];
    rnsTaxTermsMasters: RnsTaxTermsMaster[];
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    variants: RnsQuotationVariant[];
    variantsID: any;
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
    dealtermCompletionByDp: any;
    dealtermValidUntil: any;
    varDescSpecfields: any;
    varDescSpecValues: any[];
    selectedTemplates: any;
    selectedTemplatesSelect: any;
    rnsQuotationCategory: String;
    minDate: any;
    lotNames: any;
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
    isSaving: Boolean;
    chatBtnText: String;
    saveVendor: RnsQuotationVendors;
    timer: any;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsSectorMasterService: RnsSectorMasterService,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsUomMasterService: RnsUomMasterService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private rnsVendorRemarkService: RnsVendorRemarkService,
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
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
        private userService: UserService,
        private rnsVendorRemarkSocketService: RnsVendorRemarkSocketService,
        private auctionVrntService: AuctionVrntService,
        private rnsRfqPriceService: RnsRfqPriceService,
        private variantDataService: VariantDataService,
        private notifier: NotifierService
    ) {}

    loadAll() {
        this.isValidQuotationFormSubmitted = true;
        this.rnsPayTermsMasterService.query().subscribe(
            (res: HttpResponse<RnsPayTermsMaster[]>) => {
                this.rnsPayTermsMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsDelTermsMasterService.query().subscribe(
            (res: HttpResponse<RnsDelTermsMaster[]>) => {
                this.rnsDelTermsMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.currencyService.query().subscribe(
            (res: HttpResponse<Currency[]>) => {
                this.currencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.lotNames = this.variantDataService.fetchVariant();
        this.rnsVendorRemarks = [];
        this.remarksIdContainer = new Map<number, number>();
        this.loadAll();
        this.principal.identity().then(account => {
            this.account = account;
            this.rnsVendorRemarkPost = {
                vendorEmail: this.account.email,
                fromEmail: this.account.email,
                quotation: {
                    id: this.rnsQuotation.id
                }
            };
            this.routeSub = this.route.params.subscribe(params => {
                const id = params['id'];
                this.rnsVendorRemarkSocketService.subscribe(id, this.account.email);
                this.rnsVendorRemarkSocketService.receive().subscribe(activity => {
                    if (this.remarksIdContainer.has(activity.body.id)) {
                    } else {
                        this.remarksIdContainer.set(activity.body.id, activity.body.id);
                        this.rnsVendorRemarks.push(activity.body);
                    }
                    this.chatBtnText = 'Send';
                    this.rnsVendorRemarkPost.remarkText = '';
                });

                // console.log(this.account.email);
                this.rnsVendorRemarkService.queryByEmailQuoteId(this.account.email, id).subscribe(
                    (response: HttpResponse<RnsVendorRemark[]>) => {
                        const rnsVendorRemarksTemp: RnsVendorRemark[] = response.body.reverse();
                        if (rnsVendorRemarksTemp.length > 0) {
                            rnsVendorRemarksTemp.forEach(remarks => {
                                if (remarks.vendorEmail === this.account.email) {
                                    this.rnsVendorRemarks.push(remarks);
                                    this.remarksIdContainer.set(remarks.id, remarks.id);
                                }
                            });
                        }
                        if (this.vendors.length > 0) {
                            this.vendors.forEach(vendor => {
                                if (this.rnsVendorRemarks.length > 0) {
                                    this.rnsVendorRemarks.forEach(remark => {
                                        if (typeof vendor.vendor.email !== 'undefined') {
                                            // console.log('dinesh829269@gmail.com', remark.vendorEmail, vendor.vendor.email, vendor.id)
                                            if (remark.vendorEmail === vendor.vendor.email) {
                                                vendor.chatActive = remark;
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    },
                    (response: HttpErrorResponse) => this.onError(response.message)
                );
            });
        });
        this.rnsQuotation = {};
        this.varDescSpecValues = [];
        this.variantsData = [];
        this.checkQuotationDetails();
        this.rnsTaxTermsMaster = {};
        this.variants = [];
        this.vendors = [];
        this.subcodeInput = {
            input1: { display: true, data: [] },
            input2: { display: true, data: [] },
            input3: { display: true, data: [] },
            input4: { display: true, data: [] },
            input5: { display: true, data: [] }
        };

        this.selectedTemplates = {};
        this.saveQuotationText = 'Save Project';
        this.chatBtnText = 'Send';
    }

    ngOnDestroy() {
        if (this.subscribtion) {
            this.subscribtion.unsubscribe();
            this.subscribtion = null;
        }
    }

    public chatboxDisable() {
        this.displaychatBody = true;
    }

    refreshbidTime() {
        const timer = Observable.timer(2000, 1000);
        this.subscribtion = timer.subscribe(() => {
            this.rnsQuotationService.getCurrentTime().subscribe(time => {
                this.timer = time.body;
                const date = new Date(this.rnsQuotation.validity);
                const todaysDate = this.dateUtils.convertDateTimeFromServer(time.body);
                this.validityExpired = false;
                if (todaysDate > date) {
                    this.validityExpired = true;
                }
            });
        });
    }

    checkQuotationDetails() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                const id = params['id'];
                this.rnsQuotationService.findrfq(id).subscribe((rnsQuotationdata: HttpResponse<RnsQuotation>) => {
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
                    if (rnsQuotationdata.body.validity != null) {
                        const date = new Date(rnsQuotationdata.body.validity);
                        this.rnsQuotationService.getCurrentTime().subscribe(time => {
                            console.log('current Time', time.body);
                            const todaysDate = this.dateUtils.convertDateTimeFromServer(time.body);
                            this.validityExpired = false;
                            if (todaysDate > date) {
                                this.validityExpired = true;
                            }
                        });
                        this.minDate = {
                            year: date.getFullYear(),
                            month: date.getMonth() + 1,
                            day: date.getDate()
                        };
                    } else {
                        this.minDate = new Date();
                    }
                    this.rnsQuotation = rnsQuotationdata.body;
                    this.refreshbidTime();
                    this.variantsData = rnsQuotationdata.body.variants;
                    this.variants = rnsQuotationdata.body.variants;
                    this.displayVariant(rnsQuotationdata.body.displayVariant);
                    this.variants.forEach(variant => {
                        const rnsRfqPrice = new RnsRfqPrice();

                        variant.auctionConfig = variant.auctionVrnt;
                        rnsRfqPrice.priceOne = variant.auctionVrnt.priceOne;
                        rnsRfqPrice.priceTwo = variant.auctionVrnt.priceTwo;
                        rnsRfqPrice.priceThree = variant.auctionVrnt.priceThree;
                        rnsRfqPrice.priceFour = variant.auctionVrnt.priceFour;
                        rnsRfqPrice.priceFive = variant.auctionVrnt.priceFive;
                        rnsRfqPrice.priceSix = variant.auctionVrnt.priceSix;
                        rnsRfqPrice.priceSeven = variant.auctionVrnt.priceSeven;
                        rnsRfqPrice.priceEight = variant.auctionVrnt.priceEight;
                        rnsRfqPrice.priceNine = variant.auctionVrnt.priceNine;
                        rnsRfqPrice.priceTen = variant.auctionVrnt.priceTen;

                        variant.vendors[0].rfqPrice = rnsRfqPrice;

                        variant.vendors[0].allEditable = false;
                        if (!variant.vendors[0].quoteQty) {
                            if (this.validityExpired) {
                                variant.vendors[0].allEditable = true;
                            }
                        }
                        if (variant.vendors[0].expDelDate != null) {
                            const expDelDatedate = this.dateUtils.convertLocalDateFromServer(variant.vendors[0].expDelDate);
                            variant.vendors[0].minConfDelDate = {
                                year: expDelDatedate.getFullYear(),
                                month: expDelDatedate.getMonth() + 1,
                                day: expDelDatedate.getDate()
                            };
                        }
                        if (variant.vendors[0].confDelDate) {
                            const confDelDatedate = this.dateUtils.convertLocalDateFromServer(variant.vendors[0].confDelDate);
                            variant.vendors[0].confDelDatedate = {
                                year: confDelDatedate.getFullYear(),
                                month: confDelDatedate.getMonth() + 1,
                                day: confDelDatedate.getDate()
                            };
                        }

                        this.vendors.push(variant.vendors[0]);

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
                    });
                });
            } else {
                console.log('No id Found..........');
            }
        });
    }

    displayVariant(variantName) {
        this.variantDisplay = variantName;
    }

    noSubmit() {
        return false;
    }

    savevendorQuotation(form: NgForm, vendor) {
        this.isValidQuotationFormSubmitted = false;
        console.log(form.invalid);
        if (form.invalid) {
            this.notifier.notify('error', 'Please fill all required feilds.');
        } else {
            this.isValidQuotationFormSubmitted = true;
            console.log(this.rnsQuotation);
            this.saveQuotationText = 'Saving ...';
            this.isSaving = true;
            if (vendor.confDelDatedate) {
                vendor.confDelDate = this.dateUtils.convertLocalDateToServer(vendor.confDelDatedate);
            }
            vendor.rfqUserType = 'V';
            this.saveVendor = vendor;
            this.subscribeToSaveRFQResponse(this.rnsQuotationVendorsService.updaterfq(vendor));
        }
    }

    historyUpdate(variant, vendor) {
        vendor.regularRate = (
            variant.auctionConfig.convFactOne * vendor.rfqPrice.priceOne +
            variant.auctionConfig.convFactTwo * vendor.rfqPrice.priceTwo +
            variant.auctionConfig.convFactThree * vendor.rfqPrice.priceThree +
            variant.auctionConfig.convFactFour * vendor.rfqPrice.priceFour +
            variant.auctionConfig.convFactFive * vendor.rfqPrice.priceFive +
            variant.auctionConfig.convFactSix * vendor.rfqPrice.priceSix +
            variant.auctionConfig.convFactSeven * vendor.rfqPrice.priceSeven +
            variant.auctionConfig.convFactEight * vendor.rfqPrice.priceEight +
            variant.auctionConfig.convFactNine * vendor.rfqPrice.priceNine +
            variant.auctionConfig.convFactTen * vendor.rfqPrice.priceTen
        ).toFixed(2);
    }

    private subscribeToSaveRFQResponse(result: Observable<HttpResponse<RnsRfqPrice>>) {
        result.subscribe(
            (res: HttpResponse<RnsRfqPrice>) => this.quotationRfqSaved(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private quotationRfqSaved(rnsRfqPrice: HttpResponse<RnsRfqPrice>) {
        this.saveQuotationText = 'Save Project';
        if (this.isSaving) {
            this.notifier.notify('success', 'RFQ has been saved.');
        }

        this.isSaving = false;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private generateQuoation() {
        console.log('Generating Project ...');
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    /**
     * Convert a RnsQuotation to a JSON which can be sent to the server.
     */
    private convert(rnsQuotation: RnsQuotation): RnsQuotation {
        const copy: RnsQuotation = Object.assign({}, rnsQuotation);
        // copy.date = this.dateUtils.toDate(rnsQuotation.date);
        // copy.validity = this.dateUtils.toDate(rnsQuotation.validity);
        // copy.createdOn = this.dateUtils.toDate(rnsQuotation.createdOn);
        if (rnsQuotation.targetPcd !== '') {
            copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotation.targetPcd);
        }
        copy.validity = rnsQuotation.validity != null ? moment(rnsQuotation.validity) : null;
        copy.createdOn = rnsQuotation.createdOn != null ? moment(rnsQuotation.createdOn) : null;
        copy.date = rnsQuotation.date != null ? moment(rnsQuotation.date) : null;
        return copy;
    }

    public postChatMsg() {
        this.rnsVendorRemarkPost.staffEmail = this.rnsQuotation.user.email;
        this.rnsVendorRemarkPost.toEmail = this.rnsQuotation.user.email;
        this.rnsVendorRemarkPost.quotation.id = this.rnsQuotation.id;
        this.rnsVendorRemarkSocketService.sendActivity(
            this.rnsVendorRemarkPost,
            this.rnsQuotation.id,
            this.rnsVendorRemarkPost.vendorEmail
        );
        // this.rnsVendorRemarkPost.remarkText = ' ';
    }

    private subscribeToSaveRemarkResponse(result: Observable<RnsVendorRemark>) {
        result.subscribe((res: RnsVendorRemark) => this.onSaveRemarkSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveRemarkSuccess(result: RnsVendorRemark) {
        this.eventManager.broadcast({ name: 'rnsVendorRemarkListModification', content: 'OK' });
        this.rnsVendorRemarks.push(result);
    }

    private onSaveError() {}

    public confDelDate() {}
}
