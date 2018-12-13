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
import { User, UserService } from 'app/core';
import { Principal } from 'app/core';
import { RnsVendorFavMaster, RnsVendorFavMasterService } from '../rns-vendor-fav-master';
import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-rns-vendor-master-search-select',
    templateUrl: './rns-vendor-master-search-select.component.html'
})
export class RnsVendorMasterSearchSelectComponent implements OnInit, OnDestroy {
    rnsVendorMasters: User[];
    currentAccount: any;
    eventSubscriber: Subscription;
    searchArray: User[];
    vendors: any;
    searchModel: string;
    users: User[];
    isSaving: boolean;
    isWait: boolean;

    rnsVendorFavMaster: RnsVendorFavMaster;

    constructor(
        public activeModal: NgbActiveModal,
        private rnsVendorMasterService: RnsVendorMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private userService: UserService,
        private rnsVendorFavMasterService: RnsVendorFavMasterService,
        private notifier: NotifierService
    ) {}

    loadAll() {
        // this.rnsVendorMasterService.query().subscribe(
        //    (res: ResponseWrapper) => {
        //        this.rnsVendorMasters = res.json;
        //        this.searchArray = JSON.parse(JSON.stringify(this.rnsVendorMasters));
        //   },
        //   (res: HttpErrorResponse) => this.onError(res.message)
        // );

        this.userService.queryvendor().subscribe(
            res => {
                this.rnsVendorMasters = res.body;
                this.searchArray = JSON.parse(JSON.stringify(this.rnsVendorMasters));
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.favourite();
        this.isWait = false;
        this.searchModel = '';
        this.rnsVendorFavMaster = new RnsVendorFavMaster();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsVendorMasters();
        this.vendors = [];
    }

    favourite() {
        this.isWait = true;
        this.userService.queryvendorfav().subscribe(
            res => {
                if ((<any>$('#exampleFixed')).DataTable()) {
                    (<any>$('#exampleFixed')).DataTable().destroy();
                }
                this.rnsVendorMasters = res.body;
                this.searchArray = JSON.parse(JSON.stringify(this.rnsVendorMasters));
                this.afterViewInit();
                this.isWait = false;
            },
            (res: HttpErrorResponse) => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: true,
                bAutoWidth: false,
                bLengthChange: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [0, 'asc']
            });
        });
    }

    search() {
        this.isWait = true;
        this.userService.queryvendor(this.searchModel).subscribe(
            res => {
                (<any>$('#exampleFixed')).DataTable().destroy();
                this.rnsVendorMasters = res.body;
                this.searchArray = JSON.parse(JSON.stringify(this.rnsVendorMasters));
                this.afterViewInit();
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
        this.eventManager.broadcast({ name: 'selectedSearchvendor', content: vendorDetails });
        this.notifier.notify('success', 'Added! ' + vendorDetails.firstName + ' ' + vendorDetails.lastName);
    }

    addFavourite(rnsVendorMaster) {
        if (rnsVendorMaster.favourite || document.getElementById('favSpanId' + rnsVendorMaster.login).style.color === 'rgb(255, 215, 0)') {
            if (confirm('Do you want to remove in favourite list?')) {
                document.getElementById('favSpanId' + rnsVendorMaster.login).style.color = '#777777';
                this.userService.deletevendor(rnsVendorMaster.login).subscribe(
                    res => {
                        this.notifier.notify('success', 'Vendor deleted in favourite list');
                    },
                    (res: HttpErrorResponse) => {
                        this.onError(res.message);
                    }
                );
            }
        } else {
            document.getElementById('favSpanId' + rnsVendorMaster.login).style.color = '#FFD700';
            this.rnsVendorFavMaster.vendorCode = rnsVendorMaster.login;
            this.rnsVendorFavMaster.createdDate = new Date();
            this.rnsVendorFavMaster.createdBy = this.currentAccount.login;
            this.subscribeToSaveResponse(this.rnsVendorFavMasterService.create(this.rnsVendorFavMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsVendorFavMaster>>) {
        result.subscribe(
            (res: HttpResponse<RnsVendorFavMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<RnsVendorFavMaster>) {
        this.eventManager.broadcast({ name: 'rnsVendorFavMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.notifier.notify('success', 'Added in favourite');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    trackId(index: number, item: RnsVendorMaster) {
        return item.id;
    }

    registerChangeInRnsVendorMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
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
    selector: 'jhi-rns-vendor-master-popup',
    template: ''
})
export class RnsVendorMasterSearchSelectPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorMasterPopupService: RnsVendorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsVendorMasterPopupService.open(RnsVendorMasterSearchSelectComponent as Component, params['id']);
            } else {
                this.rnsVendorMasterPopupService.open(RnsVendorMasterSearchSelectComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
