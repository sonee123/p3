import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorRemarkComment } from './rns-vendor-remark-comment.model';
import { RnsVendorRemarkCommentService } from './rns-vendor-remark-comment.service';

@Component({
    selector: 'jhi-rns-vendor-remark-comment-detail',
    templateUrl: './rns-vendor-remark-comment-detail.component.html'
})
export class RnsVendorRemarkCommentDetailComponent implements OnInit, OnDestroy {
    rnsVendorRemarkComment: RnsVendorRemarkComment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsVendorRemarkCommentService: RnsVendorRemarkCommentService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsVendorRemarkComments();
    }

    load(id) {
        this.rnsVendorRemarkCommentService.find(id).subscribe(rnsVendorRemarkComment => {
            this.rnsVendorRemarkComment = rnsVendorRemarkComment.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsVendorRemarkComments() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorRemarkCommentListModification', response =>
            this.load(this.rnsVendorRemarkComment.id)
        );
    }
}
