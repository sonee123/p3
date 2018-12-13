import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { RnsQuotationRemarkDetailsPopupService } from './rns-quotation-remark-details-popup.service';
import { RnsQuotationRemarkDetailsService } from './rns-quotation-remark-details.service';
import { Account, Principal, User, UserService } from 'app/core';
import { RnsForwardTypeMaster } from 'app/entities/rns-forward-type-master';
import { RnsEmpLinkMaster, RnsEmpLinkMasterService } from 'app/entities/rns-emp-link-master';
import { RnsQuotation } from 'app/entities/rns-quotation';
import { NotifierService } from 'angular-notifier';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';

@Component({
    selector: 'jhi-rns-quotation-remark-details-call-remark',
    templateUrl: './rns-quotation-remark-details-call-remark.component.html',
    styleUrls: ['rns-chat.css']
})
export class RnsQuotationRemarkDetailsCallRemarkComponent implements OnInit {
    account: Account;
    rnsQuotationRemarkDetails: RnsQuotationRemarkDetails;
    flowType: any;
    isSaving: boolean;
    message: string;
    authDateDp: any;
    user: User;
    isValid: boolean;
    isValidWorkflow: boolean;
    rnsForwardTypeMasters: RnsForwardTypeMaster[];
    rnsEmpLinkMasters: RnsEmpLinkMaster[];
    private subscription: Subscription;
    rnsQuotation: RnsQuotation;
    approvalMessage: string;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationRemarkDetailsService: RnsQuotationRemarkDetailsService,
        private rnsEmpLinkMasterService: RnsEmpLinkMasterService,
        private userService: UserService,
        private route: ActivatedRoute,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private comparentchildservice: ComParentChildService,
        private notifier: NotifierService
    ) {}

    public onChangeAuthType(val): void {
        // event will give you full breif of action
        const newVal = val;
        this.rnsEmpLinkMasterService.emplist(newVal).subscribe(
            (res: HttpResponse<RnsEmpLinkMaster[]>) => {
                this.rnsEmpLinkMasters = res.body;
                if (val === 'R' || val === 'C') {
                    this.rnsQuotationRemarkDetails.forwardCode = this.rnsEmpLinkMasters[0].forwardEmpCode;
                } else {
                    this.rnsQuotationRemarkDetails.forwardCode = null;
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        console.log(newVal);
    }

    onClickRecall(val) {
        this.isSaving = true;
        const newVal = val;
        this.subscribeToSaveResponse(this.rnsQuotationRemarkDetailsService.delete(newVal));
        console.log(newVal);
    }

    isValidForm() {
        return this.isValid;
    }

    isValidWorkFlowForm() {
        return this.isValidWorkflow;
    }

    reOpenProcess() {
        if (confirm('Do you want to re-open approval process?')) {
            this.rnsQuotationRemarkDetails.id = 0;
            this.rnsQuotationRemarkDetails.empCode = this.rnsQuotation.user;
            this.rnsQuotationRemarkDetails.flowType = this.flowType;
            this.rnsQuotationRemarkDetails.quoteId = this.rnsQuotation.id;
            this.rnsQuotationRemarkDetails.rnsForwardTypeMaster = null;
            this.rnsQuotationRemarkDetails.approvalType = null;
            this.rnsQuotationRemarkDetails.authType = null;
            this.subscribeToSaveResponse(this.rnsQuotationRemarkDetailsService.reopen(this.rnsQuotationRemarkDetails), 'Y');
        }
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        if (this.rnsQuotationRemarkDetails.allowEntry) {
            this.isValid = false;
        } else {
            this.isValid = true;
        }
        if (this.rnsQuotationRemarkDetails.allowWorkFlow === 'C' || this.rnsQuotationRemarkDetails.allowWorkFlow === 'R') {
            this.isValidWorkflow = true;
        } else {
            this.isValidWorkflow = false;
        }
        this.isSaving = false;
        this.rnsEmpLinkMasterService.forward().subscribe(
            (res: HttpResponse<RnsForwardTypeMaster[]>) => {
                this.rnsForwardTypeMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    load(login) {
        this.userService.findpost(login).subscribe(user => {
            this.user = user.body;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.rnsQuotationRemarkDetails.flowType = this.flowType;
        console.log('130', this.rnsQuotationRemarkDetails);
        this.subscribeToSaveResponse(this.rnsQuotationRemarkDetailsService.update(this.rnsQuotationRemarkDetails));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationRemarkDetails>>, reOpen?: any) {
        result.subscribe(
            (res: HttpResponse<RnsQuotationRemarkDetails>) => this.onSaveSuccess(res, reOpen),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationRemarkDetails>, reOpen?: any) {
        if (reOpen !== null && reOpen === 'Y') {
        } else {
            this.comparentchildservice.publish('call-variant-parent');
        }
        this.isSaving = false;
        this.message = 'Save successfully';
        this.notifier.notify('success', this.message);
        this.refresh();
    }

    private onSaveError() {
        this.isSaving = false;
        this.message = 'not save';
        this.notifier.notify('error', this.message);
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    private refresh() {
        this.rnsQuotationRemarkDetailsService
            .popup(this.rnsQuotationRemarkDetails.quoteId, this.flowType)
            .subscribe(rnsQuotationRemarkDetails => {
                this.rnsQuotationRemarkDetails = rnsQuotationRemarkDetails.body;
                if (this.rnsQuotationRemarkDetails.authDate) {
                    this.rnsQuotationRemarkDetails.authDate = {
                        year: this.rnsQuotationRemarkDetails.authDate.getFullYear(),
                        month: this.rnsQuotationRemarkDetails.authDate.getMonth() + 1,
                        day: this.rnsQuotationRemarkDetails.authDate.getDate()
                    };
                }

                if (this.rnsQuotationRemarkDetails.allowEntry) {
                    this.isValid = false;
                } else {
                    this.isValid = true;
                }
                if (this.rnsQuotationRemarkDetails.allowWorkFlow === 'C' || this.rnsQuotationRemarkDetails.allowWorkFlow === 'R') {
                    this.isValidWorkflow = true;
                } else {
                    this.isValidWorkflow = false;
                }
                this.isSaving = false;
            });
    }
}

@Component({
    selector: 'jhi-rns-quotation-remark-details-popup',
    template: ''
})
export class RnsQuotationRemarkDetailsCallRemarkPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationRemarkDetailsPopupService: RnsQuotationRemarkDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationRemarkDetailsPopupService.popup(
                    RnsQuotationRemarkDetailsCallRemarkComponent as Component,
                    params['id'],
                    params['type']
                );
            } else {
                this.rnsQuotationRemarkDetailsPopupService.popup(RnsQuotationRemarkDetailsCallRemarkComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
