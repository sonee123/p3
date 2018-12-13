import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsRelation } from './rns-relation.model';
import { RnsRelationPopupService } from './rns-relation-popup.service';
import { RnsRelationService } from './rns-relation.service';
import { User, UserService } from 'app/core';

@Component({
    selector: 'jhi-rns-relation-dialog',
    templateUrl: './rns-relation-dialog.component.html'
})
export class RnsRelationDialogComponent implements OnInit {
    rnsRelation: RnsRelation;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsRelationService: RnsRelationService,
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
        if (this.rnsRelation.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsRelationService.update(this.rnsRelation));
        } else {
            this.subscribeToSaveResponse(this.rnsRelationService.create(this.rnsRelation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsRelation>>) {
        result.subscribe((res: HttpResponse<RnsRelation>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsRelation>) {
        this.eventManager.broadcast({ name: 'rnsRelationListModification', content: 'OK' });
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
    selector: 'jhi-rns-relation-popup',
    template: ''
})
export class RnsRelationPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRelationPopupService: RnsRelationPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsRelationPopupService.open(RnsRelationDialogComponent as Component, params['id']);
            } else {
                this.rnsRelationPopupService.open(RnsRelationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
