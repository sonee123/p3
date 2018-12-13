import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmailTemplateBody } from './email-template-body.model';
import { EmailTemplateBodyService } from './email-template-body.service';

@Injectable()
export class EmailTemplateBodyPopupService {
    private ngbModalRef: NgbModalRef;
    private emailTemplateBody: EmailTemplateBody;

    constructor(private modalService: NgbModal, private router: Router, private emailTemplateBodyService: EmailTemplateBodyService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.emailTemplateBodyService.find(id).subscribe(emailTemplateBody => {
                    this.emailTemplateBody = emailTemplateBody.body;
                    if (this.emailTemplateBody.createdDate) {
                        this.emailTemplateBody.createdDate = {
                            year: this.emailTemplateBody.createdDate.getFullYear(),
                            month: this.emailTemplateBody.createdDate.getMonth() + 1,
                            day: this.emailTemplateBody.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.emailTemplateBodyModalRef(component, this.emailTemplateBody);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.emailTemplateBodyModalRef(component, new EmailTemplateBody());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    emailTemplateBodyModalRef(component: Component, emailTemplateBody: EmailTemplateBody): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.emailTemplateBody = emailTemplateBody;
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
