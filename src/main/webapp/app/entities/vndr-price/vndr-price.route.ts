import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { VndrPriceComponent } from './vndr-price.component';
import { VndrPriceDetailComponent } from './vndr-price-detail.component';
import { VndrPricePopupComponent } from './vndr-price-dialog.component';
import { VndrPriceDeletePopupComponent } from './vndr-price-delete-dialog.component';
import { VndrPriceSelectPopupComponent } from './vndr-price-select-dialog.component';

export const vndrPriceRoute: Routes = [
    {
        path: 'vndr-price',
        component: VndrPriceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VndrPrices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vndr-price/:id',
        component: VndrPriceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VndrPrices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vndrPricePopupRoute: Routes = [
    {
        path: 'vndr-price-new',
        component: VndrPricePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VndrPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vndr-price/:id/edit',
        component: VndrPricePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VndrPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vndr-price/:id/surrogate',
        component: VndrPriceSelectPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'VndrPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vndr-price/:id/delete',
        component: VndrPriceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VndrPrices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
