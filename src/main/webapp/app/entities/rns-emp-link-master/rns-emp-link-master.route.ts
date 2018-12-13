import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RnsEmpLinkMasterComponent } from './rns-emp-link-master.component';
import { RnsEmpLinkMasterDetailComponent } from './rns-emp-link-master-detail.component';
import { RnsEmpLinkMasterPopupComponent } from './rns-emp-link-master-dialog.component';
import { RnsEmpLinkMasterDeletePopupComponent } from './rns-emp-link-master-delete-dialog.component';
import { EmployeeSearchMasterPopupComponent } from 'app/entities/rns-emp-link-master/emp-search-master.component';

@Injectable()
export class RnsEmpLinkMasterResolvePagingParams implements Resolve<any> {
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

export const rnsEmpLinkMasterRoute: Routes = [
    {
        path: 'rns-emp-link-master',
        component: RnsEmpLinkMasterComponent,
        resolve: {
            pagingParams: RnsEmpLinkMasterResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Workflow Master'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rns-emp-link-master/:id',
        component: RnsEmpLinkMasterDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_AUDITOR'],
            pageTitle: 'P3 - Workflow Master'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rnsEmpLinkMasterPopupRoute: Routes = [
    {
        path: 'rns-emp-link-master-new',
        component: RnsEmpLinkMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Workflow Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-emp-link-master/:id/edit',
        component: RnsEmpLinkMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Workflow Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rns-emp-link-master/:id/delete',
        component: RnsEmpLinkMasterDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'P3 - Workflow Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-search/:id',
        component: EmployeeSearchMasterPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
            pageTitle: 'P3 - Workflow Master'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
