import { Component, OnInit } from '@angular/core';
import { RnsQuotationSearch, RnsQuotationService } from 'app/entities/rns-quotation';
import { RnsCatgMaster, RnsCatgMasterService } from 'app/entities/rns-catg-master';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { RnsTypeMaster, RnsTypeMasterService } from 'app/entities/rns-type-master';
import { JhiAlertService } from 'ng-jhipster';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from 'app/entities/rns-source-team-master';
import { RnsPchMaster, RnsPchMasterService } from 'app/entities/rns-pch-master';
import { ActivatedRoute } from '@angular/router';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'jhi-quotation-detail',
    templateUrl: './quotation-detail-report.component.html'
})
export class QuotationDetailReportComponent implements OnInit {
    rnsQuotationSearch: RnsQuotationSearch;
    isWait: boolean;
    rnsCatgMasters: RnsCatgMaster[];
    rnsTypeMasters: RnsTypeMaster[];
    rnsSourceTeamMasters: RnsSourceTeamMaster[];
    rnsPchMasters: RnsPchMaster[];
    rnsTypeMastersResponseData: RnsTypeMaster[];
    routeSub: any;
    tempFromDate: any;
    tempToDate: any;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private jhiAlertService: JhiAlertService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private rnsPchMasterService: RnsPchMasterService,
        private rnsQuotationService: RnsQuotationService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.rnsQuotationSearch = new RnsQuotationSearch();
        this.rnsQuotationSearch.type = 'XLSX';
        this.isWait = false;
        this.rnsCatgMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnsCatgMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsSourceTeamMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsSourceTeamMaster[]>) => {
                this.rnsSourceTeamMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsPchMasterService.query().subscribe(
            (res: HttpResponse<RnsPchMaster[]>) => {
                this.rnsPchMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        const now = new Date();
        const since = new Date();
        since.setDate(since.getDate() - 7);
        this.rnsQuotationSearch.dateFrom = { year: since.getFullYear(), month: since.getMonth() + 1, day: since.getDate() };
        this.rnsQuotationSearch.dateTo = { year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate() };
    }

    download(): any {
        this.isWait = true;
        this.tempFromDate = this.rnsQuotationSearch.dateFrom;
        this.tempToDate = this.rnsQuotationSearch.dateTo;
        this.rnsQuotationService.downloadreport(this.rnsQuotationSearch).subscribe(
            res => {
                this.isWait = false;
                FileSaver.saveAs(res, `PrijectPlanDetail.${this.rnsQuotationSearch.type}`);
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
        this.rnsQuotationSearch.dateFrom = this.tempFromDate;
        this.rnsQuotationSearch.dateTo = this.tempToDate;
    }

    changeCategory() {
        if (this.rnsQuotationSearch.catgCode != null) {
            const code = this.rnsQuotationSearch.catgCode.id;
            this.rnsTypeMasterService.query().subscribe(
                (res: HttpResponse<RnsTypeMaster[]>) => {
                    this.rnsTypeMastersResponseData = res.body;
                    this.rnsTypeMasters = [];
                    this.routeSub = this.route.params.subscribe(params => {
                        for (const item in this.rnsTypeMastersResponseData) {
                            if (this.rnsTypeMastersResponseData[item].rnsCatgCode.id === code) {
                                this.rnsTypeMasters.push(this.rnsTypeMastersResponseData[item]);
                            }
                        }
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    public clearForm() {
        const now = new Date();
        const since = new Date();
        since.setDate(since.getDate() - 7);
        this.rnsQuotationSearch.dateFrom = { year: since.getFullYear(), month: since.getMonth() + 1, day: since.getDate() };
        this.rnsQuotationSearch.dateTo = { year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate() };
        this.rnsQuotationSearch.eventType = null;
        this.rnsQuotationSearch.catgCode = null;
        this.rnsQuotationSearch.projectType = null;
        this.rnsQuotationSearch.sourceTeam = null;
        this.rnsQuotationSearch.pchCode = null;
    }

    public dateFrom() {}

    public dateTo() {}
}
