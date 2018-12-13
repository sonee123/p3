import { Routes } from '@angular/router';

import { RnsmainComponent } from './';
import { AuctionListComponent } from './';
import { RnsQuotationComponent } from './';
import { RnsAuctionQuotationComponent } from './';
import { UserRouteAccessService } from '../core';

export const RNSMAIN_ROUTE: Routes = [
    {
        path: 'vendor',
        component: RnsmainComponent,
        data: {
            authorities: ['ROLE_VENDOR'],
            pageTitle: 'Generate Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bidding',
        component: AuctionListComponent,
        data: {
            authorities: ['ROLE_VENDOR'],
            pageTitle: 'Generate Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendor/quotation/:id/view',
        component: RnsQuotationComponent,
        data: {
            authorities: ['ROLE_VENDOR'],
            pageTitle: 'Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendor/quotation/:id/auction',
        component: RnsAuctionQuotationComponent,
        data: {
            authorities: ['ROLE_VENDOR'],
            pageTitle: 'Project'
        },
        canActivate: [UserRouteAccessService]
    }
];
