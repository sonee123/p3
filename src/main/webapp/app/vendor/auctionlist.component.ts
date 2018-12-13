import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { JhiDateUtils } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import { Auction, AuctionService } from 'app/entities/auction';
import { LoginModalService, Principal, User } from 'app/core';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { RnsRefrDetails, RnsRefrDetailsService } from 'app/entities/rns-refr-details';
import { RnsQuotation, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsCrmRequestMaster } from 'app/entities/rns-crm-request-master';
import { RnsQuotationVariant, RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { AuctionVrnt, AuctionVrntService } from 'app/entities/auction-vrnt';

@Component({
    selector: 'jhi-home',
    templateUrl: './auctionList.component.html'
})
export class AuctionListComponent implements OnInit {
    auction: Auction;
    account: Account;
    user: User;
    rnsCatgMasters: RnsCatgMaster[];
    rnsRefrDetails: RnsRefrDetails[];
    rnsQuotation: RnsQuotation;
    quotation: RnsQuotation;
    rnsQuotations: RnsQuotation[];
    rnsCrmRequestMasters: RnsCrmRequestMaster[];
    variants: RnsQuotationVariant[];
    variant: RnsQuotationVariant;
    auctionVrnts: AuctionVrnt[];
    auctionVrnt: AuctionVrnt;
    searchArray: RnsCrmRequestMaster[];
    modalRef: NgbModalRef;
    eventSubscriber: Subscription;
    generateQuotationText: {};
    publish: {};
    isSaving: boolean;
    lotNames: any;
    variantMsg: any;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private rnsQuotationService: RnsQuotationService,
        private rnsQuotationVariantService: RnsQuotationVariantService,
        private auctionVrntService: AuctionVrntService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private router: Router,
        private datePipe: DatePipe,
        private dateUtils: JhiDateUtils,
        private auctionService: AuctionService
    ) {}

    loadAll() {
        this.rnsQuotationService.queryAuctionVendor().subscribe(
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

    private onSaveError() {
        this.isSaving = false;
        console.log('Error while saving');
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
