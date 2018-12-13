import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsEmpMasterComponent } from './rns-emp-master.component';
import { RnsEmpMasterDetailComponent } from './rns-emp-master-detail.component';
import { RnsEmpMasterPopupComponent } from './rns-emp-master-dialog.component';
import { RnsEmpMasterDeletePopupComponent } from './rns-emp-master-delete-dialog.component';

export const rnsEmpMasterRoute: Routes = [
    {
        path: 'rns-emp-master',
        component: RnsEmpMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Employee Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-emp-master/:id',
        component: RnsEmpMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Employee Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsEmpMasterPopupRoute: Routes = [
    {
        path: 'rns-emp-master-new',
        component: RnsEmpMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Employee Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-emp-master/:id/edit',
        component: RnsEmpMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Employee Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-emp-master/:id/delete',
        component: RnsEmpMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Employee Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
