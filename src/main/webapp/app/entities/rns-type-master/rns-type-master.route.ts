import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsTypeMasterComponent } from './rns-type-master.component';
import { RnsTypeMasterDetailComponent } from './rns-type-master-detail.component';
import { RnsTypeMasterPopupComponent } from './rns-type-master-dialog.component';
import { RnsTypeMasterDeletePopupComponent } from './rns-type-master-delete-dialog.component';

export const rnsTypeMasterRoute: Routes = [
    {
        path: 'rns-type-master',
        component: RnsTypeMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Project Region Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-type-master/:id',
        component: RnsTypeMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Project Region Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsTypeMasterPopupRoute: Routes = [
    {
        path: 'rns-type-master-new',
        component: RnsTypeMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Project Region Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-type-master/:id/edit',
        component: RnsTypeMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Project Region Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-type-master/:id/delete',
        component: RnsTypeMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Project Region Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
