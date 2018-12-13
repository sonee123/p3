import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { DatePipe } from '@angular/common';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';
import { LoginModalService, Principal } from 'app/core';
import * as moment from 'moment';

@Component({
    selector: 'jhi-rnsmainview-component',
    templateUrl: './rnsmainview.component.html'
})
export class RnsmainviewComponent implements OnInit {
    account: Account;
    rnsCatgMasters: RnsCatgMaster[];
    rnsRefrDetails: RnsRefrDetails[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;
    variants: RnsQuotationVariant[];
    vendors: RnsQuotationVendors[];
    article: Object;
    routeSub: any;
    targetPcd: Date;
    saveQuotationText: String;
    variantDisplay: String;
    subcodeInput: any;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private route: ActivatedRoute,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils
    ) {}

    loadAll() {
        this.rnsCatgMasterService.query().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnsCatgMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsRefrDetailsService.query().subscribe(
            (res: HttpResponse<RnsRefrDetails[]>) => {
                this.rnsRefrDetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        console.log(this.account);
        this.checkQuotationDetails();
        this.loadAll();
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.variants = [{ title: 'Variant 1', vendors: [], quotation: this.rnsQuotation }];
        this.subcodeInput = {
            input1: { display: true, data: [] },
            input2: { display: true, data: [] },
            input3: { display: true, data: [] },
            input4: { display: true, data: [] },
            input5: { display: true, data: [] }
        };
        this.saveQuotationText = 'Save Quotation';

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
                this.rnsQuotationService.find(id).subscribe(rnsQuotationdata => {
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
                });

                this.rnsQuotationVariantService.getByQuotationId(id).subscribe(
                    (res: HttpResponse<RnsQuotationVariant[]>) => {
                        this.variants = res.body;
                        this.displayVariant(res.body[0].title);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );

                this.rnsQuotationVendorsService.getByQuotationId(id).subscribe(
                    (res: HttpResponse<RnsQuotationVendors[]>) => {
                        this.vendors = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            } else {
                console.log('No id FOund..........');
            }
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
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
            this.targetPcd = new Date(this.rnsQuotation.targetPcd);
            if (this.rnsQuotation.targetPcd) {
                this.rnsQuotation.targetPcd = {
                    year: this.targetPcd.getFullYear(),
                    month: this.targetPcd.getMonth() + 1,
                    day: this.targetPcd.getDate()
                };
            }
            this.rnsQuotation.date = this.datePipe.transform(this.rnsQuotation.date, 'yyyy-MM-ddTHH:mm:ss');

            // console.log('RNS CRM Request is selected, change in search selected', this.rnsQuotation,);
        });
    }

    registerChangeInSearchVendor() {
        this.eventManager.subscribe('selectedSearchvendor', message => {
            let vendorExistCheck = false;
            this.variants.forEach(variant => {
                this.vendors.forEach(vendor => {
                    if (variant.title === this.variantDisplay) {
                        if (message.content.id === vendor.vendor.id) {
                            vendorExistCheck = true;
                            console.log('vendor True');
                        }
                    }
                });
            });

            if (!vendorExistCheck) {
                this.vendors.push({ vendor: message.content });
                this.vendors.forEach(vendor => {
                    this.variants.forEach(variant => {
                        if (message.content.id === vendor.vendor.id) {
                            if (variant.title === this.variantDisplay) {
                                vendor.vendorQuotation = this.rnsQuotation;
                                vendor.variant = variant;
                            }
                        }
                    });
                });
                console.log('RNS Vendor is selected, change in search selected on Quotation Page----', this.vendors);
            }
        });

        this.eventManager.subscribe('selectedSearchRemoveVendor', vendorId => {
            this.variants.forEach(variant => {
                variant.vendors.forEach((value, key) => {
                    console.log('To Be deleted', value, value.id, vendorId, parseInt(value.id, 10) === parseInt(vendorId.content, 10));
                    if (parseInt(value.id, 10) === parseInt(vendorId.content, 10)) {
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
        console.log(variantName, this.variants);
        this.variantDisplay = variantName;
    }

    createNewVariant() {
        const variantName = 'Variant ' + +(this.variants.length + 1);
        this.variants.push({ title: variantName, quotation: this.rnsQuotation });
        this.displayVariant(variantName);
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

    saveQuotation() {
        console.log(this.rnsQuotation);
        this.saveQuotationText = 'Saving ...';
        this.subscribeToSaveResponse(this.rnsQuotationService.update(this.rnsQuotation));
    }

    private updateVariants(rnsQuotation) {
        this.variants.forEach(variant => {
            console.log('Converted Item, ', variant);
            variant.quotation = rnsQuotation;
            this.subscribeToSaveVariantResponse(this.rnsQuotationVariantService.update(variant));
        });
    }

    private updateVendors(rnsQuotation) {
        this.vendors.forEach(vendor => {
            console.log('Converted vendor Item, ', vendor);
            vendor.vendorQuotation = rnsQuotation;
            this.subscribeToSaveVendorResponse(this.rnsQuotationVendorsService.update(vendor));
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotation>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotation>) => this.quotationSaved(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private subscribeToSaveVariantResponse(result: Observable<HttpResponse<RnsQuotationVariant>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVariant>) => this.quotationVariantSavedVariant(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private subscribeToSaveVendorResponse(result: Observable<HttpResponse<RnsQuotationVendors>>) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationVendors>) => this.quotationvendorsSaved(res),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private quotationSaved(result: HttpResponse<RnsQuotation>) {
        const copy = this.convert(this.rnsQuotation);
        if (this.variants.length > 0) {
            this.updateVariants(copy);
            this.saveQuotationText = 'Saving Variants...';
        } else {
            this.saveQuotationText = 'Save Quotation';
        }
    }

    private quotationVariantSavedVariant(result: HttpResponse<RnsQuotationVariant>) {
        const copy = this.convert(this.rnsQuotation);
        if (this.vendors.length > 0) {
            this.updateVendors(copy);
            this.saveQuotationText = 'Save Vendors ...';
        } else {
            this.saveQuotationText = 'Save Quotation';
        }
    }

    private quotationvendorsSaved(result: HttpResponse<RnsQuotationVendors>) {
        this.saveQuotationText = 'Save Quotation';
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private generateQuoation() {
        console.log('Generating Quotation ...');
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

        copy.validity = rnsQuotation.validity != null ? moment(rnsQuotation.validity) : null;
        copy.createdOn = rnsQuotation.createdOn != null ? moment(rnsQuotation.createdOn) : null;
        copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotation.targetPcd);
        copy.date = rnsQuotation.date != null ? moment(rnsQuotation.date) : null;
        return copy;
    }
}
