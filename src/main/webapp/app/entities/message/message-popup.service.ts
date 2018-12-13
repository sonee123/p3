import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Message } from './message.model';
import { MessageService } from './message.service';
import { RnsQuotation, RnsQuotationService } from '../rns-quotation';

@Injectable()
export class MessagePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private messageService: MessageService,
        private rnsQuotationService: RnsQuotationService
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
                this.messageService.find(id).subscribe(message => {
                    this.ngbModalRef = this.messageModalRef(component, message.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.messageModalRef(component, new Message());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    quotation(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }
            this.rnsQuotationService.find(id).subscribe(rnsQuotation => {
                this.messageService.queryByQuotation(id).subscribe(messages => {
                    this.ngbModalRef = this.messageModalRefList(component, messages.body, rnsQuotation.body);
                    resolve(this.ngbModalRef);
                });
            });
        });
    }

    messageModalRef(component: Component, message: Message): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.message = message;
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

    messageModalRefList(component: Component, messages: Message[], rnsQuotation: RnsQuotation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static', windowClass: 'modal-xxl' });
        modalRef.componentInstance.messages = messages;
        modalRef.componentInstance.rnsQuotation = rnsQuotation;
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
