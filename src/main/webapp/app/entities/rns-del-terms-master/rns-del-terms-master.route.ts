import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsDelTermsMasterComponent } from './rns-del-terms-master.component';
import { RnsDelTermsMasterDetailComponent } from './rns-del-terms-master-detail.component';
import { RnsDelTermsMasterPopupComponent } from './rns-del-terms-master-dialog.component';
import { RnsDelTermsMasterDeletePopupComponent } from './rns-del-terms-master-delete-dialog.component';

export const rnsDelTermsMasterRoute: Routes = [
    {
        path: 'rns-del-terms-master',
        component: RnsDelTermsMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Delivery Term Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-del-terms-master/:id',
        component: RnsDelTermsMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Delivery Term Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsDelTermsMasterPopupRoute: Routes = [
    {
        path: 'rns-del-terms-master-new',
        component: RnsDelTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Delivery Term Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-del-terms-master/:id/edit',
        component: RnsDelTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Delivery Term Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-del-terms-master/:id/delete',
        component: RnsDelTermsMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Delivery Term Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
