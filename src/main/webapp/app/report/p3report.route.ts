import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../core';
import { QuotationDetailReportComponent } from './quotation-detail-report.component';

export const P3REPORT_ROUTE: Routes = [
    {
        path: 'detail-report',
        component: QuotationDetailReportComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'Project Plan Detail'
        },
        canActivate: [UserRouteAccessService]
    }
];
