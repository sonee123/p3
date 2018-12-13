import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { Subscription, Observable } from 'rxjs/Rx';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { Account, LoginModalService, Principal, UserService } from '../core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsPchMaster, RnsPchMasterService } from 'app/entities/rns-pch-master';
import { RnsBuyerMaster, RnsBuyerMasterService } from 'app/entities/rns-buyer-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { RnsUomMaster, RnsUomMasterService } from 'app/entities/rns-uom-master';
import { RnsTaxTermsMaster, RnsTaxTermsMasterService } from 'app/entities/rns-tax-terms-master';
import { Currency, CurrencyService } from 'app/entities/currency';
import { RnsSectorMaster, RnsSectorMasterService } from 'app/entities/rns-sector-master';
import { RnsDelTermsMaster, RnsDelTermsMasterService } from 'app/entities/rns-del-terms-master';
import { Template, TemplateService } from 'app/entities/template';
import { RnsPayTermsMaster, RnsPayTermsMasterService } from 'app/entities/rns-pay-terms-master';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';
import { RnsDelPlaceMaster, RnsDelPlaceMasterService } from 'app/entities/rns-del-place-master';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from 'app/entities/rns-source-team-master';
import { Auction, AuctionService } from 'app/entities/auction';
import { VariantDataService } from 'app/vendor/variant-data.service';
import { NgForm } from '@angular/forms';
import * as moment from 'moment';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsquotation_new.component.html'
})
export class RnsQuotationNewComponent implements OnInit {
    account: Account;
    rnsCatgMasters: RnsCatgMaster[];
    rnsCatgMaster: RnsCatgMaster;
    rnsPchMasters: RnsPchMaster[];
    rnsBuyerMasters: RnsBuyerMaster[];
    rnsPchMaster: RnsPchMaster;
    rnsRefrDetails: RnsRefrDetails[];
    rnsTypeMaster: RnsTypeMaster;
    rnsTypeMasters: RnsTypeMaster[];
    rnsUomMasters: RnsUomMaster[];
    rnsTaxTermsMasters: RnsTaxTermsMaster[];
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    currencies: Currency[];
    currency: Currency;
    rnsUomMaster: RnsUomMaster;
    rnsSectorMaster: RnsSectorMaster;
    rnsTypeMastersResponseData: RnsTypeMaster[];
    rnsDelTermsMasters: RnsDelTermsMaster[];
    rnsDelTermsMaster: RnsDelTermsMaster;
    templates: Template[];
    rnsPayTermsMasters: RnsPayTermsMaster[];
    rnsPayTermsMaster: RnsPayTermsMaster;
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    variants: RnsQuotationVariant[];
    vendors: RnsQuotationVendors[];
    rnsDelPlaceMaster: RnsDelPlaceMaster;
    rnsDelPlaceMasters: RnsDelPlaceMaster[];
    rnsSourceTeamMasters: RnsSourceTeamMaster[];
    article: Object;
    routeSub: any;
    targetPcd: Date;
    saveQuotationText: string;
    variantDisplay: string;
    subcodeInput: any;
    dealtermValidUntil: any;
    varDescSpecValues: any[];
    selectedTemplates: any;
    selectedTemplatesSelect: any;
    currentDate;
    date: Date = new Date();
    minDate: any;
    lotNames: any;
    settings = {
        bigBanner: true,
        timePicker: true,
        format: 'dd/MM/yyyy hh:mm a',
        defaultOpen: false
    };
    isValidQuotationFormSubmitted: Boolean;
    variantCheck: Boolean;
    vendorCheck: Boolean;
    payTermsCodeCopy: any;
    delTermsCodeCopy: any;
    refresh: string;
    isSaving: Boolean;
    saveVendors: any;
    validateQuotationVariant: any;
    auction: Auction;
    auctions: Auction[];

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsPchMasterService: RnsPchMasterService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsSectorMasterService: RnsSectorMasterService,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsUomMasterService: RnsUomMasterService,
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
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
        private router: Router,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        private currencyService: CurrencyService,
        private auctionService: AuctionService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private variantDataService: VariantDataService,
        private userService: UserService,
        private notifier: NotifierService
    ) {}

    loadAll() {
        this.isValidQuotationFormSubmitted = true;
        this.rnsCatgMasterService.query().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnsCatgMasters = res.body;
                console.log(this.rnsCatgMasters);
                this.routeSub = this.route.params.subscribe(params => {
                    if (params['categoryId']) {
                        const categoryId = params['categoryId'];
                        for (const item in this.rnsCatgMasters) {
                            if (Number(categoryId) === Number(this.rnsCatgMasters[item].id)) {
                                this.rnsQuotation.rnsCatgCode = this.rnsCatgMasters[item];
                            }
                        }
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsPchMasterService.query().subscribe(
            (res: HttpResponse<RnsPchMaster[]>) => {
                this.rnsPchMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsTypeMasterService.query().subscribe(
            (res: HttpResponse<RnsTypeMaster[]>) => {
                this.rnsTypeMastersResponseData = res.body;
                this.rnsTypeMasters = [];
                this.routeSub = this.route.params.subscribe(params => {
                    if (params['categoryId']) {
                        const id = params['categoryId'];
                        for (const item in this.rnsTypeMastersResponseData) {
                            if (Number(this.rnsTypeMastersResponseData[item].rnsCatgCode.id) === Number(id)) {
                                this.rnsTypeMasters.push(this.rnsTypeMastersResponseData[item]);
                            }
                        }
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsUomMasterService.query().subscribe(
            (res: HttpResponse<RnsUomMaster[]>) => {
                this.rnsUomMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsTaxTermsMasterService.query().subscribe(
            (res: HttpResponse<RnsTaxTermsMaster[]>) => {
                this.rnsTaxTermsMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsSourceTeamMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsSourceTeamMaster[]>) => {
                this.rnsSourceTeamMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        /*this.rnsSectorMasterService.query().subscribe(
            (res: HttpResponse<[]>) => {
                this.rnsSectorMastersResponseData = res.json;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );*/

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

        this.rnsDelPlaceMasterService.query().subscribe(
            (res: HttpResponse<RnsDelPlaceMaster[]>) => {
                this.rnsDelPlaceMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsBuyerMasterService.query().subscribe(
            (res: HttpResponse<RnsBuyerMaster[]>) => {
                this.rnsBuyerMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.currencyService.query().subscribe(
            (res: HttpResponse<Currency[]>) => {
                this.currencies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.payTermsCodeCopy = {};
        this.delTermsCodeCopy = {};

        this.auctionService.query().subscribe(
            (res: HttpResponse<Auction[]>) => {
                this.auctions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.currentDate = new Date();
        this.loadAll();
        const expDelDatedate = new Date();
        this.minDate = {
            year: expDelDatedate.getFullYear(),
            month: expDelDatedate.getMonth() + 1,
            day: expDelDatedate.getDate()
        };
        this.rnsQuotation = {};
        this.variantCheck = true;
        this.vendorCheck = true;
        this.checkQuotationDetails();
        this.varDescSpecValues = [];
        this.variants = [{ title: 'Variant 1', vendors: [], quotation: this.rnsQuotation, openCosting: 'Not Required' }];
        this.variantDisplay = 'Variant 1';

        this.vendors = [];
        this.subcodeInput = {
            input1: { display: true, data: [] },
            input2: { display: true, data: [] },
            input3: { display: true, data: [] },
            input4: { display: true, data: [] },
            input5: { display: true, data: [] }
        };

        this.lotNames = this.variantDataService.fetchVariant();
        this.refresh = '';
        this.selectedTemplates = {};
        this.saveQuotationText = 'Save Project';
        this.registerAuthenticationSuccess();
        this.registerChangeInRnsCatgMasters();
        this.registerChangeInRnsRefrDetails();
        this.registerChangeInSeachRnsCrmRequestMasters();
        this.registerChangeInSeachRnsArticle();
        this.registerChangeInSearchVendor();
    }

    checkQuotationDetails() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                const id = params['id'];
                if (params['type'] !== null && params['type'] === 'C') {
                    this.rnsQuotationService.find(id).subscribe(rnsQuotationdata => {
                        this.getTemplatesEdit(rnsQuotationdata.body.rnsCatgCode.id, rnsQuotationdata.body);
                        rnsQuotationdata.body.id = null;
                        rnsQuotationdata.body.validity = null;
                        rnsQuotationdata.body.createdOn = null;
                        rnsQuotationdata.body.workflowStatus = null;
                        rnsQuotationdata.body.rfq = null;
                        rnsQuotationdata.body.auction = null;
                        rnsQuotationdata.body.createdBy = null;
                        rnsQuotationdata.body.rfqPublishDate = null;
                        rnsQuotationdata.body.auctionPublishDate = null;
                        rnsQuotationdata.body.auctionClose = null;
                        rnsQuotationdata.body.approvedDate = null;
                        rnsQuotationdata.body.approvedBy = null;
                        rnsQuotationdata.body.updatedBy = null;
                        rnsQuotationdata.body.updatedDate = null;
                        rnsQuotationdata.body.approvedFlag = null;
                        rnsQuotationdata.body.date = null;
                        rnsQuotationdata.body.paused = null;

                        if (rnsQuotationdata.body.targetPcd) {
                            this.targetPcd = rnsQuotationdata.body.targetPcd;
                            rnsQuotationdata.body.targetPcd = {
                                year: rnsQuotationdata.body.targetPcd.getFullYear(),
                                month: rnsQuotationdata.body.targetPcd.getMonth() + 1,
                                day: rnsQuotationdata.body.targetPcd.getDate()
                            };
                        }
                        this.rnsQuotation = rnsQuotationdata.body;

                        this.rnsTypeMasters = [];
                        for (const item in this.rnsTypeMastersResponseData) {
                            if (Number(this.rnsTypeMastersResponseData[item].rnsCatgCode.id) === Number(this.rnsQuotation.rnsCatgCode.id)) {
                                this.rnsTypeMasters.push(this.rnsTypeMastersResponseData[item]);
                            }
                        }

                        this.rnsQuotationVariantService.getByQuotationId(id).subscribe(
                            (res: HttpResponse<RnsQuotationVariant[]>) => {
                                this.variants = res.body;
                                this.displayVariant(res.body[0].title);
                                this.variants.forEach(variant => {
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
                                    this.payTermsCodeCopy[variant.title] = res.body[0].dealtermPaymentTerms.toString();
                                    this.delTermsCodeCopy[variant.title] = res.body[0].dealtermDeliveryTerms.toString();
                                    variant.id = null;
                                    variant.uploadFlag = null;
                                    variant.quotation = this.rnsQuotation;
                                });
                            },
                            (res: HttpErrorResponse) => this.onError(res.message)
                        );

                        this.rnsQuotationVendorsService.getByQuotationId(id).subscribe((res: HttpResponse<RnsQuotationVendors[]>) => {
                            this.vendors = res.body;
                            this.vendors.forEach(vendor => {
                                this.userService.findpost(vendor.vendorCode).subscribe(user => {
                                    vendor.vendor = user.body;
                                });
                            });
                            this.vendors.forEach(vendor => {
                                vendor.expDelDate = null;
                                vendor.expDelDatedate = null;
                                vendor.confDelDate = null;
                                vendor.confDelDatedate = null;
                                vendor.regularRate = null;
                                vendor.bidRate = null;
                                vendor.awardQty = null;
                                vendor.rfqUserType = null;
                                vendor.auctionApplicable = null;
                                vendor.currency = null;
                                vendor.disRate = null;
                                vendor.id = null;
                                vendor.variant.id = null;
                                vendor.variant.quotation = this.rnsQuotation;
                            });
                        });
                    });
                }
            }
            if (params['categoryId']) {
                const id = params['categoryId'];
                this.getTemplates(id);
            } else {
                console.log('No id FOund..........');
            }
        });
    }

    getTemplatesEdit(id, rnsQuotationData) {
        this.templateService.queryByCatgCode(id).subscribe(
            (res: HttpResponse<Template[]>) => {
                this.templates = res.body;
                this.templates.forEach(template => {
                    if (template.specificationOneValue != null) {
                        template.specificationOneValue = template.specificationOneValue.split(',');
                    } else {
                        template.specificationOneValue = [];
                    }
                    if (template.specificationTwoValue != null) {
                        template.specificationTwoValue = template.specificationTwoValue.split(',');
                    } else {
                        template.specificationTwoValue = [];
                    }
                    if (template.specificationThreeValue != null) {
                        template.specificationThreeValue = template.specificationThreeValue.split(',');
                    } else {
                        template.specificationThreeValue = [];
                    }
                    if (template.specificationFourValue != null) {
                        template.specificationFourValue = template.specificationFourValue.split(',');
                    } else {
                        template.specificationFourValue = [];
                    }
                    if (template.specificationFiveValue != null) {
                        template.specificationFiveValue = template.specificationFiveValue.split(',');
                    } else {
                        template.specificationFiveValue = [];
                    }
                    if (template.specificationSixValue != null) {
                        template.specificationSixValue = template.specificationSixValue.split(',');
                    } else {
                        template.specificationSixValue = [];
                    }
                    if (template.specificationSevenValue != null) {
                        template.specificationSevenValue = template.specificationSevenValue.split(',');
                    } else {
                        template.specificationSevenValue = [];
                    }
                    if (template.specificationEightValue != null) {
                        template.specificationEightValue = template.specificationEightValue.split(',');
                    } else {
                        template.specificationEightValue = [];
                    }
                    if (template.specificationNineValue != null) {
                        template.specificationNineValue = template.specificationNineValue.split(',');
                    } else {
                        template.specificationNineValue = [];
                    }
                    if (template.specificationTenValue != null) {
                        template.specificationTenValue = template.specificationTenValue.split(',');
                    } else {
                        template.specificationTenValue = [];
                    }

                    if (Number(rnsQuotationData.template) === Number(template.id)) {
                        this.selectedTemplates = template;
                        this.selectedTemplatesSelect = template;
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getTemplates(id) {
        this.templateService.queryByCatgCodeActivate(id).subscribe(
            (res: HttpResponse<Template[]>) => {
                this.templates = res.body;
                this.templates.forEach(template => {
                    if (template.specificationOneValue != null) {
                        template.specificationOneValue = template.specificationOneValue.split(',');
                    } else {
                        template.specificationOneValue = [];
                    }
                    if (template.specificationTwoValue != null) {
                        template.specificationTwoValue = template.specificationTwoValue.split(',');
                    } else {
                        template.specificationTwoValue = [];
                    }
                    if (template.specificationThreeValue != null) {
                        template.specificationThreeValue = template.specificationThreeValue.split(',');
                    } else {
                        template.specificationThreeValue = [];
                    }
                    if (template.specificationFourValue != null) {
                        template.specificationFourValue = template.specificationFourValue.split(',');
                    } else {
                        template.specificationFourValue = [];
                    }
                    if (template.specificationFiveValue != null) {
                        template.specificationFiveValue = template.specificationFiveValue.split(',');
                    } else {
                        template.specificationFiveValue = [];
                    }
                    if (template.specificationSixValue != null) {
                        template.specificationSixValue = template.specificationSixValue.split(',');
                    } else {
                        template.specificationSixValue = [];
                    }
                    if (template.specificationSevenValue != null) {
                        template.specificationSevenValue = template.specificationSevenValue.split(',');
                    } else {
                        template.specificationSevenValue = [];
                    }
                    if (template.specificationEightValue != null) {
                        template.specificationEightValue = template.specificationEightValue.split(',');
                    } else {
                        template.specificationEightValue = [];
                    }
                    if (template.specificationNineValue != null) {
                        template.specificationNineValue = template.specificationNineValue.split(',');
                    } else {
                        template.specificationNineValue = [];
                    }
                    if (template.specificationTenValue != null) {
                        template.specificationTenValue = template.specificationTenValue.split(',');
                    } else {
                        template.specificationTenValue = [];
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    refreshVendor() {
        this.variants.forEach(variant => {
            if (variant.title === this.variantDisplay) {
                if (this.vendors.length > 0) {
                    this.vendors.forEach(vendor => {
                        if (vendor.variant === variant) {
                            vendor.paymentTerms = this.payTermsCodeCopy[this.variantDisplay];
                            vendor.deliveryTerms = this.delTermsCodeCopy[this.variantDisplay];
                            vendor.expiryQty = variant.orderQuantity;
                        }
                    });
                }
            }
        });
    }

    registerChangeInRnsCatgMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsCatgMasterListModification', response => this.loadAll());
    }

    registerChangeInRnsRefrDetails() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRefrDetailsListModification', response => this.loadAll());
    }

    registerChangeInSeachRnsCrmRequestMasters() {
        this.eventManager.subscribe('selectedSearchCrmRequest', message => {
            this.rnsQuotation.crmRequestNumber = message.content.crmCode;
            this.rnsQuotation.requestedBy = message.content.requestedBy;
            this.rnsQuotation.targetPcd = message.content.targetPcd;
            this.rnsQuotation.merchantRemarks = message.content.merchantRemarks;
            this.rnsQuotation.date = message.content.date;
            this.rnsQuotation.pchCode = message.content.rnsPchMaster.pchCode;
            this.rnsQuotation.buyerCode = message.content.buyerCode.buyerCode;
            this.rnsQuotation.buyerName = message.content.buyerCode.buyerName;
            this.rnsQuotation.articleCode = message.content.rnsArticleMaster.articleCode;
            this.rnsQuotation.articleDesc = message.content.rnsArticleMaster.articleDesc;

            this.targetPcd = new Date(this.rnsQuotation.targetPcd);
            if (this.rnsQuotation.targetPcd) {
                this.rnsQuotation.targetPcd = {
                    year: this.targetPcd.getFullYear(),
                    month: this.targetPcd.getMonth() + 1,
                    day: this.targetPcd.getDate()
                };
            }
            this.rnsQuotation.date = this.datePipe.transform(this.rnsQuotation.date, 'yyyy-MM-ddTHH:mm:ss');

            console.log('RNS CRM Request is selected, change in search selected', this.rnsQuotation);
        });
    }

    setpayTermsCodeCopy(payTermsCode) {
        if (payTermsCode !== null) {
            this.payTermsCodeCopy[this.variantDisplay] = payTermsCode.toString();
        }
    }

    setdelTermsCodeCopy(delTermsCode) {
        if (delTermsCode !== null) {
            this.delTermsCodeCopy[this.variantDisplay] = delTermsCode.toString();
        }
    }

    previousState() {
        window.history.back();
    }

    registerChangeInSearchVendor() {
        this.eventManager.subscribe('selectedSearchvendor', message => {
            let vendorExistCheck = false;
            this.variants.forEach(variant => {
                if (variant.title === this.variantDisplay) {
                    if (this.vendors.length > 0) {
                        message.content.paymentTerms = this.payTermsCodeCopy[this.variantDisplay];
                        message.content.deliveryTerms = this.delTermsCodeCopy[this.variantDisplay];
                        message.content.expiryQty = variant.orderQuantity;
                        if (!message.content.currency) {
                            message.content.currency = 'INR';
                        }
                        const quotationVendors = {
                            vendorQuotation: this.rnsQuotation,
                            variant: variant,
                            vendor: message.content,
                            paymentTerms: this.payTermsCodeCopy[this.variantDisplay],
                            deliveryTerms: this.delTermsCodeCopy[this.variantDisplay],
                            expiryQty: variant.orderQuantity,
                            currency: message.content.currency
                        };

                        vendorExistCheck = false;
                        let vendorIncrement = 0;
                        this.vendors.forEach(vendor => {
                            vendorIncrement++;
                            if (Number(message.content.id) === Number(vendor.vendor.id) && vendor.variant.title === variant.title) {
                                vendorExistCheck = true;
                            }
                            if (Number(vendorIncrement) === Number(this.vendors.length)) {
                                if (!vendorExistCheck) {
                                    this.vendors.push(quotationVendors);
                                }
                            }
                        });
                    } else {
                        if (!message.content.currency) {
                            message.content.currency = 'INR';
                        }
                        const quotationVendors = {
                            vendorQuotation: this.rnsQuotation,
                            variant: variant,
                            vendor: message.content,
                            paymentTerms: this.payTermsCodeCopy[this.variantDisplay],
                            deliveryTerms: this.delTermsCodeCopy[this.variantDisplay],
                            expiryQty: variant.orderQuantity,
                            currency: message.content.currency
                        };
                        this.vendors.push(quotationVendors);
                    }
                }
            });
        });

        this.eventManager.subscribe('selectedSearchRemoveVendor', vendorId => {
            this.variants.forEach(variant => {
                variant.vendors.forEach((value, key) => {
                    if (Number(value.id) === Number(vendorId.content)) {
                        console.log('deleted', value);
                        variant.vendors.splice(key, 1);
                    }
                });
            });
            console.log('RNS Vendor is Deleted, change in search selected', vendorId, this.variants);
        });
    }

    registerChangeInSeachRnsArticle() {
        this.eventManager.subscribe('selectedSearchArticle', message => {
            this.rnsQuotation.articleCode = message.content.articleCode;
            this.rnsQuotation.articleDesc = message.content.articleDesc;
            console.log('RNS Article is selected, change in search selected', this.rnsQuotation);
        });
    }

    displayVariant(variantName) {
        // console.log(variantName);
        this.variantDisplay = variantName;
    }

    removeVariant(variantName) {
        this.variants.forEach((variant, key) => {
            if (variant.title === variantName) {
                console.log('deleted', variant);
                this.vendors.forEach((vendor, vendorKey) => {
                    if (vendor.variant.title === variant.title) {
                        this.vendors.splice(vendorKey, 1);
                    }
                });
                this.variants.splice(key, 1);
            }
        });
        let ctr = 0;
        this.variants.forEach(variant => {
            variant.title = 'Variant ' + ++ctr;
        });
        this.variantDisplay = this.variants[0].title;
        this.notifier.notify('success', 'Please note this action has reset the lot numbers.');
    }

    removeVendor(vendor_id, variant) {
        this.vendors.forEach((vendor, vendorKey) => {
            if (vendor.variant === variant && vendor.vendor.login === vendor_id) {
                this.vendors.splice(vendorKey, 1);
            }
        });
    }

    copyVendor(vendorCopy) {
        // console.log('693',  vendorCopy);
        this.variants.forEach(variant => {
            let existVariant = false;
            let ctr = 0;
            const vendorCodeTemp = vendorCopy.vendor.login;
            this.vendors.forEach(vendor => {
                ++ctr;
                // console.log(vendorCodeTemp, vendor.vendor.login, variant.title, vendor.variant.title);
                if (vendorCodeTemp === vendor.vendor.login && variant.title === vendor.variant.title) {
                    existVariant = true;
                }
            });
            if (existVariant === false && ctr === this.vendors.length) {
                const copy: RnsQuotationVendors = Object.assign({}, vendorCopy);
                copy.variant = variant;
                copy.paymentTerms = this.payTermsCodeCopy[variant.title];
                copy.deliveryTerms = this.delTermsCodeCopy[variant.title];
                copy.expiryQty = variant.orderQuantity;
                this.vendors.push(copy);
            }
        });
        this.notifier.notify('success', 'Copied to all Lots!');
    }

    copyVariant(variantData) {
        console.log(this.variants, 'Line 503');
        if (this.variants.length + 1 > 50) {
            this.notifier.notify('success', 'Max allowed lots 50');
            return false;
        } else {
            const variantName = 'Variant ' + (this.variants.length + 1);

            const copy: any = Object.assign({}, variantData);
            delete copy['id'];
            copy['title'] = variantName;
            this.payTermsCodeCopy[variantName] = copy.dealtermPaymentTerms;
            this.delTermsCodeCopy[variantName] = copy.dealtermDeliveryTerms;
            console.log(this.variants, 'Line 507');
            this.variants.push(copy);
            console.log(this.variants, 'Line 509');
            this.displayVariant(variantName);
            if (this.vendors.length > 0) {
                this.vendors.forEach(vendor => {
                    if (vendor.variant.title === variantData.title) {
                        const quotationVendors = {
                            vendorQuotation: this.rnsQuotation,
                            variant: copy,
                            vendor: vendor.vendor,
                            paymentTerms: this.payTermsCodeCopy[this.variantDisplay],
                            deliveryTerms: this.delTermsCodeCopy[this.variantDisplay],
                            expiryQty: vendor.expiryQty,
                            expDelDatedate: vendor.expDelDatedate,
                            currency: vendor.currency
                        };
                        delete quotationVendors['id'];
                        delete quotationVendors.vendor['id'];
                        this.vendors.push(quotationVendors);
                    }
                });
            }
        }
    }

    createNewVariant() {
        if (this.variants.length + 1 > 50) {
            this.notifier.notify('error', 'Max allowed lots 50');
            return false;
        } else {
            const variantName = 'Variant ' + (this.variants.length + 1);

            this.variants.push({
                title: variantName,
                openCosting: 'Not Required',
                quotation: this.rnsQuotation
            });
            this.displayVariant(variantName);
        }
    }

    changeValidityDate(date) {
        console.log(date);
        date = new Date(date);
        this.minDate = {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            day: date.getDate()
        };
        console.log(this.minDate);
    }

    spec2ValueChange(spec2Value) {
        console.log(spec2Value);
    }

    spec2TitleChange(title, inputID) {
        console.log(title);
        this.subcodeInput[inputID]['display'] = true;
        this.rnsRefrDetails.forEach(refDetail => {
            if (title === refDetail.subCode) {
                console.log(refDetail.rnsRefrMasters.length);
                if (refDetail.rnsRefrMasters.length > 0) {
                    this.subcodeInput[inputID]['display'] = false;
                    this.subcodeInput[inputID]['data'] = refDetail.rnsRefrMasters;
                }
            }
        });
    }

    saveQuotation(form: NgForm) {
        this.isValidQuotationFormSubmitted = false;
        this.saveVendors = this.vendors;

        const set = new Set();
        this.vendors.forEach(vendors => {
            this.saveVendors = this.vendors;
            set.add(vendors.variant.title);
        });

        this.variants.forEach(variants => {
            this.validateQuotationVariant = this.variants;
        });

        this.validateQuotationVariant = this.variants;
        if (form.invalid || this.validateQuotationVariant === '' || this.validateQuotationVariant === 'null') {
            this.notifier.notify('error', 'please fill all required feilds.');
        } else {
            let message = '';
            this.variants.forEach(variants => {
                if (set.has(variants.title)) {
                } else {
                    message += this.lotNames[variants.title] + ',';
                }
            });
            if (message.length > 0) {
                message = message.substring(0, message.lastIndexOf(','));
                this.notifier.notify('error', 'Please select vendors in ' + message);
            } else {
                this.isValidQuotationFormSubmitted = true;
                console.log(this.rnsQuotation);
                this.saveQuotationText = 'Saving ...';
                this.isSaving = true;
                this.rnsQuotation.template = this.selectedTemplatesSelect.id;
                const isRfq = <HTMLInputElement>document.getElementById('isRfq');
                if (isRfq.checked) {
                    this.rnsQuotation.rfqApplicable = true;
                } else {
                    this.rnsQuotation.rfqApplicable = false;
                }
                const isRfb = <HTMLInputElement>document.getElementById('isRfb');
                if (isRfb.checked) {
                    this.rnsQuotation.auctionApplicable = true;
                } else {
                    this.rnsQuotation.auctionApplicable = false;
                }
                this.subscribeToSaveResponse(this.rnsQuotationService.update(this.rnsQuotation));
            }
        }
    }

    changeTemplate(template) {
        console.log('changeTemplate', template);
        this.selectedTemplates = template;
    }

    private updateVariants(rnsQuotation) {
        this.variants.forEach(variant => {
            variant.quotation = rnsQuotation;
        });
        this.subscribeToSaveVariantResponse(this.rnsQuotationVariantService.updateMultiple(this.variants));
    }

    copyExpDelDate(title) {
        let ctr = 0;
        let tempDate: any;
        this.vendors.forEach(vendor => {
            if (ctr === 0 && vendor.variant.title === title) {
                ++ctr;
                tempDate = vendor.expDelDatedate;
            } else if (vendor.variant.title === title) {
                vendor.expDelDatedate = tempDate;
            }
        });
    }

    copyEnqQty(title) {
        let ctr = 0;
        let tempexpiryQty: any;
        this.vendors.forEach(vendor => {
            if (ctr === 0 && vendor.variant.title === title) {
                ++ctr;
                tempexpiryQty = vendor.expiryQty;
            } else if (vendor.variant.title === title) {
                vendor.expiryQty = tempexpiryQty;
            }
        });
    }

    copyEnqQtyAll(variant) {
        this.vendors.forEach(vendor => {
            if (vendor.variant.title === variant.title) {
                vendor.expiryQty = variant.orderQuantity;
            }
        });
    }

    private updateVendors(rnsQuotation, result: RnsQuotationVariant[]) {
        let vendorIncrement = 0;
        this.vendors.forEach(vendor => {
            vendorIncrement++;
            result.forEach(variant => {
                if (vendor.variant.title === variant.title) {
                    vendor.variant = variant;
                    delete vendor.variant;
                    delete vendor.vendorQuotation;

                    vendor.paymentTermsChargeType = 'AMOUNT';
                    vendor.paymentTermsCharge = '0';
                    vendor.deliveryTermsChargeType = 'AMOUNT';
                    vendor.deliveryTermsCharge = '0';

                    vendor.variant = {
                        id: variant.id,
                        title: variant.title
                    };
                    vendor.vendorQuotation = {
                        id: rnsQuotation.id
                    };
                    vendor.expDelDate = this.dateUtils.convertLocalDateToServer(vendor.expDelDatedate);
                    vendor.vendorCode = vendor.vendor.login;
                    // console.log('Line 636', vendor.expDelDate)
                    if (vendorIncrement > this.vendors.length) {
                        this.router.navigateByUrl('/home');
                    } else {
                        this.subscribeToSaveVendorResponse(this.rnsQuotationVendorsService.update(vendor));
                    }
                }
            });
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotation>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotation>) => this.quotationSaved(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private subscribeToSaveVariantResponse(result: Observable<HttpResponse<RnsQuotationVariant[]>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => this.quotationVariantSavedVariant(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private subscribeToSaveVendorResponse(result: Observable<HttpResponse<RnsQuotationVendors>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVendors>) => this.quotationvendorsSaved(RnsQuotationVendors),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private quotationSaved(result: HttpResponse<RnsQuotation>) {
        console.log('Quotation Updated, ', result);
        this.rnsQuotation.id = result.body.id;
        const copy = this.convert(this.rnsQuotation);
        if (this.variants.length > 0) {
            this.updateVariants(copy);
            this.saveQuotationText = 'Saving Variants...';
        } else {
            this.saveQuotationText = 'Save Project';
            this.router.navigateByUrl('/home');
        }
    }

    private quotationVariantSavedVariant(result: HttpResponse<RnsQuotationVariant[]>) {
        console.log('RnsQuotationVariant Updated result:::;;;, ', result);
        const copy = this.convert(this.rnsQuotation);
        // console.log('result', result)
        if (typeof this.vendors !== 'undefined') {
            this.updateVendors(copy, result.body);
            this.saveQuotationText = 'Saving Party ...';
        } else {
            this.saveQuotationText = 'Save Project';
            this.router.navigateByUrl('/home');
        }
    }

    private quotationvendorsSaved(rnsQuotationVendors) {
        // console.log('RnsQuotationVendors Updated, ', RnsQuotationVendors);
        this.saveQuotationText = 'Save Project';
        this.isSaving = false;
        this.router.navigateByUrl('/home');
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

    /**
     * Convert a RnsQuotation to a JSON which can be sent to the server.
     */
    private convert(rnsQuotation: RnsQuotation): RnsQuotation {
        const copy: RnsQuotation = Object.assign({}, rnsQuotation);

        // copy.validity = this.dateUtils.toDate(rnsQuotation.validity);
        // copy.createdOn = this.dateUtils.toDate(rnsQuotation.createdOn);
        // copy.date = this.dateUtils.toDate(rnsQuotation.date);

        if (rnsQuotation.targetPcd !== '') {
            copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotation.targetPcd);
        }
        copy.createdOn = rnsQuotation.createdOn != null ? moment(rnsQuotation.createdOn) : null;
        copy.date = rnsQuotation.date != null ? moment(rnsQuotation.date) : null;
        return copy;
    }
}
