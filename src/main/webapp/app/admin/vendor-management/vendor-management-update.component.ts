import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService } from 'app/core';
import { Register } from 'app/account';
import { JhiEventManager } from 'ng-jhipster';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { NotifierService } from 'angular-notifier';
import { Currency, CurrencyService } from 'app/entities/currency';

@Component({
    selector: 'jhi-vendor-mgmt-update',
    templateUrl: './vendor-management-update.component.html'
})
export class VendorMgmtUpdateComponent implements OnInit {
    user: User;
    languages: any[];
    authorities: any[];
    currencies: Currency[];
    isSaving: boolean;
    confirmPassword: string;
    doNotMatch: string;
    authority: any;
    success: boolean;
    errorEmailExists: string;
    errorUserExists: string;
    error: string;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router,
        private registerService: Register,
        private eventManager: JhiEventManager,
        private notifier: NotifierService,
        private currencyService: CurrencyService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;
            this.authority = this.user.authorities[0].name;
        });
        this.currencyService.query().subscribe(currencies => {
            this.currencies = currencies.body;
        });
        if (this.user.authorities) {
        } else {
            this.user.authorities = [];
            this.user.authorities.push('ROLE_VENDOR');
        }
        this.registerChangeInSearchVendor();
    }

    registerChangeInSearchVendor() {
        this.eventManager.subscribe('selectedVendorUserCreation', message => {
            const vendorDetails = message.content;
            this.user.vendorCode = vendorDetails.vendorCode;
            this.user.vendorName = vendorDetails.vendorName;
        });
    }

    previousState() {
        this.router.navigate(['/admin/vendor-management']);
    }

    save() {
        this.isSaving = true;
        this.user.activated = true;
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            if (this.user.password !== this.confirmPassword) {
                this.doNotMatch = 'ERROR';
                this.isSaving = false;
                this.notifier.notify('error', 'Password mismatch!!!!');
            } else {
                this.user.langKey = 'en';
                this.registerService
                    .save(this.user)
                    .subscribe(response => this.onSaveSuccess(response), response => this.processError(response));
                // this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
            }
        }
    }

    private processError(response) {
        this.success = null;
        this.isSaving = false;
        if (response.status === 400 && response.json().type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.json().type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    showHideVendor() {
        if (this.user.registeredUser) {
            this.user.registeredUser = false;
        } else {
            this.user.registeredUser = true;
        }
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'vendorListModification', content: 'OK' });
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
