import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRouteSnapshot, NavigationEnd, RouteConfigLoadStart, RouteConfigLoadEnd} from '@angular/router';

import { Title } from '@angular/platform-browser';
import {CommonService} from 'app/common.service';
import {Principal} from 'app/core';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html',
    styleUrls: ['main.scss']
})
export class JhiMainComponent implements OnInit {
    commonService;

    constructor(private titleService: Title,
                private cs: CommonService,
                private router: Router,
                private principal: Principal) {
        this.commonService = cs;
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'p3App';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
                this.cs.navbarToggleValue = false;
                this.cs.sidebarToggleValue = true;
                window.scrollTo(0, 0);
            }
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
