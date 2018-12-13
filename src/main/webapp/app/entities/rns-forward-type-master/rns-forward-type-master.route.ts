import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RnsForwardTypeMasterComponent } from './rns-forward-type-master.component';
import { RnsForwardTypeMasterDetailComponent } from './rns-forward-type-master-detail.component';
import { RnsForwardTypeMasterPopupComponent } from './rns-forward-type-master-dialog.component';
import { RnsForwardTypeMasterDeletePopupComponent } from './rns-forward-type-master-delete-dialog.component';

@Injectable()
export class RnsForwardTypeMasterResolvePagingParams implements Resolve<any> {
    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const rnsForwardTypeMasterRoute: Routes = [
    {
        path: 'rns-forward-type-master',
        component: RnsForwardTypeMasterComponent,
        resolve: {
            pagingParams: RnsForwardTypeMasterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsForwardTypeMasters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-forward-type-master/:id',
        component: RnsForwardTypeMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsForwardTypeMasters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsForwardTypeMasterPopupRoute: Routes = [
    {
        path: 'rns-forward-type-master-new',
        component: RnsForwardTypeMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsForwardTypeMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-forward-type-master/:id/edit',
        component: RnsForwardTypeMasterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsForwardTypeMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-forward-type-master/:id/delete',
        component: RnsForwardTypeMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RnsForwardTypeMasters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
