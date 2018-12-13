import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsPayTermsMaster } from './rns-pay-terms-master.model';
import { RnsPayTermsMasterService } from './rns-pay-terms-master.service';

@Injectable()
export class RnsPayTermsMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsPayTermsMasterService: RnsPayTermsMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsPayTermsMasterService.find(id).subscribe(rnsPayTermsMaster => {
                    this.ngbModalRef = this.rnsPayTermsMasterModalRef(component, rnsPayTermsMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsPayTermsMasterModalRef(component, new RnsPayTermsMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsPayTermsMasterModalRef(component: Component, rnsPayTermsMaster: RnsPayTermsMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsPayTermsMaster = rnsPayTermsMaster;
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
