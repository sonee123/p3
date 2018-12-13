import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { PasswordComponent } from './password.component';

export const passwordRoute: Route = {
    path: 'password',
    component: PasswordComponent,
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_AUDITOR', 'ROLE_VENDOR'],
        pageTitle: 'Password'
    },
    canActivate: [UserRouteAccessService]
};
