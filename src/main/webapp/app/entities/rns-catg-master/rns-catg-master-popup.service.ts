import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsCatgMaster } from './rns-catg-master.model';
import { RnsCatgMasterService } from './rns-catg-master.service';
import { RnsCatgUser } from './rns-catg-user.model';

@Injectable()
export class RnsCatgMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsCatgMasterService: RnsCatgMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsCatgMasterService.find(id).subscribe(rnsCatgMaster => {
                    this.ngbModalRef = this.rnsCatgMasterModalRef(component, rnsCatgMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsCatgMasterModalRef(component, new RnsCatgMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    openpost(component: Component, login?: string | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (login) {
                this.rnsCatgMasterService.findPost(login).subscribe(rnsCatgUser => {
                    this.ngbModalRef = this.rnsCatgMasterModalRefUser(component, rnsCatgUser.body);
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    rnsCatgMasterModalRef(component: Component, rnsCatgMaster: RnsCatgMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsCatgMaster = rnsCatgMaster;
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

    rnsCatgMasterModalRefUser(component: Component, rnsCatgUser: RnsCatgUser): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsCatgUser = rnsCatgUser;
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
