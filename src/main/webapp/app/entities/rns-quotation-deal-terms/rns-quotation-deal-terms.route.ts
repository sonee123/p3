import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationDealTermsComponent } from './rns-quotation-deal-terms.component';
import { RnsQuotationDealTermsDetailComponent } from './rns-quotation-deal-terms-detail.component';
import { RnsQuotationDealTermsPopupComponent } from './rns-quotation-deal-terms-dialog.component';
import { RnsQuotationDealTermsDeletePopupComponent } from './rns-quotation-deal-terms-delete-dialog.component';

export const rnsQuotationDealTermsRoute: Routes = [
    {
        path: 'rns-quotation-deal-terms',
        component: RnsQuotationDealTermsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationDealTerms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-deal-terms/:id',
        component: RnsQuotationDealTermsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationDealTerms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationDealTermsPopupRoute: Routes = [
    {
        path: 'rns-quotation-deal-terms-new',
        component: RnsQuotationDealTermsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationDealTerms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-deal-terms/:id/edit',
        component: RnsQuotationDealTermsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationDealTerms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-deal-terms/:id/delete',
        component: RnsQuotationDealTermsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationDealTerms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
