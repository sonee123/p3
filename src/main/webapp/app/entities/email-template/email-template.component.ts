import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { EmailTemplate } from './email-template.model';
import { EmailTemplateService } from './email-template.service';
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
    selector: 'jhi-email-template',
    templateUrl: './email-template.component.html'
})
export class EmailTemplateComponent implements OnInit, OnDestroy {
    emailTemplates: EmailTemplate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private emailTemplateService: EmailTemplateService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.emailTemplateService.query().subscribe(
            (res: HttpResponse<EmailTemplate[]>) => {
                this.emailTemplates = res.body;
                this.afterViewInit();
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
        this.registerChangeInEmailTemplates();
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
                                    columns: [0, 1, 2, 3, 4, 5, 6]
                                }
                            },
                            {
                                extend: 'csvHtml5',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4, 5, 6]
                                }
                            },
                            {
                                extend: 'print',
                                orientation: 'landscape',
                                pageSize: 'LEGAL',
                                exportOptions: {
                                    columns: [0, 1, 2, 3, 4, 5, 6]
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

    trackId(index: number, item: EmailTemplate) {
        return item.id;
    }
    registerChangeInEmailTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('emailTemplateListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
