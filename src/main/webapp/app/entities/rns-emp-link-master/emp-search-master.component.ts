import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { IUser, Principal, UserService } from 'app/core';
import { RnsVendorMaster, RnsVendorMasterPopupService } from 'app/entities/rns-vendor-master';

@Component({
    selector: 'jhi-emp-search-master',
    templateUrl: './emp-search-master.component.html'
})
export class EmployeeSearchMasterComponent implements OnInit, OnDestroy {
    users: IUser[];
    searchModel: string;
    currentAccount: any;
    eventSubscriber: Subscription;
    isSaving: boolean;
    isWait: boolean;
    rnsVendorMaster: RnsVendorMaster;
    constructor(
        public activeModal: NgbActiveModal,
        public userService: UserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {}

    ngOnInit() {
        this.isWait = false;
        this.searchModel = '';
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsVendorMasters();
    }

    search() {
        this.isWait = true;
        this.userService.queryUser(this.searchModel).subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
                this.isWait = false;
            },
            (res: HttpErrorResponse) => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    selectVendor(user) {
        user.type = this.rnsVendorMaster.vendorCode;
        this.eventManager.broadcast({ name: 'selectedUserLinkCreation', content: user });
        this.activeModal.dismiss('cancel');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    registerChangeInRnsVendorMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorMasterListModification', response => {
            this.loadAll();
        });
    }

    removeVendor(vendorId) {
        this.eventManager.broadcast({ name: 'selectedSearchRemoveVendor', content: vendorId });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-emp-search-master-popup',
    template: ''
})
export class EmployeeSearchMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorMasterPopupService: RnsVendorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsVendorMasterPopupService.type(EmployeeSearchMasterComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
