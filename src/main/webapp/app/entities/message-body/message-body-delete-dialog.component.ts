import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MessageBody } from './message-body.model';
import { MessageBodyPopupService } from './message-body-popup.service';
import { MessageBodyService } from './message-body.service';

@Component({
    selector: 'jhi-message-body-delete-dialog',
    templateUrl: './message-body-delete-dialog.component.html'
})
export class MessageBodyDeleteDialogComponent {
    messageBody: MessageBody;

    constructor(
        private messageBodyService: MessageBodyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.messageBodyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'messageBodyListModification',
                content: 'Deleted an messageBody'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-message-body-delete-popup',
    template: ''
})
export class MessageBodyDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private messageBodyPopupService: MessageBodyPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.messageBodyPopupService.open(MessageBodyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
