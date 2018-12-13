import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { EmailTemplateBodyComponent } from './email-template-body.component';
import { EmailTemplateBodyDetailComponent } from './email-template-body-detail.component';
import { EmailTemplateBodyPopupComponent } from './email-template-body-dialog.component';
import { EmailTemplateBodyDeletePopupComponent } from './email-template-body-delete-dialog.component';

export const emailTemplateBodyRoute: Routes = [
    {
        path: 'email-template-body',
        component: EmailTemplateBodyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailTemplateBodies'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'email-template-body/:id',
        component: EmailTemplateBodyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailTemplateBodies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailTemplateBodyPopupRoute: Routes = [
    {
        path: 'email-template-body-new',
        component: EmailTemplateBodyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailTemplateBodies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-template-body/:id/edit',
        component: EmailTemplateBodyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailTemplateBodies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-template-body/:id/delete',
        component: EmailTemplateBodyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailTemplateBodies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
