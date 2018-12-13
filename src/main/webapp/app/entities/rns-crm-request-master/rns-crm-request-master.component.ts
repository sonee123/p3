import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsCrmRequestMaster } from './rns-crm-request-master.model';
import { RnsCrmRequestMasterService } from './rns-crm-request-master.service';
import { RnsPchMaster, RnsPchMasterService } from '../rns-pch-master';
import { RnsArticleMaster, RnsArticleMasterService } from '../rns-article-master';
import { RnsBuyerMaster, RnsBuyerMasterService } from '../rns-buyer-master';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import 'datatables.net-buttons';
import 'datatables.net-buttons/js/buttons.colVis.min';
import 'datatables.net-buttons/js/dataTables.buttons.min';
import 'datatables.net-buttons/js/buttons.flash.min';
import 'jszip/dist/jszip.min';
import 'pdfmake/build/pdfmake.min';
import 'pdfmake/build/vfs_fonts';
import 'datatables.net-buttons/js/buttons.html5.min';
import 'datatables.net-buttons/js/buttons.print.min';

@Component({
    selector: 'jhi-rns-crm-request-master',
    templateUrl: './rns-crm-request-master.component.html'
})
export class RnsCrmRequestMasterComponent implements OnInit, OnDestroy {
    rnsCrmRequestMasters: RnsCrmRequestMaster[];
    rnsCrmRequestMaster: RnsCrmRequestMaster;
    rnsPchMasters: RnsPchMaster[];
    rnsArticleMasters: RnsArticleMaster[];
    rnsBuyerMasters: RnsBuyerMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsCrmRequestMasterService: RnsCrmRequestMasterService,
        private rnsPchMasterService: RnsPchMasterService,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private rnsArticleMasterService: RnsArticleMasterService,
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

        this.rnsPchMasterService.query().subscribe(
            (res: HttpResponse<RnsPchMaster[]>) => {
                this.rnsPchMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsArticleMasterService.query().subscribe(
            (res: HttpResponse<RnsArticleMaster[]>) => {
                this.rnsArticleMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsBuyerMasterService.query().subscribe(
            (res: HttpResponse<RnsBuyerMaster[]>) => {
                this.rnsBuyerMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        const pdfMake = require('pdfmake/build/pdfmake.js');
        const pdfFonts = require('pdfmake/build/vfs_fonts.js');
        pdfMake.vfs = pdfFonts.pdfMake.vfs;
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsCrmRequestMasters();
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: true,
                bAutoWidth: true,
                bLengthChange: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [0, 'desc'],
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'collection',
                        text: 'Export &#8964;',
                        buttons: [
                            {
                                extend: 'pdfHtml5',
                                orientation: 'landscape',
                                pageSize: 'LEGAL',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
                                }
                            },
                            {
                                extend: 'csvHtml5',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
                                }
                            },
                            {
                                extend: 'print',
                                orientation: 'landscape',
                                pageSize: 'LEGAL',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
                                }
                            }
                        ]
                    }
                ]
            });
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsCrmRequestMaster) {
        return item.id;
    }
    registerChangeInRnsCrmRequestMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsCrmRequestMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
