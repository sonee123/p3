import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsVendorRemarkComment } from './rns-vendor-remark-comment.model';
import { RnsVendorRemarkCommentService } from './rns-vendor-remark-comment.service';

@Injectable()
export class RnsVendorRemarkCommentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private rnsVendorRemarkCommentService: RnsVendorRemarkCommentService
    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsVendorRemarkCommentService.find(id).subscribe(rnsVendorRemarkComment => {
                    this.ngbModalRef = this.rnsVendorRemarkCommentModalRef(component, rnsVendorRemarkComment.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsVendorRemarkCommentModalRef(component, new RnsVendorRemarkComment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsVendorRemarkCommentModalRef(component: Component, rnsVendorRemarkComment: RnsVendorRemarkComment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsVendorRemarkComment = rnsVendorRemarkComment;
        modalRef.result.then(
            result => {
                this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
                this.ngbModalRef = null;
            },
            reason => {
                this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
                this.ngbModalRef = null;
            }
        );
        return modalRef;
    }
}
