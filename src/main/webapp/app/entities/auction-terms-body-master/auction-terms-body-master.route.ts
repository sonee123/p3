import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuctionTermsBodyMasterComponent } from './auction-terms-body-master.component';
import { AuctionTermsBodyMasterDetailComponent } from './auction-terms-body-master-detail.component';
import { AuctionTermsBodyMasterPopupComponent } from './auction-terms-body-master-dialog.component';
import { AuctionTermsBodyMasterDeletePopupComponent } from './auction-terms-body-master-delete-dialog.component';

export const auctionTermsBodyMasterRoute: Routes = [
    {
        path: 'auction-terms-body-master',
        component: AuctionTermsBodyMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'AuctionTermsBodyMasters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'auction-terms-body-master/:id',
        component: AuctionTermsBodyMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'AuctionTermsBodyMasters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const auctionTermsBodyMasterPopupRoute: Routes = [
    {
        path: 'auction-terms-body-master-new',
        component: AuctionTermsBodyMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'AuctionTermsBodyMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-terms-body-master/:id/edit',
        component: AuctionTermsBodyMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'AuctionTermsBodyMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'auction-terms-body-master/:id/delete',
        component: AuctionTermsBodyMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'AuctionTermsBodyMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
