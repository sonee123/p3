import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsVendorRemarkCommentComponent } from './rns-vendor-remark-comment.component';
import { RnsVendorRemarkCommentDetailComponent } from './rns-vendor-remark-comment-detail.component';
import { RnsVendorRemarkCommentPopupComponent } from './rns-vendor-remark-comment-dialog.component';
import { RnsVendorRemarkCommentDeletePopupComponent } from './rns-vendor-remark-comment-delete-dialog.component';

export const rnsVendorRemarkCommentRoute: Routes = [
    {
        path: 'rns-vendor-remark-comment',
        component: RnsVendorRemarkCommentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarkComments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-vendor-remark-comment/:id',
        component: RnsVendorRemarkCommentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarkComments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsVendorRemarkCommentPopupRoute: Routes = [
    {
        path: 'rns-vendor-remark-comment-new',
        component: RnsVendorRemarkCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarkComments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-remark-comment/:id/edit',
        component: RnsVendorRemarkCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarkComments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-vendor-remark-comment/:id/delete',
        component: RnsVendorRemarkCommentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsVendorRemarkComments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
