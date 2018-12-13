import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmailTemplate } from './email-template.model';
import { EmailTemplatePopupService } from './email-template-popup.service';
import { EmailTemplateService } from './email-template.service';

@Component({
    selector: 'jhi-email-template-delete-dialog',
    templateUrl: './email-template-delete-dialog.component.html'
})
export class EmailTemplateDeleteDialogComponent {
    emailTemplate: EmailTemplate;

    constructor(
        private emailTemplateService: EmailTemplateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailTemplateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'emailTemplateListModification',
                content: 'Deleted an emailTemplate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-template-delete-popup',
    template: ''
})
export class EmailTemplateDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private emailTemplatePopupService: EmailTemplatePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.emailTemplatePopupService.open(EmailTemplateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
