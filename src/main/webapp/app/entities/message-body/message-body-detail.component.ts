import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MessageBody } from './message-body.model';
import { MessageBodyService } from './message-body.service';

@Component({
    selector: 'jhi-message-body-detail',
    templateUrl: './message-body-detail.component.html'
})
export class MessageBodyDetailComponent implements OnInit, OnDestroy {
    messageBody: MessageBody;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private messageBodyService: MessageBodyService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInMessageBodies();
    }

    load(id) {
        this.messageBodyService.find(id).subscribe(messageBody => {
            this.messageBody = messageBody.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMessageBodies() {
        this.eventSubscriber = this.eventManager.subscribe('messageBodyListModification', response => this.load(this.messageBody.id));
    }
}
