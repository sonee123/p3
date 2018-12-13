import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';
import { RnsCrmRequestMaster, RnsCrmRequestMasterService, RnsCrmRequestMasterPopupService } from 'app/entities/rns-crm-request-master';
import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';

@Component({
    selector: 'jhi-rns-crm-request-master-search-dialog',
    templateUrl: './rns-crm-request-master-search.component.html'
})
export class RnsCrmRequestMasterSearchDialogComponent implements OnInit, OnDestroy {
    rnsCrmRequestMasters: RnsCrmRequestMaster[];
    rnsCrmRequestMaster: RnsCrmRequestMaster;
    currentAccount: any;
    eventSubscriber: Subscription;
    searchArray: RnsCrmRequestMaster[];
    searchModel: String;

    constructor(
        public activeModal: NgbActiveModal,
        private rnsCrmRequestMasterService: RnsCrmRequestMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsCrmRequestMasterService.query().subscribe(
            (res: HttpResponse<RnsCrmRequestMaster[]>) => {
                this.rnsCrmRequestMasters = res.body;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
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

    ngOnInit() {
        this.loadAll();
        this.searchModel = '';
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsCrmRequestMasters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsCrmRequestMaster) {
        return item.id;
    }

    registerChangeInRnsCrmRequestMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsCrmRequestMasterListModification', response => {
            this.loadAll();
        });
    }

    selectCrmRequest(crmRequestId) {
        console.log(crmRequestId);
        this.eventManager.broadcast({ name: 'selectedSearchCrmRequest', content: crmRequestId });
        this.activeModal.dismiss('cancel');
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-crm-request-master-popup-search',
    template: ''
})
export class RnsCrmRequestMasterPopupSearchComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsCrmRequestMasterPopupService: RnsCrmRequestMasterPopupService) {}

    ngOnInit() {
        this.rnsCrmRequestMasterPopupService.open(RnsCrmRequestMasterSearchDialogComponent as Component);
    }

    ngOnDestroy() {
        // this.routeSub.unsubscribe();
    }
}
