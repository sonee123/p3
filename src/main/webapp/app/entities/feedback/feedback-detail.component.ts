import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Feedback } from './feedback.model';
import { FeedbackService } from './feedback.service';

@Component({
    selector: 'jhi-feedback-detail',
    templateUrl: './feedback-detail.component.html'
})
export class FeedbackDetailComponent implements OnInit, OnDestroy {
    feedback: Feedback;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    isWait: boolean;

    constructor(
        private eventManager: JhiEventManager,
        private feedbackService: FeedbackService,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInFeedbacks();
    }

    downloadPdf(feedback: Feedback): any {
        this.isWait = true;
        this.feedbackService.download(feedback.id).subscribe(
            res => {
                this.isWait = false;
                saveAs(res, `${feedback.displayAttachFile}`);
            },
            error => {
                this.isWait = false;
                this.onError(error.message);
            }
        );
    }

    load(id) {
        this.feedbackService.find(id).subscribe(feedback => {
            this.feedback = feedback.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFeedbacks() {
        this.eventSubscriber = this.eventManager.subscribe('feedbackListModification', response => this.load(this.feedback.id));
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
