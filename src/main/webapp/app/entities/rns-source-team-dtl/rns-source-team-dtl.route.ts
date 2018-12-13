import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsSourceTeamDtlComponent } from './rns-source-team-dtl.component';
import { RnsSourceTeamDtlDetailComponent } from './rns-source-team-dtl-detail.component';
import { RnsSourceTeamDtlPopupComponent } from './rns-source-team-dtl-dialog.component';
import { RnsSourceTeamDtlDeletePopupComponent } from './rns-source-team-dtl-delete-dialog.component';

export const rnsSourceTeamDtlRoute: Routes = [
    {
        path: 'rns-source-team-dtl',
        component: RnsSourceTeamDtlComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Sourcing Team Detail'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-source-team-dtl/:id',
        component: RnsSourceTeamDtlDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Sourcing Team Detail'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsSourceTeamDtlPopupRoute: Routes = [
    {
        path: 'rns-source-team-dtl-new',
        component: RnsSourceTeamDtlPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Sourcing Team Detail'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-source-team-dtl/:id/edit',
        component: RnsSourceTeamDtlPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Sourcing Team Detail'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-source-team-dtl/:id/delete',
        component: RnsSourceTeamDtlDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Sourcing Team Detail'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
