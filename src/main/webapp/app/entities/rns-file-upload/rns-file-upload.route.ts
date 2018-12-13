import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RnsFileUploadComponent } from './rns-file-upload.component';
import { RnsFileUploadDetailComponent } from './rns-file-upload-detail.component';
import { RnsFileUploadPopupComponent } from './rns-file-upload-dialog.component';
import { RnsFileUploadDeletePopupComponent } from './rns-file-upload-delete-dialog.component';
import { UserRouteAccessService } from 'app/core';
import { RnsFileImportPopupComponent } from './rns-file-import-dialog.component';
import { RnsRfqFileImportPopupComponent } from './rns-rfq-file-import-dialog.component';

export const rnsFileUploadRoute: Routes = [
    {
        path: 'rns-file-upload',
        component: RnsFileUploadComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-file-upload/:id',
        component: RnsFileUploadDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsFileUploadPopupRoute: Routes = [
    {
        path: 'rns-file-upload-new',
        component: RnsFileUploadPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-file-upload/:id/edit',
        component: RnsFileUploadPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-file-upload/:id/:type',
        component: RnsFileUploadPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_VENDOR'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-file-import/:id',
        component: RnsFileImportPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-rfq-file-import/:id',
        component: RnsRfqFileImportPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-file-upload/:id/delete',
        component: RnsFileUploadDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsFileUploads'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
