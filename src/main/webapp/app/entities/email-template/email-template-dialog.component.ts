import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmailTemplate } from './email-template.model';
import { EmailTemplatePopupService } from './email-template-popup.service';
import { EmailTemplateService } from './email-template.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-email-template-dialog',
    templateUrl: './email-template-dialog.component.html'
})
export class EmailTemplateDialogComponent implements OnInit {
    emailTemplate: EmailTemplate;
    isSaving: boolean;
    createdDateDp: any;
    lastUpdatedDateDp: any;

    name = 'ng2-ckeditor';
    ckeConfig: any;
    mycontent: string;
    log = '';
    @ViewChild('myckeditor') ckeditor: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private emailTemplateService: EmailTemplateService,
        private eventManager: JhiEventManager
    ) {
        this.mycontent = `<p>My html content</p>`;
    }

    ngOnInit() {
        this.isSaving = false;
        this.ckeConfig = {
            allowedContent: false,
            forcePasteAsPlainText: true
        };
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.emailTemplate.id !== undefined) {
            this.subscribeToSaveResponse(this.emailTemplateService.update(this.emailTemplate));
        } else {
            this.subscribeToSaveResponse(this.emailTemplateService.create(this.emailTemplate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmailTemplate>>) {
        result.subscribe((res: HttpResponse<EmailTemplate>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<EmailTemplate>) {
        this.eventManager.broadcast({ name: 'emailTemplateListModification', content: 'OK' });
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
    selector: 'jhi-email-template-popup',
    template: ''
})
export class EmailTemplatePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private emailTemplatePopupService: EmailTemplatePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.emailTemplatePopupService.open(EmailTemplateDialogComponent as Component, params['id']);
            } else {
                this.emailTemplatePopupService.open(EmailTemplateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
