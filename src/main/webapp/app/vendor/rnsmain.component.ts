import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { JhiDateUtils } from 'ng-jhipster';
import { Account, LoginModalService, Principal } from '../core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsCrmRequestMaster } from 'app/entities/rns-crm-request-master';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';

@Component({
    selector: 'jhi-home',
    templateUrl: './rnsmain.component.html'
})
export class RnsmainComponent implements OnInit {
    account: Account;
    rnsCatgMasters: RnsCatgMaster[];
    rnsRefrDetails: RnsRefrDetails[];
    rnsQuotation: RnsQuotation;
    quotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    rnsCrmRequestMasters: RnsCrmRequestMaster[];
    variants: RnsQuotationVariant[];
    searchArray: RnsCrmRequestMaster[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    generateQuotationText: {};
    publish: {};
    isSaving: boolean;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private router: Router,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils
    ) {}

    loadAll() {
        this.rnsQuotationService.queryVendor().subscribe(
            (res: HttpResponse<RnsQuotation[]>) => {
                this.rnsQuotations = res.body;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                responsive: false,
                paging: true,
                bInfo: false,
                bFilter: true,
                bAutoWidth: false,
                bLengthChange: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [0, 'desc']
            });
            $('#exampleFixed').removeClass('collapsed');
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
