import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationRemarkDetailsComponent } from './rns-quotation-remark-details.component';
import { RnsQuotationRemarkDetailsDetailComponent } from './rns-quotation-remark-details-detail.component';
import { RnsQuotationRemarkDetailsPopupComponent } from './rns-quotation-remark-details-dialog.component';
import { RnsQuotationRemarkDetailsDeletePopupComponent } from './rns-quotation-remark-details-delete-dialog.component';
import { RnsQuotationRemarkDetailsCallRemarkPopupComponent } from './rns-quotation-remark-details-call-remark.component';

export const rnsQuotationRemarkDetailsRoute: Routes = [
    {
        path: 'rns-quotation-remark-details',
        component: RnsQuotationRemarkDetailsComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotationRemarkDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-remark-details/:id',
        component: RnsQuotationRemarkDetailsDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotationRemarkDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationRemarkDetailsPopupRoute: Routes = [
    {
        path: 'rns-quotation-remark-details-new',
        component: RnsQuotationRemarkDetailsPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotationRemarkDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-remark-details/:id/edit',
        component: RnsQuotationRemarkDetailsPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotationRemarkDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-remark-details/:id/delete',
        component: RnsQuotationRemarkDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotationRemarkDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-remark-details-call-quote/:id/:type',
        component: RnsQuotationRemarkDetailsCallRemarkPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotationRemarkDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
