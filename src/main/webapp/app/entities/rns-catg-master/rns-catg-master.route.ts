import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsCatgMasterComponent } from './rns-catg-master.component';
import { RnsCatgMasterDetailComponent } from './rns-catg-master-detail.component';
import { RnsCatgMasterPopupComponent } from './rns-catg-master-dialog.component';
import { RnsCatgMasterDeletePopupComponent } from './rns-catg-master-delete-dialog.component';
import { RnsCatgUserPopupComponent } from './rns-catg-user-dialog.component';

export const rnsCatgMasterRoute: Routes = [
    {
        path: 'rns-catg-master',
        component: RnsCatgMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Category Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-catg-master/:id',
        component: RnsCatgMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Category Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsCatgMasterPopupRoute: Routes = [
    {
        path: 'rns-catg-master-new',
        component: RnsCatgMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Category Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-catg-master/:id/edit',
        component: RnsCatgMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Category Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-catg-master/:id/delete',
        component: RnsCatgMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Category Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-catg-master/:login/user',
        component: RnsCatgUserPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'P3 - Category Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
