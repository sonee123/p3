import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { TemplateComponent } from './template.component';
import { TemplateDetailComponent } from './template-detail.component';
import { TemplatePopupComponent } from './template-dialog.component';
import { TemplateDeletePopupComponent } from './template-delete-dialog.component';
import { TemplateCopyPopupComponent } from './template-copy-dialog.component';

export const templateRoute: Routes = [
    {
        path: 'template',
        component: TemplateComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Templates'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'template/:id',
        component: TemplateDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Templates'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const templatePopupRoute: Routes = [
    {
        path: 'template-new',
        component: TemplatePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Templates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'template/:id/edit',
        component: TemplatePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Templates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'template/:id/copy',
        component: TemplateCopyPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Templates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'template/:id/delete',
        component: TemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Templates'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
