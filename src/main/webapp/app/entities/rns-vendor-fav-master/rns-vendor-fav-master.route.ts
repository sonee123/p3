import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsVendorFavMasterComponent } from './rns-vendor-fav-master.component';
import { RnsVendorFavMasterDetailComponent } from './rns-vendor-fav-master-detail.component';
import { RnsVendorFavMasterPopupComponent } from './rns-vendor-fav-master-dialog.component';
import { RnsVendorFavMasterDeletePopupComponent } from './rns-vendor-fav-master-delete-dialog.component';

export const rnsVendorFavMasterRoute: Routes = [
    {
        path: 'rns-vendor-fav-master',
        component: RnsVendorFavMasterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorFavMasters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-vendor-fav-master/:id',
        component: RnsVendorFavMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorFavMasters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsVendorFavMasterPopupRoute: Routes = [
    {
        path: 'rns-vendor-fav-master-new',
        component: RnsVendorFavMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorFavMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-fav-master/:id/edit',
        component: RnsVendorFavMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorFavMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-fav-master/:id/delete',
        component: RnsVendorFavMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorFavMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
