import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuctionVrntComponent } from './auction-vrnt.component';
import { AuctionVrntDetailComponent } from './auction-vrnt-detail.component';
import { AuctionVrntPopupComponent } from './auction-vrnt-dialog.component';
import { AuctionVrntGetByVariantPopupComponent } from './auction-vrnt-dialog-get-by-variant.component';
import { AuctionVrntDeletePopupComponent } from './auction-vrnt-delete-dialog.component';

export const auctionVrntRoute: Routes = [
    {
        path: 'auction-vrnt',
        component: AuctionVrntComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionVrnts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auction-vrnt/:id',
        component: AuctionVrntDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionVrnts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auctionVrntPopupRoute: Routes = [
    {
        path: 'auction-vrnt-new',
        component: AuctionVrntPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionVrnts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-vrnt/:id/edit',
        component: AuctionVrntPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionVrnts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-vrnt/get-by-variant/:id',
        component: AuctionVrntGetByVariantPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'AuctionVrnts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-vrnt/:id/delete',
        component: AuctionVrntDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AuctionVrnts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
