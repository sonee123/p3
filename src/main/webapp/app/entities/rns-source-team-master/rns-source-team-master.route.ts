import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsSourceTeamMasterComponent } from './rns-source-team-master.component';
import { RnsSourceTeamMasterDetailComponent } from './rns-source-team-master-detail.component';
import { RnsSourceTeamMasterPopupComponent } from './rns-source-team-master-dialog.component';
import { RnsSourceTeamMasterDeletePopupComponent } from './rns-source-team-master-delete-dialog.component';

export const rnsSourceTeamMasterRoute: Routes = [
    {
        path: 'rns-source-team-master',
        component: RnsSourceTeamMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Sourcing Team Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-source-team-master/:id',
        component: RnsSourceTeamMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Sourcing Team Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsSourceTeamMasterPopupRoute: Routes = [
    {
        path: 'rns-source-team-master-new',
        component: RnsSourceTeamMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Sourcing Team Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-source-team-master/:id/edit',
        component: RnsSourceTeamMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Sourcing Team Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-source-team-master/:id/delete',
        component: RnsSourceTeamMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Sourcing Team Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
