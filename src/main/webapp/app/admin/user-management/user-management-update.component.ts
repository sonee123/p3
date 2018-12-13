import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService } from 'app/core';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
    user: User;
    languages: any[];
    authorities: any[];
    isSaving: boolean;
    confirmPassword: string;
    doNotMatch: string;
    authority: any;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router,
        private notifier: NotifierService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;
            this.authority = this.user.authorities[0].name;
        });
        this.authorities = [];
        this.userService.authoritiesByType('ROLE_VENDOR', true).subscribe(authorities => {
            this.authorities = authorities;
        });
    }

    previousState() {
        this.router.navigate(['/admin/user-management']);
    }

    save() {
        this.isSaving = true;
        this.user.activated = true;
        this.user.authorities = [];
        this.user.authorities.push(this.authority);
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            if (this.user.password !== this.confirmPassword) {
                this.doNotMatch = 'ERROR';
                this.isSaving = false;
                this.notifier.notify('error', 'Password mismatch!!!!');
            } else {
                this.user.langKey = 'en';
                this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
            }
        }
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
