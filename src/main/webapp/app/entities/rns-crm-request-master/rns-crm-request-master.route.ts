import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { RnsCrmRequestMasterComponent } from './rns-crm-request-master.component';
import { RnsCrmRequestMasterDetailComponent } from './rns-crm-request-master-detail.component';
import { RnsCrmRequestMasterPopupComponent } from './rns-crm-request-master-dialog.component';
import { RnsCrmRequestMasterPopupSearchComponent } from './rns-crm-request-master-search.component';
import { RnsCrmRequestMasterDeletePopupComponent } from './rns-crm-request-master-delete-dialog.component';

export const rnsCrmRequestMasterRoute: Routes = [
    {
        path: 'rns-crm-request-master',
        component: RnsCrmRequestMasterComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - CRM Request Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-crm-request-master/:id',
        component: RnsCrmRequestMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - CRM Request Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsCrmRequestMasterPopupRoute: Routes = [
    {
        path: 'rns-crm-request-master-new',
        component: RnsCrmRequestMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - CRM Request Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-crm-request-master/:id/edit',
        component: RnsCrmRequestMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - CRM Request Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-crm-request-master/:id/delete',
        component: RnsCrmRequestMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - CRM Request Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-crm-request-master/search',
        component: RnsCrmRequestMasterPopupSearchComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
            pageTitle: 'P3 - CRM Request Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
