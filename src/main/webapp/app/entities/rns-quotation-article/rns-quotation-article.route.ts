import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsQuotationArticleComponent } from './rns-quotation-article.component';
import { RnsQuotationArticleDetailComponent } from './rns-quotation-article-detail.component';
import { RnsQuotationArticlePopupComponent } from './rns-quotation-article-dialog.component';
import { RnsQuotationArticleDeletePopupComponent } from './rns-quotation-article-delete-dialog.component';

export const rnsQuotationArticleRoute: Routes = [
    {
        path: 'rns-quotation-article',
        component: RnsQuotationArticleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationArticles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-quotation-article/:id',
        component: RnsQuotationArticleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationArticles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsQuotationArticlePopupRoute: Routes = [
    {
        path: 'rns-quotation-article-new',
        component: RnsQuotationArticlePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationArticles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-article/:id/edit',
        component: RnsQuotationArticlePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationArticles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-quotation-article/:id/delete',
        component: RnsQuotationArticleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsQuotationArticles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
