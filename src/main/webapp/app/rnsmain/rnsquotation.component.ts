import { Component, OnInit } from '@angular/core';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import { Observable, Subscription } from 'rxjs/Rx';
import { DatePipe, Location } from '@angular/common';
import { NgForm } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsBuyerMaster, RnsBuyerMasterService } from 'app/entities/rns-buyer-master';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { RnsUomMaster, RnsUomMasterService } from 'app/entities/rns-uom-master';
import { RnsVendorRemark, RnsVendorRemarkService, RnsVendorRemarkSocketService } from 'app/entities/rns-vendor-remark';
import { RnsTaxTermsMaster, RnsTaxTermsMasterService } from 'app/entities/rns-tax-terms-master';
import { RnsSectorMaster, RnsSectorMasterService } from 'app/entities/rns-sector-master';
import { RnsDelTermsMaster, RnsDelTermsMasterService } from 'app/entities/rns-del-terms-master';
import { Template, TemplateService } from 'app/entities/template';
import { Currency, CurrencyService } from 'app/entities/currency';
import { RnsPayTermsMaster, RnsPayTermsMasterService } from 'app/entities/rns-pay-terms-master';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';
import { RnsDelPlaceMaster, RnsDelPlaceMasterService } from 'app/entities/rns-del-place-master';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from 'app/entities/rns-source-team-master';
import { RnsPchMaster, RnsPchMasterService } from 'app/entities/rns-pch-master';
import { Account, LoginModalService, Principal, UserService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import { VariantDataService } from 'app/vendor/variant-data.service';
import * as moment from 'moment';
import * as FileSaver from 'file-saver';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsquotation.component.html'
})
export class RnsQuotationComponent implements OnInit {
    account: Account;
    rnsCatgMasters: RnsCatgMaster[];
    rnsCatgMaster: RnsCatgMaster;
    rnsRefrDetails: RnsRefrDetails[];
    rnsBuyerMasters: RnsBuyerMaster[];
    rnsTypeMaster: RnsTypeMaster;
    rnsTypeMasters: RnsTypeMaster[];
    rnsUomMasters: RnsUomMaster[];
    rnsVendorRemarks: RnsVendorRemark[];
    remarksIdContainer: Map<number, number>;
    rnsVendorRemarkPost: RnsVendorRemark;
    rnsUomMaster: RnsUomMaster;
    rnsTaxTermsMasters: RnsTaxTermsMaster[];
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    rnsSectorMasters: RnsSectorMaster[];
    rnsSectorMaster: RnsSectorMaster;
    rnsTypeMastersResponseData: RnsTypeMaster[];
    rnsSectorMastersResponseData: RnsSectorMaster[];
    rnsDelTermsMasters: RnsDelTermsMaster[];
    rnsDelTermsMaster: RnsDelTermsMaster;
    templates: Template[];
    currencies: Currency[];
    rnsPayTermsMasters: RnsPayTermsMaster[];
    rnsPayTermsMaster: RnsPayTermsMaster;
    rnsPayTermsMastersResponseData: RnsPayTermsMaster[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    variants: RnsQuotationVariant[];
    vendors: RnsQuotationVendors[];
    vendor: RnsQuotationVendors;
    rnsDelPlaceMaster: RnsDelPlaceMaster;
    rnsDelPlaceMasters: RnsDelPlaceMaster[];
    rnsSourceTeamMasters: RnsSourceTeamMaster[];
    rnsPchMasters: RnsPchMaster[];
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
    confDelDate: any;
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
    validityExpiredExist: Boolean;
    rfqMessage: string;
    rfqDate: any;
    workFlowText: String;
    isSaving: Boolean;
    chatOpenofVendor: String;
    highlightChat: any;
    chatBtnText: String;
    isWait: boolean;

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
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private templateService: TemplateService,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        private currencyService: CurrencyService,
        private userService: UserService,
        private router: Router,
        private location: Location,
        private rnsVendorRemarkSocketService: RnsVendorRemarkSocketService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private comparentchildservice: ComParentChildService,
        private rnsPchMasterService: RnsPchMasterService,
        private variantDataService: VariantDataService,
        private notifier: NotifierService
    ) {}

