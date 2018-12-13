import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationVariantComponent } from './rns-quotation-variant.component';
import { RnsQuotationVariantDetailComponent } from './rns-quotation-variant-detail.component';
import { RnsQuotationVariantPopupComponent } from './rns-quotation-variant-dialog.component';
import { RnsQuotationVariantDeletePopupComponent } from './rns-quotation-variant-delete-dialog.component';

export const rnsQuotationVariantRoute: Routes = [
    {
        path: 'rns-quotation-variant',
        component: RnsQuotationVariantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVariants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-variant/:id',
        component: RnsQuotationVariantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVariants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationVariantPopupRoute: Routes = [
    {
        path: 'rns-quotation-variant-new',
        component: RnsQuotationVariantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVariants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-variant/:id/edit',
        component: RnsQuotationVariantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVariants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-variant/:id/delete',
        component: RnsQuotationVariantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationVariants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
