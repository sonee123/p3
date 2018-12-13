import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationCrmRequestComponent } from './rns-quotation-crm-request.component';
import { RnsQuotationCrmRequestDetailComponent } from './rns-quotation-crm-request-detail.component';
import { RnsQuotationCrmRequestPopupComponent } from './rns-quotation-crm-request-dialog.component';
import { RnsQuotationCrmRequestDeletePopupComponent } from './rns-quotation-crm-request-delete-dialog.component';

export const rnsQuotationCrmRequestRoute: Routes = [
    {
        path: 'rns-quotation-crm-request',
        component: RnsQuotationCrmRequestComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationCrmRequests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-crm-request/:id',
        component: RnsQuotationCrmRequestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationCrmRequests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationCrmRequestPopupRoute: Routes = [
    {
        path: 'rns-quotation-crm-request-new',
        component: RnsQuotationCrmRequestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationCrmRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-crm-request/:id/edit',
        component: RnsQuotationCrmRequestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationCrmRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-crm-request/:id/delete',
        component: RnsQuotationCrmRequestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationCrmRequests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
