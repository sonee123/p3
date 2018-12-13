import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsPayTermsMasterComponent } from './rns-pay-terms-master.component';
import { RnsPayTermsMasterDetailComponent } from './rns-pay-terms-master-detail.component';
import { RnsPayTermsMasterPopupComponent } from './rns-pay-terms-master-dialog.component';
import { RnsPayTermsMasterDeletePopupComponent } from './rns-pay-terms-master-delete-dialog.component';

export const rnsPayTermsMasterRoute: Routes = [
    {
        path: 'rns-pay-terms-master',
        component: RnsPayTermsMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Pay Term Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-pay-terms-master/:id',
        component: RnsPayTermsMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Pay Term Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsPayTermsMasterPopupRoute: Routes = [
    {
        path: 'rns-pay-terms-master-new',
        component: RnsPayTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Pay Term Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-pay-terms-master/:id/edit',
        component: RnsPayTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Pay Term Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-pay-terms-master/:id/delete',
        component: RnsPayTermsMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Pay Term Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
