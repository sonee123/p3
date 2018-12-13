import { Component, OnInit } from '@angular/core';
import { LoginService, Principal } from 'app/core';
import { CommonService } from 'app/common.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
    public sidebarItems: any;
    isNavbarCollapsed: boolean;

    ngOnInit() {
        this.sidebarItems = [
            {
                link: 'dashboard',
                label: 'Dashboard',
                icon: 'tachometer-alt',
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR']
            },
            {
                link: 'home',
                label: 'Prepare Project',
                icon: 'pencil-alt',
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER']
            },
            { link: 'vendor', label: 'RFQ', icon: 'quote-right', type: ['ROLE_VENDOR'] },
            { link: 'bidding', label: 'Bidding', icon: 'gavel', type: ['ROLE_VENDOR'] },
            {
                link: 'message',
                label: 'Messages',
                icon: 'inbox',
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR']
            },
            {
                label: 'Entities',
                icon: 'th-list',
                isCollapsed: false,
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                subItem: [
                    { link: 'rns-article-master', label: 'Article Master', icon: 'am' },
                    { link: 'auction-terms-master', label: 'Bidding Terms Master', icon: 'bt' },
                    { link: 'rns-catg-master', label: 'Category Master', icon: 'cm' },
                    { link: 'rns-crm-request-master', label: 'CRM Request Master', icon: 'cr' },
                    { link: 'rns-del-place-master', label: 'Delivery Place Master', icon: 'dp' },
                    { link: 'rns-del-terms-master', label: 'Delivery Terms Master', icon: 'dt' },
                    { link: 'email-template', label: 'Email Template', icon: 'et' },
                    { link: 'rns-buyer-master', label: 'End User Master', icon: 'eu' },
                    { link: 'rns-pay-terms-master', label: 'Pay Terms Master', icon: 'pt' },
                    { link: 'rns-pch-master', label: 'PCH Master', icon: 'pm' },
                    { link: 'rns-type-master', label: 'Project Region Master', icon: 'pr' },
                    { link: 'rns-source-team-master', label: 'Sourcing Team Master', icon: 'st' },
                    { link: 'rns-tax-terms-master', label: 'Tax Terms Master', icon: 'tt' },
                    { link: 'template', label: 'Template Master', icon: 'tc' },
                    { link: 'rns-uom-master', label: 'UOM Master', icon: 'um' },
                    { link: 'rns-upcharge-master', label: 'Upcharge Master', icon: 'uc' },
                    { link: 'rns-vendor-master', label: 'Vendor Master', icon: 'vm' }
                ]
            },
            {
                label: 'Administration',
                icon: 'user-plus',
                isCollapsed: false,
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                subItem: [
                    { link: 'user-management', label: 'User management', icon: 'um' },
                    { link: 'rns-source-team-dtl', label: 'Sourcing Team Detail', icon: 'sd' },
                    { link: 'rns-emp-link-master', label: 'Workflow Master', icon: 'wm' },
                    { link: 'vendor-management', label: 'Party Registration', icon: 'pr' }
                ]
            },
            {
                label: 'Reports',
                icon: 'chart-bar',
                isCollapsed: false,
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER'],
                subItem: [{ link: 'detail-report', label: 'Project Plan Detail', icon: 'PD' }]
            },
            {
                label: 'Reports',
                icon: 'chart-bar',
                isCollapsed: false,
                type: ['ROLE_VENDOR'],
                subItem: []
            },
            {
                link: 'feedback',
                label: 'Feedback',
                icon: 'comments',
                type: ['ROLE_AUDITOR', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER', 'ROLE_VENDOR']
            }
        ];
    }

    constructor(
        public commonService: CommonService,
        private principal: Principal,
        private loginService: LoginService,
        private router: Router
    ) {
        this.isNavbarCollapsed = true;
    }

    public collapseMenu(item) {
        if (item.isCollapsed === true) {
            item.isCollapsed = false;
        } else {
            item.isCollapsed = true;
        }
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }
}
