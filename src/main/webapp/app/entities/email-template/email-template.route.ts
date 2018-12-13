import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { EmailTemplateComponent } from './email-template.component';
import { EmailTemplateDetailComponent } from './email-template-detail.component';
import { EmailTemplatePopupComponent } from './email-template-dialog.component';
import { EmailTemplateDeletePopupComponent } from './email-template-delete-dialog.component';

export const emailTemplateRoute: Routes = [
    {
        path: 'email-template',
        component: EmailTemplateComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Email Template'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'email-template/:id',
        component: EmailTemplateDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Email Template'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailTemplatePopupRoute: Routes = [
    {
        path: 'email-template-new',
        component: EmailTemplatePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Email Template'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-template/:id/edit',
        component: EmailTemplatePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Email Template'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-template/:id/delete',
        component: EmailTemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Email Template'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
