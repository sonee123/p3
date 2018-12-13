import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsDelPlaceMasterComponent } from './rns-del-place-master.component';
import { RnsDelPlaceMasterDetailComponent } from './rns-del-place-master-detail.component';
import { RnsDelPlaceMasterPopupComponent } from './rns-del-place-master-dialog.component';
import { RnsDelPlaceMasterDeletePopupComponent } from './rns-del-place-master-delete-dialog.component';

export const rnsDelPlaceMasterRoute: Routes = [
    {
        path: 'rns-del-place-master',
        component: RnsDelPlaceMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Delivery Place Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-del-place-master/:id',
        component: RnsDelPlaceMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Delivery Place Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsDelPlaceMasterPopupRoute: Routes = [
    {
        path: 'rns-del-place-master-new',
        component: RnsDelPlaceMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Delivery Place Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-del-place-master/:id/edit',
        component: RnsDelPlaceMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Delivery Place Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-del-place-master/:id/delete',
        component: RnsDelPlaceMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Delivery Place Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
