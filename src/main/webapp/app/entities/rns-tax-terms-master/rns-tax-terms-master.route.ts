import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsTaxTermsMasterComponent } from './rns-tax-terms-master.component';
import { RnsTaxTermsMasterDetailComponent } from './rns-tax-terms-master-detail.component';
import { RnsTaxTermsMasterPopupComponent } from './rns-tax-terms-master-dialog.component';
import { RnsTaxTermsMasterDeletePopupComponent } from './rns-tax-terms-master-delete-dialog.component';

export const rnsTaxTermsMasterRoute: Routes = [
    {
        path: 'rns-tax-terms-master',
        component: RnsTaxTermsMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Tax Terms Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-tax-terms-master/:id',
        component: RnsTaxTermsMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Tax Terms Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsTaxTermsMasterPopupRoute: Routes = [
    {
        path: 'rns-tax-terms-master-new',
        component: RnsTaxTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Tax Terms Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-tax-terms-master/:id/edit',
        component: RnsTaxTermsMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Tax Terms Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-tax-terms-master/:id/delete',
        component: RnsTaxTermsMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Tax Terms Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
