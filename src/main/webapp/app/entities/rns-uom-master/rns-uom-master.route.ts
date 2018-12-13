import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsUomMasterComponent } from './rns-uom-master.component';
import { RnsUomMasterDetailComponent } from './rns-uom-master-detail.component';
import { RnsUomMasterPopupComponent } from './rns-uom-master-dialog.component';
import { RnsUomMasterDeletePopupComponent } from './rns-uom-master-delete-dialog.component';

export const rnsUomMasterRoute: Routes = [
    {
        path: 'rns-uom-master',
        component: RnsUomMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - UOM Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-uom-master/:id',
        component: RnsUomMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - UOM Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsUomMasterPopupRoute: Routes = [
    {
        path: 'rns-uom-master-new',
        component: RnsUomMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - UOM Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-uom-master/:id/edit',
        component: RnsUomMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - UOM Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-uom-master/:id/delete',
        component: RnsUomMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - UOM Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
