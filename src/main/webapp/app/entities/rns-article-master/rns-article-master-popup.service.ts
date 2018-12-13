import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsArticleMaster } from './rns-article-master.model';
import { RnsArticleMasterService } from './rns-article-master.service';

@Injectable()
export class RnsArticleMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsArticleMasterService: RnsArticleMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any, catgId?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsArticleMasterService.find(id).subscribe(rnsArticleMaster => {
                    this.ngbModalRef = this.rnsArticleMasterModalRef(component, rnsArticleMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else if (catgId) {
                this.rnsArticleMasterService.findByCatg(catgId).subscribe(rnsArticleMasters => {
                    this.ngbModalRef = this.rnsArticleSearchMasterModalRef(component, rnsArticleMasters.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsArticleMasterModalRef(component, new RnsArticleMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsArticleMasterModalRef(component: Component, rnsArticleMaster: RnsArticleMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsArticleMaster = rnsArticleMaster;
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

    rnsArticleSearchMasterModalRef(component: Component, rnsArticleMasters: RnsArticleMaster[]): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsArticleMasters = rnsArticleMasters;
        modalRef.componentInstance.searchArray = JSON.parse(JSON.stringify(rnsArticleMasters));
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
