import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationComponent } from './rns-quotation.component';
import { RnsQuotationDetailComponent } from './rns-quotation-detail.component';
import { RnsQuotationPopupComponent } from './rns-quotation-dialog.component';
import { RnsQuotationDeletePopupComponent } from './rns-quotation-delete-dialog.component';
import { RnsQuotationComparePopupComponent } from './rns-quotation-compare-dialog.component';

export const rnsQuotationRoute: Routes = [
    {
        path: 'rns-quotation',
        component: RnsQuotationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation/:id',
        component: RnsQuotationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationPopupRoute: Routes = [
    {
        path: 'rns-quotation-new',
        component: RnsQuotationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation/:id/edit',
        component: RnsQuotationPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bidding/:id/compare',
        component: RnsQuotationComparePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsQuotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation/:id/delete',
        component: RnsQuotationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
