import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MessageBody } from './message-body.model';
import { MessageBodyService } from './message-body.service';

@Injectable()
export class MessageBodyPopupService {
    private ngbModalRef: NgbModalRef;
    private messageBody: MessageBody;

    constructor(private modalService: NgbModal, private router: Router, private messageBodyService: MessageBodyService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.messageBodyService.find(id).subscribe(messageBody => {
                    this.messageBody = messageBody.body;
                    if (this.messageBody.createdDate) {
                        this.messageBody.createdDate = {
                            year: this.messageBody.createdDate.getFullYear(),
                            month: this.messageBody.createdDate.getMonth() + 1,
                            day: this.messageBody.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.messageBodyModalRef(component, this.messageBody);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.messageBodyModalRef(component, new MessageBody());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    messageBodyModalRef(component: Component, messageBody: MessageBody): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.messageBody = messageBody;
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
