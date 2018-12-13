import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsArticleMasterComponent } from './rns-article-master.component';
import { RnsArticleMasterDetailComponent } from './rns-article-master-detail.component';
import { RnsArticleMasterPopupComponent } from './rns-article-master-dialog.component';
import { RnsArticleMasterDeletePopupComponent } from './rns-article-master-delete-dialog.component';
import { RnsArticleMasterSearchPopupComponent } from './rns-article-master-search.component';

export const rnsArticleMasterRoute: Routes = [
    {
        path: 'rns-article-master',
        component: RnsArticleMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Article Masters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-article-master/:id',
        component: RnsArticleMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Article Masters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsArticleMasterPopupRoute: Routes = [
    {
        path: 'rns-article-master-new',
        component: RnsArticleMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Article Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-article-master/:id/edit',
        component: RnsArticleMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Article Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-article-master/:id/delete',
        component: RnsArticleMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Article Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-article-master/:catgId/search',
        component: RnsArticleMasterSearchPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Article Masters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
