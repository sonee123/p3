import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuctionPauseDetailsComponent } from './auction-pause-details.component';
import { AuctionPauseDetailsDetailComponent } from './auction-pause-details-detail.component';
import { AuctionPauseDetailsPopupComponent } from './auction-pause-details-dialog.component';
import { AuctionPauseDetailsDeletePopupComponent } from './auction-pause-details-delete-dialog.component';

export const auctionPauseDetailsRoute: Routes = [
    {
        path: 'auction-pause-details',
        component: AuctionPauseDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionPauseDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auction-pause-details/:id',
        component: AuctionPauseDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionPauseDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auctionPauseDetailsPopupRoute: Routes = [
    {
        path: 'auction-pause-details-new',
        component: AuctionPauseDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionPauseDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-pause-details/:id/edit',
        component: AuctionPauseDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionPauseDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-pause-details/:id/delete',
        component: AuctionPauseDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionPauseDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
