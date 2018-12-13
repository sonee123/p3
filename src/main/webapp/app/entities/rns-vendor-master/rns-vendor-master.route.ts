import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsVendorMasterComponent } from './rns-vendor-master.component';
import { RnsVendorMasterDetailComponent } from './rns-vendor-master-detail.component';
import { RnsVendorMasterPopupComponent } from './rns-vendor-master-dialog.component';
import { RnsVendorMasterDeletePopupComponent } from './rns-vendor-master-delete-dialog.component';
import { RnsVendorMasterSearchSelectPopupComponent } from './rns-vendor-master-search-select.component';
import { VendorSearchMasterPopupComponent } from './vendor-search-master.component';

export const rnsVendorMasterRoute: Routes = [
    {
        path: 'rns-vendor-master',
        component: RnsVendorMasterComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-vendor-master/:id',
        component: RnsVendorMasterDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsVendorMasterPopupRoute: Routes = [
    {
        path: 'rns-vendor-master-new',
        component: RnsVendorMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-master/:id/edit',
        component: RnsVendorMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-master/:id/delete',
        component: RnsVendorMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-master/search-select',
        component: RnsVendorMasterSearchSelectPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vendor-search-master-search',
        component: VendorSearchMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'P3 - Vendor Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
