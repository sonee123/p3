import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsVendorRemarkComponent } from './rns-vendor-remark.component';
import { RnsVendorRemarkDetailComponent } from './rns-vendor-remark-detail.component';
import { RnsVendorRemarkPopupComponent } from './rns-vendor-remark-dialog.component';
import { RnsVendorRemarkDeletePopupComponent } from './rns-vendor-remark-delete-dialog.component';

export const rnsVendorRemarkRoute: Routes = [
    {
        path: 'rns-vendor-remark',
        component: RnsVendorRemarkComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarks'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-vendor-remark/:id',
        component: RnsVendorRemarkDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarks'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsVendorRemarkPopupRoute: Routes = [
    {
        path: 'rns-vendor-remark-new',
        component: RnsVendorRemarkPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-remark/:id/edit',
        component: RnsVendorRemarkPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-remark/:id/delete',
        component: RnsVendorRemarkDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarks'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
