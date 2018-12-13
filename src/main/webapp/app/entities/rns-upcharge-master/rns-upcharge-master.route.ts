import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsUpchargeMasterComponent } from './rns-upcharge-master.component';
import { RnsUpchargeMasterDetailComponent } from './rns-upcharge-master-detail.component';
import { RnsUpchargeMasterPopupComponent } from './rns-upcharge-master-dialog.component';
import { RnsUpchargeMasterDeletePopupComponent } from './rns-upcharge-master-delete-dialog.component';

export const rnsUpchargeMasterRoute: Routes = [
    {
        path: 'rns-upcharge-master',
        component: RnsUpchargeMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Upcharge Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-upcharge-master/:id',
        component: RnsUpchargeMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Upcharge Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsUpchargeMasterPopupRoute: Routes = [
    {
        path: 'rns-upcharge-master-new',
        component: RnsUpchargeMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Upcharge Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-upcharge-master/:id/edit',
        component: RnsUpchargeMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Upcharge Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-upcharge-master/:id/delete',
        component: RnsUpchargeMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Upcharge Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
