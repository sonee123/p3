import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsEmpLinkMaster } from './rns-emp-link-master.model';
import { RnsEmpLinkMasterPopupService } from './rns-emp-link-master-popup.service';
import { RnsEmpLinkMasterService } from './rns-emp-link-master.service';
import { RnsForwardTypeMaster, RnsForwardTypeMasterService } from '../rns-forward-type-master';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-rns-emp-link-master-dialog',
    templateUrl: './rns-emp-link-master-dialog.component.html',
    styleUrls: ['link-emp.css']
})
export class RnsEmpLinkMasterDialogComponent implements OnInit {
    rnsEmpLinkMaster: RnsEmpLinkMaster;
    isSaving: boolean;

    rnsForwardTypeMasters: RnsForwardTypeMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsEmpLinkMasterService: RnsEmpLinkMasterService,
        private rnsForwardTypeMasterService: RnsForwardTypeMasterService,
        private eventManager: JhiEventManager,
        private notifier: NotifierService
    ) {}

    ngOnInit() {
        this.registerChangeInSearcUser();
        this.isSaving = false;
        if (this.rnsEmpLinkMaster.id !== undefined) {
        } else {
            this.rnsEmpLinkMaster.mailApplicable = true;
        }
        this.rnsForwardTypeMasterService.query().subscribe(
            (res: HttpResponse<RnsForwardTypeMaster[]>) => {
                this.rnsForwardTypeMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    registerChangeInSearcUser() {
        this.eventManager.subscribe('selectedUserLinkCreation', message => {
            const userDetails = message.content;
            if (userDetails.type === 'empCode') {
                this.rnsEmpLinkMaster.empCode = userDetails;
            } else {
                this.rnsEmpLinkMaster.forwardEmpCode = userDetails;
            }
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    checktype() {
        if (this.rnsEmpLinkMaster.forwardEmpType === 'C' || this.rnsEmpLinkMaster.forwardEmpType === 'R') {
            if (this.rnsEmpLinkMaster.empCode !== null) {
                this.rnsEmpLinkMaster.forwardEmpCode = this.rnsEmpLinkMaster.empCode;
            }
        } else {
            if (
                this.rnsEmpLinkMaster.empCode !== null &&
                this.rnsEmpLinkMaster.forwardEmpCode !== null &&
                this.rnsEmpLinkMaster.empCode.login === this.rnsEmpLinkMaster.forwardEmpCode.login
            ) {
                this.rnsEmpLinkMaster.forwardEmpCode = null;
            }
        }
    }

    save() {
        this.isSaving = true;
        if (this.rnsEmpLinkMaster.forwardEmpType === 'C' || this.rnsEmpLinkMaster.forwardEmpType === 'R') {
            if (this.rnsEmpLinkMaster.empCode.login !== this.rnsEmpLinkMaster.forwardEmpCode.login) {
                this.isSaving = false;
                this.notifier.notify('error', 'For Approval/Rejection Type Employee and To Employee must be same');
                return false;
            }
        } else {
            if (this.rnsEmpLinkMaster.empCode.login === this.rnsEmpLinkMaster.forwardEmpCode.login) {
                this.isSaving = false;
                this.notifier.notify('error', 'For Forward/Verified Type Employee and To Employee must not be same');
                return false;
            }
        }
        const mailApplicable = <HTMLInputElement>document.getElementById('mailApplicable');
        if (mailApplicable.checked) {
            this.rnsEmpLinkMaster.mailApplicable = true;
        } else {
            this.rnsEmpLinkMaster.mailApplicable = false;
        }
        if (this.rnsEmpLinkMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsEmpLinkMasterService.update(this.rnsEmpLinkMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsEmpLinkMasterService.create(this.rnsEmpLinkMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsEmpLinkMaster>>) {
        result.subscribe((res: HttpResponse<RnsEmpLinkMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsEmpLinkMaster>) {
        this.eventManager.broadcast({ name: 'rnsEmpLinkMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-emp-link-master-popup',
    template: ''
})
export class RnsEmpLinkMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsEmpLinkMasterPopupService: RnsEmpLinkMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsEmpLinkMasterPopupService.open(RnsEmpLinkMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsEmpLinkMasterPopupService.open(RnsEmpLinkMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
