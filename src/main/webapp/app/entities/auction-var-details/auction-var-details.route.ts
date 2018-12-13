import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuctionVarDetailsComponent } from './auction-var-details.component';
import { AuctionVarDetailsDetailComponent } from './auction-var-details-detail.component';
import { AuctionVarDetailsPopupComponent } from './auction-var-details-dialog.component';
import { AuctionVarDetailsDeletePopupComponent } from './auction-var-details-delete-dialog.component';

export const auctionVarDetailsRoute: Routes = [
    {
        path: 'auction-var-details',
        component: AuctionVarDetailsComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVarDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auction-var-details/:id',
        component: AuctionVarDetailsDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVarDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auctionVarDetailsPopupRoute: Routes = [
    {
        path: 'auction-var-details-new',
        component: AuctionVarDetailsPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVarDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-var-details/:id/edit',
        component: AuctionVarDetailsPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVarDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-var-details/:id/:type/edit',
        component: AuctionVarDetailsPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVarDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-var-details/:id/delete',
        component: AuctionVarDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVarDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
