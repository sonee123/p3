import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsForwardTypeMaster } from './rns-forward-type-master.model';
import { RnsForwardTypeMasterService } from './rns-forward-type-master.service';

@Injectable()
export class RnsForwardTypeMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsForwardTypeMasterService: RnsForwardTypeMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsForwardTypeMasterService.find(id).subscribe(rnsForwardTypeMaster => {
                    this.ngbModalRef = this.rnsForwardTypeMasterModalRef(component, rnsForwardTypeMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsForwardTypeMasterModalRef(component, new RnsForwardTypeMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsForwardTypeMasterModalRef(component: Component, rnsForwardTypeMaster: RnsForwardTypeMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsForwardTypeMaster = rnsForwardTypeMaster;
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
