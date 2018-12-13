import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account, LoginModalService, LoginService, Principal } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { StateStorageService } from 'app/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';

@Component({
    selector: 'jhi-home-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    routeSub: any;
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private activeModal: NgbActiveModal,
        private router: Router,
        private location: Location,
        private stateStorageService: StateStorageService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            console.log('loginsss', params['login']);
            this.username = params['login'];
            this.password = params['login'];
            this.loginService
                .loginbylogin({
                    username: this.username,
                    password: this.password,
                    rememberMe: this.rememberMe
                })
                .then(() => {
                    this.authenticationError = false;
                    this.activeModal.dismiss('login success');
                    if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                        this.router.navigate(['']);
                    }

                    this.eventManager.broadcast({
                        name: 'authenticationSuccess',
                        content: 'Sending Authentication Success'
                    });
                    this.principal.identity().then(account => {
                        this.account = account;
                        if (this.account.authorities.indexOf('ROLE_VENDOR') !== -1) {
                            // this.router.navigate(['vendor']);
                            this.router.navigate(['message']);
                        } else {
                            this.router.navigate(['dashboard']);
                        }
                    });
                })
                .catch(() => {
                    this.authenticationError = true;
                });
        });

        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
    }
}
