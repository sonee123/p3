import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsQuotationVendors } from './rns-quotation-vendors.model';
import { RnsQuotationVendorsPopupService } from './rns-quotation-vendors-popup.service';
import { RnsQuotationVendorsService } from './rns-quotation-vendors.service';
import { User, UserService } from 'app/core';
import { RnsQuotationVariant, RnsQuotationVariantService } from '../rns-quotation-variant';
import { RnsVendorMaster, RnsVendorMasterService } from '../rns-vendor-master';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';

@Component({
    selector: 'jhi-rns-quotation-vendors-dialog',
    templateUrl: './rns-quotation-vendors-dialog.component.html'
})
export class RnsQuotationVendorsDialogComponent implements OnInit {
    rnsQuotationVendors: RnsQuotationVendors;
    isSaving: boolean;

    users: User[];

    rnsquotationvariants: RnsQuotationVariant[];

    rnsvendormasters: RnsVendorMaster[];

    rnsquotations: RnsQuotation[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        private userService: UserService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private rnsVendorMasterService: RnsVendorMasterService,
        private rnsQuotationService: RnsQuotationService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.userService.query().subscribe(
            res => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsQuotationVariantService.query().subscribe(
            (res: HttpResponse<RnsQuotationVariant[]>) => {
                this.rnsquotationvariants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsVendorMasterService.query().subscribe(
            (res: HttpResponse<RnsVendorMaster[]>) => {
                this.rnsvendormasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rnsQuotationService.query().subscribe(
            (res: HttpResponse<RnsQuotation[]>) => {
                this.rnsquotations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsQuotationVendors.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsQuotationVendorsService.update(this.rnsQuotationVendors));
        } else {
            this.subscribeToSaveResponse(this.rnsQuotationVendorsService.create(this.rnsQuotationVendors));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsQuotationVendors>>) {
        result.subscribe((res: HttpResponse<RnsQuotationVendors>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsQuotationVendors>) {
        this.eventManager.broadcast({ name: 'rnsQuotationVendorsListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackRnsQuotationVariantById(index: number, item: RnsQuotationVariant) {
        return item.id;
    }

    trackRnsVendorMasterById(index: number, item: RnsVendorMaster) {
        return item.id;
    }

    trackRnsQuotationById(index: number, item: RnsQuotation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-quotation-vendors-popup',
    template: ''
})
export class RnsQuotationVendorsPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationVendorsPopupService: RnsQuotationVendorsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsQuotationVendorsPopupService.open(RnsQuotationVendorsDialogComponent as Component, params['id']);
            } else {
                this.rnsQuotationVendorsPopupService.open(RnsQuotationVendorsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
