import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { P3SharedModule } from '../shared';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { QuotationDetailReportComponent } from './quotation-detail-report.component';
import { P3REPORT_ROUTE } from './';

@NgModule({
    imports: [P3SharedModule, RouterModule.forChild(P3REPORT_ROUTE), BrowserModule, ReactiveFormsModule, FormsModule],
    declarations: [QuotationDetailReportComponent],
    entryComponents: [QuotationDetailReportComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class P3ReportModule {}
