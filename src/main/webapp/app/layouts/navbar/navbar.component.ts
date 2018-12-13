import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {VERSION} from 'app/app.constants';
import {Principal, LoginModalService, LoginService} from 'app/core';
import {ProfileService} from '../profiles/profile.service';
import {CommonService} from 'app/common.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
    previousScroll = 0;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(public commonService: CommonService,
                private loginService: LoginService,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private profileService: ProfileService,
                private router: Router) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    @HostListener('window:scroll', ['$event'])
    onWindowScroll(event) {
        const currentScroll = window.pageYOffset;
        if (currentScroll > 60 && currentScroll < document.documentElement.scrollHeight - window.innerHeight) {
            if (currentScroll > this.previousScroll) {
                this.hideNavbar();
            } else {
                this.showNavbar();
            }
            this.previousScroll = currentScroll;
        }
    }

    hideNavbar = () => {
        setTimeout(() => {
            this.commonService.navbarToggleValue = true;
        }, 100);
    }

    showNavbar = () => {
        setTimeout(() => {
            this.commonService.navbarToggleValue = false;
        }, 100);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
