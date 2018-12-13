import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { MessageBodyComponent } from './message-body.component';
import { MessageBodyDetailComponent } from './message-body-detail.component';
import { MessageBodyPopupComponent } from './message-body-dialog.component';
import { MessageBodyDeletePopupComponent } from './message-body-delete-dialog.component';

export const messageBodyRoute: Routes = [
    {
        path: 'message-body',
        component: MessageBodyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MessageBodies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'message-body/:id',
        component: MessageBodyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MessageBodies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messageBodyPopupRoute: Routes = [
    {
        path: 'message-body-new',
        component: MessageBodyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MessageBodies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-body/:id/edit',
        component: MessageBodyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MessageBodies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message-body/:id/delete',
        component: MessageBodyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MessageBodies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
