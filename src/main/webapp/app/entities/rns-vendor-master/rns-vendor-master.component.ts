import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsVendorMaster } from './rns-vendor-master.model';
import { RnsVendorMasterService } from './rns-vendor-master.service';
import { User, UserService } from 'app/core';
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
    selector: 'jhi-rns-vendor-master',
    templateUrl: './rns-vendor-master.component.html'
})
export class RnsVendorMasterComponent implements OnInit, OnDestroy {
    rnsVendorMasters: RnsVendorMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;
    users: User[];

    constructor(
        private rnsVendorMasterService: RnsVendorMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private userService: UserService,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsVendorMasterService.query().subscribe(
            (res: HttpResponse<RnsVendorMaster[]>) => {
                this.rnsVendorMasters = res.body;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.userService.query().subscribe(
            res => {
                this.users = res.body;
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
        this.registerChangeInRnsVendorMasters();
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
                                    columns: [0, 1, 2, 3, 4]
                                }
                            },
                            {
                                extend: 'csvHtml5',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4]
                                }
                            },
                            {
                                extend: 'print',
                                orientation: 'landscape',
                                pageSize: 'LEGAL',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4]
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

    trackId(index: number, item: RnsVendorMaster) {
        return item.id;
    }
    registerChangeInRnsVendorMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorMasterListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
