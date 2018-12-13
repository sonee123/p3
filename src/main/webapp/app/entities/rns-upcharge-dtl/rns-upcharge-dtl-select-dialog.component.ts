import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { RnsUpchargeDtlPopupService } from './rns-upcharge-dtl-popup.service';
import { RnsUpchargeDtlService } from './rns-upcharge-dtl.service';
import { RnsUpchargeMaster, RnsUpchargeMasterService } from 'app/entities/rns-upcharge-master';
import { RnsQuotationVendors, RnsQuotationVendorsService } from 'app/entities/rns-quotation-vendors';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import { RnsQuotation } from 'app/entities/rns-quotation';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-rns-upcharge-select-dtl-dialog',
    templateUrl: './rns-upcharge-dtl-select-dialog.component.html'
})
export class RnsUpchargeDtlSelectDialogComponent implements OnInit {
    rnsUpchargeDtl: RnsUpchargeDtl;
    rnsUpchargeMasters: RnsUpchargeMaster[];
    rnsUpchargeDtls: RnsUpchargeDtl[];
    rnsQuotationVendors: RnsQuotationVendors;
    totalValue: number;
    isSaving: boolean;
    rnsQuotation: RnsQuotation;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsUpchargeDtlService: RnsUpchargeDtlService,
        private rnsUpchargeMasterService: RnsUpchargeMasterService,
        private rnsQuotaionVendorsService: RnsQuotationVendorsService,
        private eventManager: JhiEventManager,
        private comparentchildservice: ComParentChildService,
        private notifier: NotifierService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsUpchargeMasterService.query().subscribe((res: HttpResponse<RnsUpchargeMaster[]>) => {
            this.rnsUpchargeMasters = res.body;
        });

        let ctr = 0;

        this.rnsQuotation = this.rnsQuotationVendors.vendorQuotation;

        this.rnsUpchargeDtlService.findVendors(this.rnsQuotationVendors.id).subscribe((res: HttpResponse<RnsUpchargeDtl[]>) => {
            this.rnsUpchargeDtls = res.body;

            this.totalValue = 0;
            this.rnsUpchargeDtls.forEach(rnsUpchargeDtl => {
                ++ctr;
                if (rnsUpchargeDtl.rate != null) {
                    if (this.rnsQuotationVendors.bidRate) {
                        if (rnsUpchargeDtl.upchargeType === 'V') {
                            rnsUpchargeDtl.value = rnsUpchargeDtl.rate;
                        } else if (rnsUpchargeDtl.upchargeType === 'P') {
                            rnsUpchargeDtl.value = Number((this.rnsQuotationVendors.bidRate * rnsUpchargeDtl.rate / 100).toFixed(2));
                        }
                    } else if (this.rnsQuotationVendors.regularRate) {
                        if (rnsUpchargeDtl.upchargeType === 'V') {
                            rnsUpchargeDtl.value = rnsUpchargeDtl.rate;
                        } else if (rnsUpchargeDtl.upchargeType === 'P') {
                            rnsUpchargeDtl.value = Number((this.rnsQuotationVendors.regularRate * rnsUpchargeDtl.rate / 100).toFixed(2));
                        }
                    }
                }
                if (rnsUpchargeDtl.value != null) {
                    this.totalValue = Number(this.totalValue) + Number(rnsUpchargeDtl.value);
                }
            });
            this.totalValue = Number(this.totalValue.toFixed(2));

            if (ctr < 10) {
                for (let i = ctr; i < 10; i++) {
                    this.rnsUpchargeDtls.push(new RnsUpchargeDtl());
                }
            }
        });
    }

    addUpcharge() {
        this.rnsUpchargeDtls.push(new RnsUpchargeDtl());
    }

    calculateValue(rnsUpchargeDtl) {
        if (rnsUpchargeDtl.rate != null) {
            if (this.rnsQuotationVendors.bidRate) {
                if (rnsUpchargeDtl.upchargeType === 'V') {
                    rnsUpchargeDtl.value = rnsUpchargeDtl.rate;
                } else if (rnsUpchargeDtl.upchargeType === 'P') {
                    rnsUpchargeDtl.value = Number((this.rnsQuotationVendors.bidRate * rnsUpchargeDtl.rate / 100).toFixed(2));
                }
            } else if (this.rnsQuotationVendors.regularRate) {
                if (rnsUpchargeDtl.upchargeType === 'V') {
                    rnsUpchargeDtl.value = rnsUpchargeDtl.rate;
                } else if (rnsUpchargeDtl.upchargeType === 'P') {
                    rnsUpchargeDtl.value = Number((this.rnsQuotationVendors.regularRate * rnsUpchargeDtl.rate / 100).toFixed(2));
                }
            }
        }
        this.totalValue = 0;
        this.rnsUpchargeDtls.forEach(value => {
            if (value.value != null) {
                this.totalValue = Number(this.totalValue) + Number(value.value);
            }
        });
        this.totalValue = Number(this.totalValue.toFixed(2));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.rnsUpchargeDtls.forEach((value: RnsUpchargeDtl) => {
            value.vendorId = this.rnsQuotationVendors.id;
        });
        this.subscribeToSaveResponse(this.rnsUpchargeDtlService.createMulti(this.rnsUpchargeDtls));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsUpchargeDtl[]>>) {
        result.subscribe((res: HttpResponse<RnsUpchargeDtl[]>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsUpchargeDtl[]>) {
        this.eventManager.broadcast({ name: 'rnsUpchargeDtlListModification', content: 'OK' });
        this.eventManager.broadcast({ name: 'rnsQuotationVendorsListModification', content: 'OK' });
        this.isSaving = false;
        this.rnsUpchargeDtls = result.body;
        this.totalValue = 0;
        let ctr = 0;
        this.rnsUpchargeDtls.forEach(rnsUpchargeDtl => {
            ++ctr;
            if (rnsUpchargeDtl.rate != null) {
                if (this.rnsQuotationVendors.bidRate) {
                    if (rnsUpchargeDtl.upchargeType === 'V') {
                        rnsUpchargeDtl.value = rnsUpchargeDtl.rate;
                    } else if (rnsUpchargeDtl.upchargeType === 'P') {
                        rnsUpchargeDtl.value = Number((this.rnsQuotationVendors.bidRate * rnsUpchargeDtl.rate / 100).toFixed(2));
                    }
                } else if (this.rnsQuotationVendors.regularRate) {
                    if (rnsUpchargeDtl.upchargeType === 'V') {
                        rnsUpchargeDtl.value = rnsUpchargeDtl.rate;
                    } else if (rnsUpchargeDtl.upchargeType === 'P') {
                        rnsUpchargeDtl.value = Number((this.rnsQuotationVendors.regularRate * rnsUpchargeDtl.rate / 100).toFixed(2));
                    }
                }
            }
            if (rnsUpchargeDtl.value != null) {
                this.totalValue = Number(this.totalValue) + Number(rnsUpchargeDtl.value);
            }
        });
        this.totalValue = Number(this.totalValue.toFixed(2));

        if (ctr < 10) {
            for (let i = ctr; i < 10; i++) {
                this.rnsUpchargeDtls.push(new RnsUpchargeDtl());
            }
        }
        this.notifier.notify('success', 'Save successfully.');
        this.comparentchildservice.publish('call-parent');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-upcharge-select-dtl-popup',
    template: ''
})
export class RnsUpchargeDtlSelectPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUpchargeDtlPopupService: RnsUpchargeDtlPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsUpchargeDtlPopupService.selectvendor(RnsUpchargeDtlSelectDialogComponent as Component, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
