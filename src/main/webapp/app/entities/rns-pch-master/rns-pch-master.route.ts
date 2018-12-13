import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsPchMasterComponent } from './rns-pch-master.component';
import { RnsPchMasterDetailComponent } from './rns-pch-master-detail.component';
import { RnsPchMasterPopupComponent } from './rns-pch-master-dialog.component';
import { RnsPchMasterDeletePopupComponent } from './rns-pch-master-delete-dialog.component';

export const rnsPchMasterRoute: Routes = [
    {
        path: 'rns-pch-master',
        component: RnsPchMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - PCH Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-pch-master/:id',
        component: RnsPchMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - PCH Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsPchMasterPopupRoute: Routes = [
    {
        path: 'rns-pch-master-new',
        component: RnsPchMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - PCH Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-pch-master/:id/edit',
        component: RnsPchMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - PCH Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-pch-master/:id/delete',
        component: RnsPchMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - PCH Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
