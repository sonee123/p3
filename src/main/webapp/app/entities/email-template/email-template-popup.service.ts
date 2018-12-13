import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EmailTemplate } from './email-template.model';
import { EmailTemplateService } from './email-template.service';

@Injectable()
export class EmailTemplatePopupService {
    private ngbModalRef: NgbModalRef;
    private emailTemplate: EmailTemplate;

    constructor(private modalService: NgbModal, private router: Router, private emailTemplateService: EmailTemplateService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.emailTemplateService.find(id).subscribe(emailTemplate => {
                    this.emailTemplate = emailTemplate.body;
                    if (this.emailTemplate.createdDate) {
                        this.emailTemplate.createdDate = {
                            year: this.emailTemplate.createdDate.getFullYear(),
                            month: this.emailTemplate.createdDate.getMonth() + 1,
                            day: this.emailTemplate.createdDate.getDate()
                        };
                    }
                    if (this.emailTemplate.lastUpdatedDate) {
                        this.emailTemplate.lastUpdatedDate = {
                            year: this.emailTemplate.lastUpdatedDate.getFullYear(),
                            month: this.emailTemplate.lastUpdatedDate.getMonth() + 1,
                            day: this.emailTemplate.lastUpdatedDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.emailTemplateModalRef(component, this.emailTemplate);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.emailTemplateModalRef(component, new EmailTemplate());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    emailTemplateModalRef(component: Component, emailTemplate: EmailTemplate): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static', windowClass: 'modal-xl' });
        modalRef.componentInstance.emailTemplate = emailTemplate;
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
