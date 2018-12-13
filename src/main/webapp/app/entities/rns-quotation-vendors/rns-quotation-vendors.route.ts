import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationVendorsComponent } from './rns-quotation-vendors.component';
import { RnsQuotationVendorsDetailComponent } from './rns-quotation-vendors-detail.component';
import { RnsQuotationVendorsPopupComponent } from './rns-quotation-vendors-dialog.component';
import { RnsQuotationVendorsDeletePopupComponent } from './rns-quotation-vendors-delete-dialog.component';

export const rnsQuotationVendorsRoute: Routes = [
    {
        path: 'rns-quotation-vendors',
        component: RnsQuotationVendorsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVendors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-vendors/:id',
        component: RnsQuotationVendorsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVendors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationVendorsPopupRoute: Routes = [
    {
        path: 'rns-quotation-vendors-new',
        component: RnsQuotationVendorsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVendors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-vendors/:id/edit',
        component: RnsQuotationVendorsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVendors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-vendors/:id/delete',
        component: RnsQuotationVendorsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVendors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
