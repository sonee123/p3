import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CurrencyComponent } from './currency.component';
import { CurrencyDetailComponent } from './currency-detail.component';
import { CurrencyPopupComponent } from './currency-dialog.component';
import { CurrencyDeletePopupComponent } from './currency-delete-dialog.component';

export const currencyRoute: Routes = [
    {
        path: 'currency',
        component: CurrencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'currency/:id',
        component: CurrencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const currencyPopupRoute: Routes = [
    {
        path: 'currency-new',
        component: CurrencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'currency/:id/edit',
        component: CurrencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'currency/:id/delete',
        component: CurrencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Currencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
