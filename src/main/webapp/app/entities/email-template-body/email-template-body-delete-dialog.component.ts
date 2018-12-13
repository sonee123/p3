import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmailTemplateBody } from './email-template-body.model';
import { EmailTemplateBodyPopupService } from './email-template-body-popup.service';
import { EmailTemplateBodyService } from './email-template-body.service';

@Component({
    selector: 'jhi-email-template-body-delete-dialog',
    templateUrl: './email-template-body-delete-dialog.component.html'
})
export class EmailTemplateBodyDeleteDialogComponent {
    emailTemplateBody: EmailTemplateBody;

    constructor(
        private emailTemplateBodyService: EmailTemplateBodyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailTemplateBodyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'emailTemplateBodyListModification',
                content: 'Deleted an emailTemplateBody'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-template-body-delete-popup',
    template: ''
})
export class EmailTemplateBodyDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private emailTemplateBodyPopupService: EmailTemplateBodyPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.emailTemplateBodyPopupService.open(EmailTemplateBodyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
