import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MessageBody } from './message-body.model';
import { MessageBodyPopupService } from './message-body-popup.service';
import { MessageBodyService } from './message-body.service';

@Component({
    selector: 'jhi-message-body-dialog',
    templateUrl: './message-body-dialog.component.html'
})
export class MessageBodyDialogComponent implements OnInit {
    messageBody: MessageBody;
    isSaving: boolean;
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private messageBodyService: MessageBodyService,
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
        if (this.messageBody.id !== undefined) {
            this.subscribeToSaveResponse(this.messageBodyService.update(this.messageBody));
        } else {
            this.subscribeToSaveResponse(this.messageBodyService.create(this.messageBody));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MessageBody>>) {
        result.subscribe((res: HttpResponse<MessageBody>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<MessageBody>) {
        this.eventManager.broadcast({ name: 'messageBodyListModification', content: 'OK' });
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
    selector: 'jhi-message-body-popup',
    template: ''
})
export class MessageBodyPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private messageBodyPopupService: MessageBodyPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.messageBodyPopupService.open(MessageBodyDialogComponent as Component, params['id']);
            } else {
                this.messageBodyPopupService.open(MessageBodyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