    async loadAll() {
        this.isValidQuotationFormSubmitted = true;
        this.rnsCatgMasterService.query().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnsCatgMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.templateService.query().subscribe(
            (res: HttpResponse<Template[]>) => {
                this.templates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsTypeMasterService.query().subscribe(
            (res: HttpResponse<RnsTypeMaster[]>) => {
                this.rnsTypeMastersResponseData = res.body;
                this.rnsTypeMasters = [];
                const trnsCatgMaster = this.rnsQuotation.rnsCatgCode as RnsCatgMaster;
                if (trnsCatgMaster != null && trnsCatgMaster.id != null) {
                    const id = trnsCatgMaster.id;
                    for (const item in this.rnsTypeMastersResponseData) {
                        if (Number(this.rnsTypeMastersResponseData[item].rnsCatgCode.id) === Number(id)) {
                            this.rnsTypeMasters.push(this.rnsTypeMastersResponseData[item]);
                        }
                    }
                }
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
            (res: HttpResponse<RnsSectorMasters[]>) => {
                this.rnsSectorMastersResponseData = res.body;
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

        this.rnsPchMasterService.query().subscribe(
            (res: HttpResponse<RnsPchMaster[]>) => {
                this.rnsPchMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.isWait = false;
        this.rnsQuotation = {} as RnsQuotation;
        this.varDescSpecValues = [];
        this.variants = [{ title: 'Variant 1', vendors: [], quotation: this.rnsQuotation }];
        this.vendors = [];
        this.highlightChat = {};
        this.subcodeInput = {
            input1: { display: true, data: [] },
            input2: { display: true, data: [] },
            input3: { display: true, data: [] },
            input4: { display: true, data: [] },
            input5: { display: true, data: [] }
        };
        this.lotNames = this.variantDataService.fetchVariant();
        this.selectedTemplates = {};

        this.remarksIdContainer = new Map<number, number>();
        this.eventSubscriber = this.comparentchildservice.on('call-parent').subscribe(() => this.refreshVendors());
        this.eventSubscriber = this.comparentchildservice.on('call-variant-parent').subscribe(() => this.checkQuotationDetails());
        this.loadAll();
        this.checkQuotationDetails();
        this.principal.identity().then(account => {
            this.account = account;
        });

        this.saveQuotationText = 'Save Project';
        this.registerAuthenticationSuccess();
        this.registerChangeInRnsCatgMasters();
        this.registerChangeInRnsRefrDetails();
        this.registerChangeInSeachRnsCrmRequestMasters();
        this.registerChangeInSeachRnsArticle1();
        this.registerChangeInSearchVendor();
        this.payTermsCodeCopy = {};
        this.delTermsCodeCopy = {};
        this.workFlowText = 'WorkFlow';
        this.chatBtnText = 'Send';
    }

    allowEntry() {
        if (this.account && this.rnsQuotation && this.rnsQuotation.user) {
            if (
                this.rnsQuotation.user.login !== this.account.login ||
                this.rnsQuotation.approvedFlag === 'C' ||
                this.rnsQuotation.closedDate !== null
            ) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    hiddenEntry() {
        if (this.account && this.rnsQuotation && this.rnsQuotation.user) {
            if (this.rnsQuotation.user.login === this.account.login && !this.rnsQuotation.approvedFlag && !this.rnsQuotation.closedDate) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    bestPrice(variantTitle, value) {
        const arr = [];
        this.vendors.forEach(vendor => {
            if (vendor.variant.title === variantTitle && vendor.ctc > 0) {
                arr.push(vendor.ctc);
            }
        });
        if (this.rnsQuotation.eventType === 'AUCTION') {
            const maxValue = Math.max.apply(null, arr);
            if (value === maxValue) {
                return true;
            } else {
                return false;
            }
        } else if (this.rnsQuotation.eventType === 'REVERSE_AUCTION') {
            const minValue = Math.min.apply(null, arr);
            if (value === minValue) {
                return true;
            } else {
                return false;
            }
        }
    }

    refreshVendors() {
        this.rnsQuotationVendorsService.getByQuotationId(this.rnsQuotation.id).subscribe((res: HttpResponse<RnsQuotationVendors[]>) => {
            this.vendors = res.body;

            this.vendors.forEach(vendor => {
                /*this.userService.findpost(vendor.vendorCode).subscribe(user => {
                    vendor.vendor = user.body;
                });*/

                // console.log('vendor.expDelDate',vendor.expDelDate)

                if (vendor.expDelDate != null) {
                    const expDelDatedate = this.dateUtils.convertLocalDateFromServer(vendor.expDelDate);
                    vendor.expDelDatedate = {
                        year: expDelDatedate.getFullYear(),
                        month: expDelDatedate.getMonth() + 1,
                        day: expDelDatedate.getDate()
                    };
                    vendor.minExpDelDate = {
                        year: expDelDatedate.getFullYear(),
                        month: expDelDatedate.getMonth() + 1,
                        day: expDelDatedate.getDate()
                    };
                    vendor.minConfDelDate = {
                        year: expDelDatedate.getFullYear(),
                        month: expDelDatedate.getMonth() + 1,
                        day: expDelDatedate.getDate()
                    };
                } else {
                    const expDelDatedate = new Date();
                    vendor.minExpDelDate = {
                        year: expDelDatedate.getFullYear(),
                        month: expDelDatedate.getMonth() + 1,
                        day: expDelDatedate.getDate()
                    };
                }

                if (vendor.confDelDate != null) {
                    const confDelDatedate = this.dateUtils.convertLocalDateFromServer(vendor.confDelDate);
                    vendor.confDelDatedate = {
                        year: confDelDatedate.getFullYear(),
                        month: confDelDatedate.getMonth() + 1,
                        day: confDelDatedate.getDate()
                    };
                }
                if (vendor.bidRate) {
                    vendor.ctc = Number(vendor.bidRate) + Number(vendor.upCharge);
                } else if (vendor.regularRate) {
                    vendor.ctc = Number(vendor.regularRate) + Number(vendor.upCharge);
                }
            });
        });
    }

    checkQuotationDetails() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                const id = params['id'];
                this.rnsQuotationService.find(id).subscribe(rnsQuotationdata => {
                    this.getTemplates(rnsQuotationdata.body.rnsCatgCode.id, rnsQuotationdata.body);
                    rnsQuotationdata.body.validity = this.datePipe.transform(rnsQuotationdata.body.validity, 'yyyy-MM-ddTHH:mm:ss');
                    rnsQuotationdata.body.createdOn = this.datePipe.transform(rnsQuotationdata.body.createdOn, 'yyyy-MM-ddTHH:mm:ss');

                    if (rnsQuotationdata.body.targetPcd) {
                        this.targetPcd = rnsQuotationdata.body.targetPcd;
                        rnsQuotationdata.body.targetPcd = {
                            year: rnsQuotationdata.body.targetPcd.getFullYear(),
                            month: rnsQuotationdata.body.targetPcd.getMonth() + 1,
                            day: rnsQuotationdata.body.targetPcd.getDate()
                        };
                    }

                    rnsQuotationdata.body.date = this.datePipe.transform(rnsQuotationdata.body.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.rnsQuotation = rnsQuotationdata.body;

                    let existsourceTeam = false;
                    this.rnsSourceTeamMasters.forEach(rnsSourceTeamMaster => {
                        if (Number(this.rnsQuotation.sourceTeam) === rnsSourceTeamMaster.id) {
                            existsourceTeam = true;
                        }
                    });
                    if (existsourceTeam === false) {
                        this.rnsSourceTeamMasterService.find(Number(this.rnsQuotation.sourceTeam)).subscribe(sourceTeam => {
                            this.rnsSourceTeamMasters.push(sourceTeam.body);
                        });
                    }

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
                            });
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );

                    this.rnsQuotationVendorsService.getByQuotationId(id).subscribe((res: HttpResponse<RnsQuotationVendors[]>) => {
                        this.vendors = res.body;

                        this.vendors.forEach(vendor => {
                            this.userService.findpost(vendor.vendorCode).subscribe(user => {
                                vendor.vendor = user.body;

                                this.rnsVendorRemarkSocketService.subscribe(id, user.body.email);
                                this.rnsVendorRemarkSocketService.receive().subscribe(activity => {
                                    const data = activity.body;
                                    if (this.chatOpenofVendor === data.vendorEmail) {
                                        if (this.remarksIdContainer.has(activity.body.id)) {
                                        } else {
                                            this.remarksIdContainer.set(activity.body.id, activity.body.id);
                                            this.rnsVendorRemarks.push(data);
                                        }
                                        this.chatBtnText = 'Send';
                                        this.rnsVendorRemarkPost.remarkText = '';
                                        return false;
                                    }

                                    this.highlightChat[data.vendorEmail] = true;
                                });
                            });

                            // console.log('vendor.expDelDate',vendor.expDelDate)

                            if (vendor.expDelDate != null) {
                                const expDelDatedate = this.dateUtils.convertLocalDateFromServer(vendor.expDelDate);
                                vendor.expDelDatedate = {
                                    year: expDelDatedate.getFullYear(),
                                    month: expDelDatedate.getMonth() + 1,
                                    day: expDelDatedate.getDate()
                                };
                                vendor.minExpDelDate = {
                                    year: expDelDatedate.getFullYear(),
                                    month: expDelDatedate.getMonth() + 1,
                                    day: expDelDatedate.getDate()
                                };
                                vendor.minConfDelDate = {
                                    year: expDelDatedate.getFullYear(),
                                    month: expDelDatedate.getMonth() + 1,
                                    day: expDelDatedate.getDate()
                                };
                            } else {
                                const expDelDatedate = new Date();
                                vendor.minExpDelDate = {
                                    year: expDelDatedate.getFullYear(),
                                    month: expDelDatedate.getMonth() + 1,
                                    day: expDelDatedate.getDate()
                                };
                            }

                            if (vendor.confDelDate != null) {
                                const confDelDatedate = this.dateUtils.convertLocalDateFromServer(vendor.confDelDate);
                                vendor.confDelDatedate = {
                                    year: confDelDatedate.getFullYear(),
                                    month: confDelDatedate.getMonth() + 1,
                                    day: confDelDatedate.getDate()
                                };
                            }
                            if (vendor.bidRate) {
                                vendor.ctc = Number(vendor.bidRate) + Number(vendor.upCharge);
                            } else if (vendor.regularRate) {
                                vendor.ctc = Number(vendor.regularRate) + Number(vendor.upCharge);
                            }
                        });
                    });

                    this.vendors.forEach(vendor => {
                        if (this.vendors.length > 0) {
                            this.vendors.forEach(vendore => {
                                vendore.allEditable = false;
                                if (!vendore.quoteQty) {
                                    if (this.validityExpired && this.rnsQuotation.rfq) {
                                        vendore.allEditable = true;
                                    }
                                }
                            });

                            this.rnsVendorRemarks.forEach(remark => {
                                if (remark.vendorEmail === vendor.vendor.email) {
                                    vendor.chatActive = remark;
                                }
                            });
                        }
                    });

                    // console.log('rnsQuotationdata.validity', rnsQuotationdata.validity);
                    if (rnsQuotationdata.body.validity != null) {
                        const date = new Date(rnsQuotationdata.body.validity);
                        const todaysDate = new Date();
                        // console.log(date < todaysDate, date > todaysDate)
                        this.validityExpired = false;
                        if (todaysDate > date) {
                            this.validityExpired = true;
                        } else if (date >= todaysDate) {
                            this.validityExpiredExist = true;
                            this.rfqMessage = 'RFQ in progress';
                            this.rfqDate = date;
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
            }
        });
    }

    isAwardQtyEntered() {
        let disabledAward = false;
        this.vendors.forEach(value => {
            if (value.awardQty !== null && Number(value.awardQty) > 0) {
                disabledAward = true;
            }
        });
        return disabledAward;
    }

    isAwardQty() {
        let disabledAward = false;
        const isRfq = <HTMLInputElement>document.getElementById('isRfq');
        if (isRfq.checked) {
            if (this.rnsQuotation.rfq === true) {
            } else {
                disabledAward = true;
            }
        }
        if (disabledAward === false) {
            const isRfb = <HTMLInputElement>document.getElementById('isRfb');
            if (isRfb.checked) {
                if (this.rnsQuotation.auction === true) {
                } else {
                    disabledAward = true;
                }
            }
        }
        if (disabledAward === false) {
            if (this.rnsQuotation.workflowStatus != null) {
                disabledAward = true;
            }
        }
        return disabledAward;
    }

    getTemplates(id, rnsQuotationData) {
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

    changeTemplate(template) {
        this.selectedTemplates = template;
    }

    publishQuotation(rnsQuotationData, eventData) {
        // console.log(rnsQuotationData);
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
        // console.log('Response', Response);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    refreshVendor() {
        // console.log('refreshVendor', )
        this.variants.forEach(variant => {
            // console.log('refreshVendorcondition', this.variantDisplay)
            if (variant.title === this.variantDisplay) {
                // console.log('refreshVendorcondition')
                if (this.vendors.length > 0) {
                    this.vendors.forEach(vendor => {
                        if (vendor.variant.title === variant.title) {
                            // console.log('condition match',)
                            vendor.paymentTerms = this.payTermsCodeCopy[this.variantDisplay];
                            vendor.deliveryTerms = this.delTermsCodeCopy[this.variantDisplay];
                            vendor.expiryQty = variant.orderQuantity;
                        }
                    });
                }
            }
        });
    }

    setpayTermsCodeCopy(payTermsCode) {
        if (payTermsCode != null) {
            this.payTermsCodeCopy[this.variantDisplay] = payTermsCode.toString();
        }
    }

    setdelTermsCodeCopy(delTermsCode) {
        if (delTermsCode != null) {
            this.delTermsCodeCopy[this.variantDisplay] = delTermsCode.toString();
        }
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

            // console.log('RNS CRM Request is selected, change in search selected rnsQuotation', this.rnsQuotation, );
        });
    }

    registerChangeInSearchVendor() {
        this.eventManager.subscribe('selectedSearchvendor', message => {
            let vendorExistCheck = false;
            // console.log('Line 365');
            this.variants.forEach(variant => {
                if (variant.title === this.variantDisplay) {
                    if (this.vendors.length > 0) {
                        // console.log('Line 623', this.payTermsCodeCopy);
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
                            regularRate: null,
                            currency: message.content.currency
                        };

                        // console.log(this.vendors);
                        vendorExistCheck = false;
                        let vendorIncrement = 0;
                        this.vendors.forEach(vendor => {
                            // console.log('Line 635', message.content.id, vendor.vendor.id, message.content.id == vendor.vendor.id);
                            vendorIncrement++;
                            if (Number(message.content.id) === Number(vendor.vendor.id) && vendor.variant.title === variant.title) {
                                // console.log('Line 378')
                                vendorExistCheck = true;
                            }
                            // console.log('Line 381', vendorIncrement, this.vendors.length)
                            if (Number(vendorIncrement) === Number(this.vendors.length)) {
                                if (!vendorExistCheck) {
                                    // console.log('Line 531')
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
                            regularRate: null,
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
                    // console.log('To Be deleted', value, value.id, vendorId, parseInt(value.id)==parseInt(vendorId.content));
                    if (Number(value.id) === Number(vendorId.content)) {
                        // console.log('deleted', value);
                        variant.vendors.splice(key, 1);
                    }
                });
            });
        });
    }

    registerChangeInSeachRnsArticle1() {
        this.eventManager.subscribe('selectedSearchArticle', message => {
            this.rnsQuotation.articleCode = message.content.articleCode;
            this.rnsQuotation.articleDesc = message.content.articleDesc;
            // console.log('RNS Article is selected, change in search selected', this.rnsQuotation);
        });
    }

    displayVariant(variantName) {
        // console.log(variantName);
        this.variantDisplay = variantName;
    }

    removeVariant(variantName) {
        this.variants.forEach((variant, key) => {
            if (variant.title === variantName) {
                if (variant.id !== undefined && variant.id !== null) {
                    if (
                        confirm(
                            this.lotNames[variantName] +
                                ' is already saved. Are you sure you want to delete? Warning! Any unsaved changes will be lost!'
                        )
                    ) {
                        this.rnsQuotationVariantService.delete(variant.id).subscribe(response => {
                            this.router.navigate(['/']).then(result => {
                                this.router.navigate(['/quotation/' + this.rnsQuotation.id + '/edit']).then(result1 => {
                                    this.notifier.notify('success', 'Please note this action has reset the lot numbers.');
                                });
                            });
                        });
                    }
                } else {
                    // console.log('deleted', variant);
                    this.vendors.forEach((vendor, vendorKey) => {
                        if (vendor.variant.title === variant.title) {
                            this.vendors.splice(vendorKey, 1);
                        }
                    });
                    this.variants.splice(key, 1);

                    let ctr = 0;
                    this.variants.forEach(variant => {
                        variant.title = 'Variant ' + ++ctr;
                    });
                    this.variantDisplay = this.variants[0].title;
                    this.notifier.notify('success', 'Please note this action has reset the lot numbers.');
                }
            }
        });
    }

    removeVendor(vendor_id, variant) {
        // console.log('Line 441')
        this.vendors.forEach((vendor, vendorKey) => {
            if (vendor.variant === variant && vendor.vendor.login === vendor_id) {
                this.vendors.splice(vendorKey, 1);
                this.rnsQuotationVendorsService.delete(vendor.id).subscribe(response => {
                    this.eventManager.broadcast({
                        name: 'rnsQuotationVendorsListModification',
                        content: 'Deleted an rnsQuotationVendors'
                    });
                });
            }
        });
    }

    copyVariant(variantData) {
        // console.log(this.variants, 'Line 723');
        if (this.variants.length + 1 > 50) {
            this.notifier.notify('error', 'Max allowed lots 50');
            return false;
        } else {
            const variantName = 'Variant ' + (this.variants.length + 1);

            const copy: any = Object.assign({}, variantData);
            delete copy['id'];
            copy['title'] = variantName;
            this.payTermsCodeCopy[variantName] = copy.dealtermPaymentTerms;
            this.delTermsCodeCopy[variantName] = copy.dealtermDeliveryTerms;
            // console.log(this.variants, 'Line 730');
            this.variants.push(copy);
            // console.log(this.variants, 'Line 732');
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
                delete copy['id'];
                this.vendors.push(copy);
            }
        });
        this.notifier.notify('success', 'Copied to all Lots!');
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
        // console.log(date);
        date = new Date(date);
        this.minDate = {
            year: date.getFullYear(),
            month: date.getMonth() + 1,
            day: date.getDate()
        };
        // console.log(this.minDate);
    }

    spec2ValueChange(spec2Value) {
        // console.log(spec2Value);
    }

    spec2TitleChange(title, inputID) {
        // console.log(title);
        this.subcodeInput[inputID]['display'] = true;
        this.rnsRefrDetails.forEach(refDetail => {
            if (title === refDetail.subCode) {
                // console.log(refDetail.rnsRefrMasters.length);
                if (refDetail.rnsRefrMasters.length > 0) {
                    this.subcodeInput[inputID]['display'] = false;
                    this.subcodeInput[inputID]['data'] = refDetail.rnsRefrMasters;
                }
            }
        });
    }

    noSubmit() {
        return false;
    }

    suspendProject() {
        if (confirm('Stopping the project will not allow you to work on the project further! Are you sure to stop project?')) {
            this.subscribeToSaveResponseSuspend(this.rnsQuotationService.closed(this.rnsQuotation.id));
        }
    }

    saveQuotation(form: NgForm) {
        this.isValidQuotationFormSubmitted = false;

        const set = new Set();
        this.vendors.forEach(vendors => {
            console.log('Converted Item, ', vendors);
            set.add(vendors.variant.title);
        });

        if (form.invalid) {
            this.notifier.notify('error', 'please fill all required feilds.');
        } else {
            let message = '';
            this.variants.forEach(variants => {
                console.log(set.has(variants.title));
                if (set.has(variants.title)) {
                } else {
                    message += this.lotNames[variants.title] + ',';
                }
            });
            if (message.length > 0) {
                message = message.substring(0, message.lastIndexOf(','));
                this.notifier.notify('error', 'Please select vendors in ' + message);
            } else {
                // console.log(form.invalid);
                this.isValidQuotationFormSubmitted = true;
                console.log('all quation data :::::::', this.rnsQuotation);
                this.saveQuotationText = 'Saving ...';
                this.isSaving = true;
                this.rnsQuotation.template = this.selectedTemplatesSelect.id;
                this.rnsQuotation.validity = this.datePipe.transform(this.rnsQuotation.validity, 'yyyy-MM-ddTHH:mm:ss');
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
                /* this.vendors[0].confDelDate=this.datePipe.transform(this.vendors[0].confDelDate, 'yyyy-MM-dd');
                 console.log('check conform del date data :::::::',this.confDelDate);*/
                this.subscribeToSaveResponse(this.rnsQuotationService.update(this.rnsQuotation));
            }
        }
    }

    private updateVariants(rnsQuotation) {
        this.variants.forEach(variant => {
            // console.log('Converted Item, ', variant);
            variant.quotation = rnsQuotation;
        });
        this.subscribeToSaveVariantResponse(this.rnsQuotationVariantService.updateMultiple(this.variants));
    }

    private updateVendors(rnsQuotation, result) {
        this.vendors.forEach(vendor => {
            result.forEach(variant => {
                // console.log('vendor.variant', vendor.variant)
                if (vendor.variant.title === variant.title) {
                    vendor.variant = variant;

                    vendor.vendorQuotation = rnsQuotation;
                    const copy: RnsQuotationVariant = Object.assign({}, vendor.variant);
                    delete vendor.variant;
                    delete copy.dealtermCompletionBy;
                    delete copy.dealtermValidUntil;
                    // console.log('vendor',vendor);
                    vendor.variant = copy;
                    vendor.vendorCode = vendor.vendor.login;

                    if (vendor.expDelDatedate != null) {
                        vendor.expDelDate = this.dateUtils.convertLocalDateToServer(vendor.expDelDatedate);
                    }
                    if (vendor.confDelDatedate != null) {
                        vendor.confDelDate = this.dateUtils.convertLocalDateToServer(vendor.confDelDatedate);
                        if (vendor.rfqUserType != null && vendor.rfqUserType === 'V') {
                        } else {
                            vendor.rfqUserType = 'S';
                        }
                    }
                    // console.log('Line 636', vendor.expDelDate)
                    // }

                    this.subscribeToSaveVendorResponse(this.rnsQuotationVendorsService.update(vendor));
                }
            });
        });
    }

    private subscribeToSaveResponseSuspend(result: Observable<HttpResponse<RnsQuotation>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotation>) => this.quotationSuspend(res),
            (res: HttpErrorResponse) => this.onError(Response)
        );
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotation>>) {
        result.subscribe((res: HttpResponse<RnsQuotation>) => this.quotationSaved(res), (res: HttpErrorResponse) => this.onError(Response));
    }

    private subscribeToSaveVariantResponse(result: Observable<HttpResponse<RnsQuotationVariant[]>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => this.quotationVariantSavedVariant(res),
            (res: HttpErrorResponse) => this.onError(Response)
        );
    }

    private subscribeToSaveVendorResponse(result: Observable<HttpResponse<RnsQuotationVendors>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVendors>) => this.quotationvendorsSaved(res),
            (res: HttpErrorResponse) => this.onError(Response)
        );
    }

    private quotationSuspend(res: HttpResponse<RnsQuotation>) {
        // console.log('Quotation Updated, ', RnsQuotation);
        this.rnsQuotation = res.body;
        this.notifier.notify('success', 'Project suspended Successfully');
    }

    private quotationSaved(res: HttpResponse<RnsQuotation>) {
        // console.log('Quotation Updated, ', RnsQuotation);
        const copy = this.convert(this.rnsQuotation);
        if (this.variants.length > 0) {
            this.updateVariants(copy);
            this.saveQuotationText = 'Saving Variants...';
        } else {
            this.saveQuotationText = 'Save Quotation';
        }
    }

    private quotationVariantSavedVariant(result: HttpResponse<RnsQuotationVariant[]>) {
        // console.log('RnsQuotationVariant Updated, ', result);
        const copy = this.convert(this.rnsQuotation);
        // console.log('result', result)
        if (typeof this.vendors !== 'undefined') {
            this.updateVendors(copy, result.body);
            this.saveQuotationText = 'Save Vendors ...';
        } else {
            this.saveQuotationText = 'Save Quotation';
        }
    }

    private quotationvendorsSaved(res: HttpResponse<RnsQuotationVendors>) {
        // console.log('RnsQuotationVendors Updated, ', RnsQuotationVendors);
        this.saveQuotationText = 'Save Quotation';
        if (this.isSaving) {
            this.router.navigate(['/']).then(result => {
                this.router.navigate(['/quotation/' + this.rnsQuotation.id + '/edit']).then(result1 => {
                    this.notifier.notify('success', 'Project has been saved.');
                });
            });
        }
        this.isSaving = false;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    exportFile() {
        this.isWait = true;
        this.rnsQuotationService.export(this.rnsQuotation.id).subscribe(
            res => {
                this.isWait = false;
                FileSaver.saveAs(res, `Project#${this.rnsQuotation.id}.xlsx`);
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
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
        // console.log('rnsQuotation.validity', rnsQuotation.validity);
        // copy.validity = this.dateUtils.toDate(rnsQuotation.validity);
        // copy.date = this.dateUtils.toDate(rnsQuotation.date);
        // copy.createdOn = this.dateUtils.toDate(rnsQuotation.createdOn);
        if (rnsQuotation.targetPcd !== '') {
            copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotation.targetPcd);
        }
        copy.createdOn = rnsQuotation.createdOn != null ? moment(rnsQuotation.createdOn) : null;
        copy.date = rnsQuotation.date != null ? moment(rnsQuotation.date) : null;
        copy.validity = rnsQuotation.validity != null ? moment(rnsQuotation.validity) : null;

        return copy;
    }

    public chatboxDisable(email, id) {
        this.rnsVendorRemarks = [];
        this.displaychatBody = true;
        this.chatOpenofVendor = email;
        this.highlightChat[email] = false;
        this.rnsVendorRemarkPost = {
            vendorEmail: email,
            toEmail: email,
            quotation: {
                id: this.rnsQuotation.id
            },
            read: false
        };
        console.log(email);
        this.rnsVendorRemarkService.queryByEmailQuoteId(email, id).subscribe(
            (response: HttpResponse<RnsVendorRemark[]>) => {
                const rnsVendorRemarksTemp: RnsVendorRemark[] = response.body.reverse();
                if (rnsVendorRemarksTemp.length > 0) {
                    rnsVendorRemarksTemp.forEach(remarks => {
                        if (remarks.vendorEmail === email) {
                            this.rnsVendorRemarks.push(remarks);
                        }
                    });
                }
            },
            (response: HttpErrorResponse) => this.onError(response.message)
        );
    }

    public postChatMsg(message) {
        this.rnsVendorRemarkPost.staffEmail = this.rnsQuotation.user.email;
        this.rnsVendorRemarkPost.fromEmail = this.rnsQuotation.user.email;
        this.rnsVendorRemarkPost.quotation.id = this.rnsQuotation.id;
        this.rnsVendorRemarkPost.remarkText = message;
        // console.log(this.rnsVendorRemark);
        this.rnsVendorRemarkSocketService.sendActivity(
            this.rnsVendorRemarkPost,
            this.rnsQuotation.id,
            this.rnsVendorRemarkPost.vendorEmail
        );
        // this.rnsVendorRemarkPost.remarkText = '';
        // this.subscribeToSaveRemarkResponse(
        //     this.rnsVendorRemarkService.update(this.rnsVendorRemark));
    }

    private subscribeToSaveRemarkResponse(result: Observable<RnsVendorRemark>) {
        result.subscribe((res: RnsVendorRemark) => this.onSaveRemarkSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveRemarkSuccess(result: RnsVendorRemark) {
        this.eventManager.broadcast({ name: 'rnsVendorRemarkListModification', content: 'OK' });
        this.rnsVendorRemarks.push(result);
    }

    private onSaveError() {}

    previousState() {
        this.router.navigate(['home']);
        // window.history.back();
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
}
