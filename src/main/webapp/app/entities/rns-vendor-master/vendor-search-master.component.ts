import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { Subscription } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsVendorMaster } from './rns-vendor-master.model';
import { RnsVendorMasterPopupService } from './rns-vendor-master-popup.service';
import { RnsVendorMasterService } from './rns-vendor-master.service';
import { Principal } from 'app/core';

import { RnsVendorFavMaster } from '../rns-vendor-fav-master';

@Component({
    selector: 'jhi-vendor-search-master',
    templateUrl: './vendor-search-master.component.html'
})
export class VendorSearchMasterComponent implements OnInit, OnDestroy {
    rnsVendorMasters: RnsVendorMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;
    searchArray: RnsVendorMaster[];
    vendors: any;
    searchModel: string;
    isSaving: boolean;
    isWait: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private rnsVendorMasterService: RnsVendorMasterService,
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
        this.vendors = [];
    }

    search() {
        this.isWait = true;
        this.rnsVendorMasterService.queryvendor(this.searchModel).subscribe(
            (res: HttpResponse<RnsVendorMaster[]>) => {
                this.rnsVendorMasters = res.body;
                this.searchArray = JSON.parse(JSON.stringify(this.rnsVendorMasters));
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

    selectVendor(vendorDetails) {
        this.eventManager.broadcast({ name: 'selectedVendorUserCreation', content: vendorDetails });
        this.activeModal.dismiss('cancel');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    trackId(index: number, item: RnsVendorMaster) {
        return item.id;
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
    selector: 'jhi-vendor-search-master-popup',
    template: ''
})
export class VendorSearchMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorMasterPopupService: RnsVendorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsVendorMasterPopupService.open(VendorSearchMasterComponent as Component, params['id']);
            } else {
                this.rnsVendorMasterPopupService.open(VendorSearchMasterComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
