import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsRelationComponent } from './rns-relation.component';
import { RnsRelationDetailComponent } from './rns-relation-detail.component';
import { RnsRelationPopupComponent } from './rns-relation-dialog.component';
import { RnsRelationDeletePopupComponent } from './rns-relation-delete-dialog.component';

export const rnsRelationRoute: Routes = [
    {
        path: 'rns-relation',
        component: RnsRelationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRelations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-relation/:id',
        component: RnsRelationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRelations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsRelationPopupRoute: Routes = [
    {
        path: 'rns-relation-new',
        component: RnsRelationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-relation/:id/edit',
        component: RnsRelationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-relation/:id/delete',
        component: RnsRelationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsRelations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
