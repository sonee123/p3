import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EmailTemplateBody } from './email-template-body.model';
import { EmailTemplateBodyService } from './email-template-body.service';

@Component({
    selector: 'jhi-email-template-body-detail',
    templateUrl: './email-template-body-detail.component.html'
})
export class EmailTemplateBodyDetailComponent implements OnInit, OnDestroy {
    emailTemplateBody: EmailTemplateBody;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emailTemplateBodyService: EmailTemplateBodyService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInEmailTemplateBodies();
    }

    load(id) {
        this.emailTemplateBodyService.find(id).subscribe(emailTemplateBody => {
            this.emailTemplateBody = emailTemplateBody.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailTemplateBodies() {
        this.eventSubscriber = this.eventManager.subscribe('emailTemplateBodyListModification', response =>
            this.load(this.emailTemplateBody.id)
        );
    }
}
