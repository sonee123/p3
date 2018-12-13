import { Routes } from '@angular/router';

import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    metricsRoute,
    trackerRoute,
    userMgmtRoute,
    vendorMgmtRoute,
    vendorDialogRoute
} from './';

import { UserRouteAccessService } from 'app/core';

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    logsRoute,
    trackerRoute,
    ...userMgmtRoute,
    ...vendorMgmtRoute,
    metricsRoute
];

export const adminState: Routes = [
    {
        path: '',
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR']
        },
        canActivate: [UserRouteAccessService],
        children: ADMIN_ROUTES
    },
    ...vendorDialogRoute
];
