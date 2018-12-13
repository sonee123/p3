import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorRemarkComment } from './rns-vendor-remark-comment.model';
import { RnsVendorRemarkCommentPopupService } from './rns-vendor-remark-comment-popup.service';
import { RnsVendorRemarkCommentService } from './rns-vendor-remark-comment.service';

@Component({
    selector: 'jhi-rns-vendor-remark-comment-delete-dialog',
    templateUrl: './rns-vendor-remark-comment-delete-dialog.component.html'
})
export class RnsVendorRemarkCommentDeleteDialogComponent {
    rnsVendorRemarkComment: RnsVendorRemarkComment;

    constructor(
        private rnsVendorRemarkCommentService: RnsVendorRemarkCommentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsVendorRemarkCommentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsVendorRemarkCommentListModification',
                content: 'Deleted an rnsVendorRemarkComment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-vendor-remark-comment-delete-popup',
    template: ''
})
export class RnsVendorRemarkCommentDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorRemarkCommentPopupService: RnsVendorRemarkCommentPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsVendorRemarkCommentPopupService.open(RnsVendorRemarkCommentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
