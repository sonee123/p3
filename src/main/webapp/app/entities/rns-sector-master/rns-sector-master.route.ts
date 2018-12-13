import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsSectorMasterComponent } from './rns-sector-master.component';
import { RnsSectorMasterDetailComponent } from './rns-sector-master-detail.component';
import { RnsSectorMasterPopupComponent } from './rns-sector-master-dialog.component';
import { RnsSectorMasterDeletePopupComponent } from './rns-sector-master-delete-dialog.component';

export const rnsSectorMasterRoute: Routes = [
    {
        path: 'rns-sector-master',
        component: RnsSectorMasterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsSectorMasters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-sector-master/:id',
        component: RnsSectorMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsSectorMasters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsSectorMasterPopupRoute: Routes = [
    {
        path: 'rns-sector-master-new',
        component: RnsSectorMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsSectorMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-sector-master/:id/edit',
        component: RnsSectorMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsSectorMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-sector-master/:id/delete',
        component: RnsSectorMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsSectorMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
