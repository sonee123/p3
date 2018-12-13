import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationEventDefinationComponent } from './rns-quotation-event-defination.component';
import { RnsQuotationEventDefinationDetailComponent } from './rns-quotation-event-defination-detail.component';
import { RnsQuotationEventDefinationPopupComponent } from './rns-quotation-event-defination-dialog.component';
import { RnsQuotationEventDefinationDeletePopupComponent } from './rns-quotation-event-defination-delete-dialog.component';

export const rnsQuotationEventDefinationRoute: Routes = [
    {
        path: 'rns-quotation-event-defination',
        component: RnsQuotationEventDefinationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationEventDefinations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-event-defination/:id',
        component: RnsQuotationEventDefinationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationEventDefinations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationEventDefinationPopupRoute: Routes = [
    {
        path: 'rns-quotation-event-defination-new',
        component: RnsQuotationEventDefinationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationEventDefinations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-event-defination/:id/edit',
        component: RnsQuotationEventDefinationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationEventDefinations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-event-defination/:id/delete',
        component: RnsQuotationEventDefinationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationEventDefinations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
