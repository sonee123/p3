import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { EmailTemplateBody } from './email-template-body.model';
import { EmailTemplateBodyService } from './email-template-body.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-email-template-body',
    templateUrl: './email-template-body.component.html'
})
export class EmailTemplateBodyComponent implements OnInit, OnDestroy {
    emailTemplateBodies: EmailTemplateBody[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private emailTemplateBodyService: EmailTemplateBodyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.emailTemplateBodyService.query().subscribe(
            (res: HttpResponse<EmailTemplateBody[]>) => {
                this.emailTemplateBodies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEmailTemplateBodies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmailTemplateBody) {
        return item.id;
    }
    registerChangeInEmailTemplateBodies() {
        this.eventSubscriber = this.eventManager.subscribe('emailTemplateBodyListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
