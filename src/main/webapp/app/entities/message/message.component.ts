import { Component, OnInit, OnDestroy } from '@angular/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Dashboard } from 'app/rnsmain/dashboard.model';
import { RnsQuotationService } from 'app/entities/rns-quotation';

import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';

@Component({
    selector: 'jhi-message',
    templateUrl: './message.component.html',
    styleUrls: ['./dashboard.css']
})
export class MessageComponent implements OnInit, OnDestroy {
    activeTab: string;
    myForm: FormGroup;
    dashBoard: Dashboard;

    constructor(
        private rnsQuotationService: RnsQuotationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private fb: FormBuilder
    ) {}

    public changeTab(tabId) {
        this.activeTab = tabId;
    }

    ngOnInit() {
        this.activeTab = 'message';
        const date = new Date();
        if (date.getMonth() + 1 < 10) {
            this.myForm = this.fb.group({
                messagefecha: { year: date.getFullYear(), month: '0' + (date.getMonth() + 1) }
            });
        } else {
            this.myForm = this.fb.group({
                messagefecha: { year: date.getFullYear(), month: date.getMonth() + 1 }
            });
        }
        this.loadAll();
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: false,
                bAutoWidth: true,
                bLengthChange: false,
                ordering: false
            });

            let tableTo = (<any>$('#exampleFixedTo')).DataTable();
            tableTo.destroy();

            tableTo = (<any>$('#exampleFixedTo')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: false,
                bAutoWidth: true,
                bLengthChange: false,
                ordering: false
            });
        });
    }

    loadAll() {
        const yearstr = Number(this.myForm.controls['messagefecha'].value['year']);
        const month = Number(this.myForm.controls['messagefecha'].value['month']);
        let monthstr = month + '';
        if (month < 10) {
            monthstr = '0' + month;
        }
        this.dashBoard = {
            id: null,
            monthYear: monthstr + '-' + yearstr,
            catgCode: null
        };
        this.rnsQuotationService.dashboardMessage(this.dashBoard).subscribe((dashboard: HttpResponse<Dashboard>) => {
            this.dashBoard = dashboard.body;
            this.afterViewInit();
        });
    }

    readFlagChange(message) {
        message.readflag = 'Y';
    }

    ngOnDestroy() {}

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
