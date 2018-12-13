import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsDelTermsMaster } from './rns-del-terms-master.model';
import { RnsDelTermsMasterService } from './rns-del-terms-master.service';

@Injectable()
export class RnsDelTermsMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsDelTermsMasterService: RnsDelTermsMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsDelTermsMasterService.find(id).subscribe(rnsDelTermsMaster => {
                    this.ngbModalRef = this.rnsDelTermsMasterModalRef(component, rnsDelTermsMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsDelTermsMasterModalRef(component, new RnsDelTermsMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsDelTermsMasterModalRef(component: Component, rnsDelTermsMaster: RnsDelTermsMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsDelTermsMaster = rnsDelTermsMaster;
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
