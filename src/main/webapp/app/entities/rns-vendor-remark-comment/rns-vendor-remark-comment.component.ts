import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsVendorRemarkComment } from './rns-vendor-remark-comment.model';
import { RnsVendorRemarkCommentService } from './rns-vendor-remark-comment.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-vendor-remark-comment',
    templateUrl: './rns-vendor-remark-comment.component.html'
})
export class RnsVendorRemarkCommentComponent implements OnInit, OnDestroy {
    rnsVendorRemarkComments: RnsVendorRemarkComment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsVendorRemarkCommentService: RnsVendorRemarkCommentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsVendorRemarkCommentService.query().subscribe(
            (res: HttpResponse<RnsVendorRemarkComment[]>) => {
                this.rnsVendorRemarkComments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsVendorRemarkComments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsVendorRemarkComment) {
        return item.id;
    }
    registerChangeInRnsVendorRemarkComments() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorRemarkCommentListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
