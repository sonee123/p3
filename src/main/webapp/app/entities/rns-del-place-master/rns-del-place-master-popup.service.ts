import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsDelPlaceMaster } from './rns-del-place-master.model';
import { RnsDelPlaceMasterService } from './rns-del-place-master.service';

@Injectable()
export class RnsDelPlaceMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsDelPlaceMasterService: RnsDelPlaceMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsDelPlaceMasterService.find(id).subscribe(rnsDelPlaceMaster => {
                    this.ngbModalRef = this.rnsDelPlaceMasterModalRef(component, rnsDelPlaceMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsDelPlaceMasterModalRef(component, new RnsDelPlaceMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsDelPlaceMasterModalRef(component: Component, rnsDelPlaceMaster: RnsDelPlaceMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsDelPlaceMaster = rnsDelPlaceMaster;
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
