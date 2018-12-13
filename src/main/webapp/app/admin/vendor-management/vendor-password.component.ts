import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { Principal, User } from 'app/core';
import { PasswordService } from 'app/account';
import { VendorModalService } from 'app/admin/vendor-management/vendor-modal.service';

@Component({
    selector: 'jhi-vendor-password',
    templateUrl: './vendor-password.component.html'
})
export class VendorPasswordComponent implements OnInit {
    doNotMatch: string;
    error: string;
    success: string;
    account: any;
    password: string;
    confirmPassword: string;
    user: User;
    isSaving: Boolean;

    constructor(
        private passwordService: PasswordService,
        private principal: Principal,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    changePassword() {
        if (this.password !== this.confirmPassword) {
            this.error = null;
            this.success = null;
            this.doNotMatch = 'ERROR';
        } else {
            this.isSaving = true;
            this.user.password = this.password;
            this.doNotMatch = null;
            this.passwordService.savepassword(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'userListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-vendor-password-dialog',
    template: ''
})
export class VendorPasswordDialogComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private vendorModalService: VendorModalService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['login']) {
                this.vendorModalService.open(VendorPasswordComponent as Component, params['login']);
            } else {
                this.vendorModalService.open(VendorPasswordComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
