import { Routes } from '@angular/router';

import { RnsmainComponent } from './';
import { RnsQuotationComponent } from './';
import { RnsmainviewComponent } from './';
import { RnsQuotationNewComponent } from './';
import { RnsmainAuctionListingComponent } from './';
import { UserRouteAccessService } from '../core';
import { LoginComponent } from './login.component';
import { DashboardComponent } from './dashboard.component';

export const RNSMAIN_ROUTE: Routes = [
    {
        path: 'home',
        component: RnsmainComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'Generate Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dashboard',
        component: DashboardComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR', 'ROLE_VENDOR'],
            pageTitle: 'Dashboard'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'login/:login/login',
        component: LoginComponent,
        data: {
            authorities: [],
            pageTitle: 'Generate Project'
        }
    },
    {
        path: 'quotation/:categoryId/new',
        component: RnsQuotationNewComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'New Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/:id/edit',
        component: RnsQuotationComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/:id/:type/copy',
        component: RnsQuotationNewComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/:id/view',
        component: RnsmainviewComponent,

        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'View Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/:id/auctionlisting',
        component: RnsmainAuctionListingComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'Project Listing'
        },
        canActivate: [UserRouteAccessService]
    }
];
