import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuctionTermsMasterComponent } from './auction-terms-master.component';
import { AuctionTermsMasterDetailComponent } from './auction-terms-master-detail.component';
import { AuctionTermsMasterPopupComponent } from './auction-terms-master-dialog.component';
import { AuctionTermsMasterDeletePopupComponent } from './auction-terms-master-delete-dialog.component';
import { AuctionTermsMasterAgreePopupComponent } from './auction-terms-master-agree-dialog.component';

export const auctionTermsMasterRoute: Routes = [
    {
        path: 'auction-terms-master',
        component: AuctionTermsMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Bidding Terms Masters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auction-terms-master/:id',
        component: AuctionTermsMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Bidding Terms Masters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auctionTermsMasterPopupRoute: Routes = [
    {
        path: 'auction-terms-master-new',
        component: AuctionTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Bidding Terms Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-terms-master/:id/edit',
        component: AuctionTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Bidding Terms Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-terms-master/:id/agree',
        component: AuctionTermsMasterAgreePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_VENDOR'],
            pageTitle: 'P3 - Bidding Terms Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-terms-master/:id/delete',
        component: AuctionTermsMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Bidding Terms Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
