import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsVendorRemarkComment } from './rns-vendor-remark-comment.model';
import { RnsVendorRemarkCommentPopupService } from './rns-vendor-remark-comment-popup.service';
import { RnsVendorRemarkCommentService } from './rns-vendor-remark-comment.service';
import { RnsVendorRemark, RnsVendorRemarkService } from '../rns-vendor-remark';

@Component({
    selector: 'jhi-rns-vendor-remark-comment-dialog',
    templateUrl: './rns-vendor-remark-comment-dialog.component.html'
})
export class RnsVendorRemarkCommentDialogComponent implements OnInit {
    rnsVendorRemarkComment: RnsVendorRemarkComment;
    isSaving: boolean;

    rnsvendorremarks: RnsVendorRemark[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsVendorRemarkCommentService: RnsVendorRemarkCommentService,
        private rnsVendorRemarkService: RnsVendorRemarkService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsVendorRemarkService.query().subscribe(
            (res: HttpResponse<RnsVendorRemark[]>) => {
                this.rnsvendorremarks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsVendorRemarkComment.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsVendorRemarkCommentService.update(this.rnsVendorRemarkComment));
        } else {
            this.subscribeToSaveResponse(this.rnsVendorRemarkCommentService.create(this.rnsVendorRemarkComment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsVendorRemarkComment>>) {
        result.subscribe((res: HttpResponse<RnsVendorRemarkComment>) => this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsVendorRemarkComment>) {
        this.eventManager.broadcast({ name: 'rnsVendorRemarkCommentListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsVendorRemarkById(index: number, item: RnsVendorRemark) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rns-vendor-remark-comment-popup',
    template: ''
})
export class RnsVendorRemarkCommentPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorRemarkCommentPopupService: RnsVendorRemarkCommentPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsVendorRemarkCommentPopupService.open(RnsVendorRemarkCommentDialogComponent as Component, params['id']);
            } else {
                this.rnsVendorRemarkCommentPopupService.open(RnsVendorRemarkCommentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
