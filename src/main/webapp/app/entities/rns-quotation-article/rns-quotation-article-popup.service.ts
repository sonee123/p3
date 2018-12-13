import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsQuotationArticle } from './rns-quotation-article.model';
import { RnsQuotationArticleService } from './rns-quotation-article.service';

@Injectable()
export class RnsQuotationArticlePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsQuotationArticleService: RnsQuotationArticleService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsQuotationArticleService.find(id).subscribe(rnsQuotationArticle => {
                    this.ngbModalRef = this.rnsQuotationArticleModalRef(component, rnsQuotationArticle.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsQuotationArticleModalRef(component, new RnsQuotationArticle());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsQuotationArticleModalRef(component: Component, rnsQuotationArticle: RnsQuotationArticle): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsQuotationArticle = rnsQuotationArticle;
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
