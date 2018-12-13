import { Component, ElementRef, Input, OnInit, Renderer } from '@angular/core';
import { User, UserService } from 'app/core';

@Component({
    selector: 'jhi-user-name-details',
    templateUrl: './userdetail.component.html'
})
export class UserDetailComponent implements OnInit {
    user: User;

    constructor(private userService: UserService) {}

    ngOnInit() {
        this.userService.currentuser().subscribe(user => {
            this.user = user.body;
        });
    }

    authorityDisplay(roleName) {
        if (roleName === 'ROLE_VENDOR') {
            return 'ROLE_PARTY';
        } else {
            return roleName;
        }
    }
}
