import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsRfqPriceComponent } from './rns-rfq-price.component';
import { RnsRfqPriceDetailComponent } from './rns-rfq-price-detail.component';
import { RnsRfqPricePopupComponent } from './rns-rfq-price-dialog.component';
import { RnsRfqPriceDeletePopupComponent } from './rns-rfq-price-delete-dialog.component';
import { RnsRfqPriceSelectPopupComponent } from './rns-rfq-price-select-dialog.component';

export const rnsRfqPriceRoute: Routes = [
    {
        path: 'rns-rfq-price',
        component: RnsRfqPriceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRfqPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-rfq-price/:id',
        component: RnsRfqPriceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRfqPrices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsRfqPricePopupRoute: Routes = [
    {
        path: 'rns-rfq-price-new',
        component: RnsRfqPricePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRfqPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-rfq-price/:id/edit',
        component: RnsRfqPricePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRfqPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-rfq-price/:id/rfq',
        component: RnsRfqPriceSelectPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsRfqPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-rfq-price/:id/delete',
        component: RnsRfqPriceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRfqPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
