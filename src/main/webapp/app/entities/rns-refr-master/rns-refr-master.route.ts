import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsRefrMasterComponent } from './rns-refr-master.component';
import { RnsRefrMasterDetailComponent } from './rns-refr-master-detail.component';
import { RnsRefrMasterPopupComponent } from './rns-refr-master-dialog.component';
import { RnsRefrMasterDeletePopupComponent } from './rns-refr-master-delete-dialog.component';

export const rnsRefrMasterRoute: Routes = [
    {
        path: 'rns-refr-master',
        component: RnsRefrMasterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrMasters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-refr-master/:id',
        component: RnsRefrMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrMasters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsRefrMasterPopupRoute: Routes = [
    {
        path: 'rns-refr-master-new',
        component: RnsRefrMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-refr-master/:id/edit',
        component: RnsRefrMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-refr-master/:id/delete',
        component: RnsRefrMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
