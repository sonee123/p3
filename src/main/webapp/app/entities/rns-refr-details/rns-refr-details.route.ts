import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsRefrDetailsComponent } from './rns-refr-details.component';
import { RnsRefrDetailsDetailComponent } from './rns-refr-details-detail.component';
import { RnsRefrDetailsPopupComponent } from './rns-refr-details-dialog.component';
import { RnsRefrDetailsDeletePopupComponent } from './rns-refr-details-delete-dialog.component';

export const rnsRefrDetailsRoute: Routes = [
    {
        path: 'rns-refr-details',
        component: RnsRefrDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-refr-details/:id',
        component: RnsRefrDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsRefrDetailsPopupRoute: Routes = [
    {
        path: 'rns-refr-details-new',
        component: RnsRefrDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-refr-details/:id/edit',
        component: RnsRefrDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-refr-details/:id/delete',
        component: RnsRefrDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRefrDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
