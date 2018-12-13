import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';

import { Principal, User, UserService } from 'app/core';
import { VendorMgmtComponent } from './vendor-management.component';
import { VendorMgmtDetailComponent } from './vendor-management-detail.component';
import { VendorMgmtUpdateComponent } from './vendor-management-update.component';
import { VendorPasswordDialogComponent } from './vendor-password.component';

@Injectable({ providedIn: 'root' })
export class VendorResolve implements CanActivate {
    constructor(private principal: Principal) {}

    canActivate() {
        return this.principal.identity().then(account => this.principal.hasAnyAuthority(['ROLE_ADMIN']));
    }
}

@Injectable({ providedIn: 'root' })
export class VendorMgmtResolve implements Resolve<any> {
    constructor(private service: UserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['login'] ? route.params['login'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new User();
    }
}

export const vendorMgmtRoute: Routes = [
    {
        path: 'vendor-management',
        component: VendorMgmtComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            pageTitle: 'P3 - Party Registration',
            defaultSort: 'id,asc'
        }
    },
    {
        path: 'vendor-management/:login/view',
        component: VendorMgmtDetailComponent,
        resolve: {
            user: VendorMgmtResolve
        },
        data: {
            pageTitle: 'P3 - Party Registration'
        }
    },
    {
        path: 'vendor-management/new',
        component: VendorMgmtUpdateComponent,
        resolve: {
            user: VendorMgmtResolve
        },
        data: {
            pageTitle: 'P3 - Party Registration'
        }
    },
    {
        path: 'vendor-management/:login/edit',
        component: VendorMgmtUpdateComponent,
        resolve: {
            user: VendorMgmtResolve
        },
        data: {
            pageTitle: 'P3 - Party Registration'
        }
    }
];
export const vendorDialogRoute: Routes = [
    {
        path: 'password-management/:login/edit',
        component: VendorPasswordDialogComponent,
        outlet: 'popup',
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Party Registration'
        }
    }
];
