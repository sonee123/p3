import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsVendorMaster } from './rns-vendor-master.model';
import { RnsVendorMasterPopupService } from './rns-vendor-master-popup.service';
import { RnsVendorMasterService } from './rns-vendor-master.service';
import { User, UserService } from 'app/core';

@Component({
    selector: 'jhi-rns-vendor-master-dialog',
    templateUrl: './rns-vendor-master-dialog.component.html'
})
export class RnsVendorMasterDialogComponent implements OnInit {
    rnsVendorMaster: RnsVendorMaster;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsVendorMasterService: RnsVendorMasterService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.userService.query().subscribe(
            res => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsVendorMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsVendorMasterService.update(this.rnsVendorMaster));
        } else {
            this.subscribeToSaveResponse(this.rnsVendorMasterService.create(this.rnsVendorMaster));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsVendorMaster>>) {
        result.subscribe((res: HttpResponse<RnsVendorMaster>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsVendorMaster>) {
        this.eventManager.broadcast({ name: 'rnsVendorMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-vendor-master-popup',
    template: ''
})
export class RnsVendorMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorMasterPopupService: RnsVendorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsVendorMasterPopupService.open(RnsVendorMasterDialogComponent as Component, params['id']);
            } else {
                this.rnsVendorMasterPopupService.open(RnsVendorMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
