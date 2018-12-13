import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsUpchargeDtlComponent } from './rns-upcharge-dtl.component';
import { RnsUpchargeDtlDetailComponent } from './rns-upcharge-dtl-detail.component';
import { RnsUpchargeDtlPopupComponent } from './rns-upcharge-dtl-dialog.component';
import { RnsUpchargeDtlDeletePopupComponent } from './rns-upcharge-dtl-delete-dialog.component';
import { RnsUpchargeDtlSelectPopupComponent } from './rns-upcharge-dtl-select-dialog.component';

export const rnsUpchargeDtlRoute: Routes = [
    {
        path: 'rns-upcharge-dtl',
        component: RnsUpchargeDtlComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsUpchargeDtls'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-upcharge-dtl/:id',
        component: RnsUpchargeDtlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsUpchargeDtls'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsUpchargeDtlPopupRoute: Routes = [
    {
        path: 'rns-upcharge-dtl-new',
        component: RnsUpchargeDtlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsUpchargeDtls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-upcharge-dtl/:id/edit',
        component: RnsUpchargeDtlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsUpchargeDtls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-upcharge-dtl-select/:id/upcharge',
        component: RnsUpchargeDtlSelectPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'RnsUpchargeDtls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-upcharge-dtl/:id/delete',
        component: RnsUpchargeDtlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsUpchargeDtls'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
