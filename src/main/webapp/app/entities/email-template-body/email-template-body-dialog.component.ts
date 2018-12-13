import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmailTemplateBody } from './email-template-body.model';
import { EmailTemplateBodyPopupService } from './email-template-body-popup.service';
import { EmailTemplateBodyService } from './email-template-body.service';

@Component({
    selector: 'jhi-email-template-body-dialog',
    templateUrl: './email-template-body-dialog.component.html'
})
export class EmailTemplateBodyDialogComponent implements OnInit {
    emailTemplateBody: EmailTemplateBody;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private emailTemplateBodyService: EmailTemplateBodyService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emailTemplateBody.id !== undefined) {
            this.subscribeToSaveResponse(this.emailTemplateBodyService.update(this.emailTemplateBody));
        } else {
            this.subscribeToSaveResponse(this.emailTemplateBodyService.create(this.emailTemplateBody));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmailTemplateBody>>) {
        result.subscribe((res: HttpResponse<EmailTemplateBody>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<EmailTemplateBody>) {
        this.eventManager.broadcast({ name: 'emailTemplateBodyListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-email-template-body-popup',
    template: ''
})
export class EmailTemplateBodyPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private emailTemplateBodyPopupService: EmailTemplateBodyPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.emailTemplateBodyPopupService.open(EmailTemplateBodyDialogComponent as Component, params['id']);
            } else {
                this.emailTemplateBodyPopupService.open(EmailTemplateBodyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
