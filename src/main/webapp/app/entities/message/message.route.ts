import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { MessageComponent } from './message.component';
import { MessageDetailComponent } from './message-detail.component';
import { MessagePopupComponent } from './message-dialog.component';
import { MessageDeletePopupComponent } from './message-delete-dialog.component';
import { MessageQuotationPopupComponent } from './message-quotation-dialog.component';

export const messageRoute: Routes = [
    {
        path: 'message',
        component: MessageComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'message/:id',
        component: MessageDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const messagePopupRoute: Routes = [
    {
        path: 'message-new',
        component: MessagePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message/:id/edit',
        component: MessagePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message/:id/delete',
        component: MessageDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'message/:id/quotation',
        component: MessageQuotationPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR'],
            pageTitle: 'Messages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
