import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuctionComponent } from './auction.component';
import { AuctionDetailComponent } from './auction-detail.component';
import { AuctionPopupComponent } from './auction-dialog.component';
import { AuctionDeletePopupComponent } from './auction-delete-dialog.component';

export const auctionRoute: Routes = [
    {
        path: 'auction',
        component: AuctionComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Auctions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auction/:id',
        component: AuctionDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Auctions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auctionPopupRoute: Routes = [
    {
        path: 'auction-new',
        component: AuctionPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Auctions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction/:id/edit',
        component: AuctionPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'Auctions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction/:id/delete',
        component: AuctionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Auctions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
