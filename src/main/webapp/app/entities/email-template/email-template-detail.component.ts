import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmailTemplate } from './email-template.model';
import { EmailTemplateService } from './email-template.service';

@Component({
    selector: 'jhi-email-template-detail',
    templateUrl: './email-template-detail.component.html'
})
export class EmailTemplateDetailComponent implements OnInit, OnDestroy {
    emailTemplate: EmailTemplate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private emailTemplateService: EmailTemplateService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInEmailTemplates();
    }

    load(id) {
        this.emailTemplateService.find(id).subscribe(emailTemplate => {
            this.emailTemplate = emailTemplate.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('emailTemplateListModification', response => this.load(this.emailTemplate.id));
    }
}
