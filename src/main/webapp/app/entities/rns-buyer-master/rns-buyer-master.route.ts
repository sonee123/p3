import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsBuyerMasterComponent } from './rns-buyer-master.component';
import { RnsBuyerMasterDetailComponent } from './rns-buyer-master-detail.component';
import { RnsBuyerMasterPopupComponent } from './rns-buyer-master-dialog.component';
import { RnsBuyerMasterDeletePopupComponent } from './rns-buyer-master-delete-dialog.component';

export const rnsBuyerMasterRoute: Routes = [
    {
        path: 'rns-buyer-master',
        component: RnsBuyerMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - End User Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-buyer-master/:id',
        component: RnsBuyerMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - End User Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsBuyerMasterPopupRoute: Routes = [
    {
        path: 'rns-buyer-master-new',
        component: RnsBuyerMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - End User Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-buyer-master/:id/edit',
        component: RnsBuyerMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - End User Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-buyer-master/:id/delete',
        component: RnsBuyerMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - End User Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
