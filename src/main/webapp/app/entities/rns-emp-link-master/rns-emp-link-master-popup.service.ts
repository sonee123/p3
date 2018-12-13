import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsEmpLinkMaster } from './rns-emp-link-master.model';
import { RnsEmpLinkMasterService } from './rns-emp-link-master.service';

@Injectable()
export class RnsEmpLinkMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsEmpLinkMasterService: RnsEmpLinkMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsEmpLinkMasterService.find(id).subscribe(rnsEmpLinkMaster => {
                    this.ngbModalRef = this.rnsEmpLinkMasterModalRef(component, rnsEmpLinkMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsEmpLinkMasterModalRef(component, new RnsEmpLinkMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsEmpLinkMasterModalRef(component: Component, rnsEmpLinkMaster: RnsEmpLinkMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsEmpLinkMaster = rnsEmpLinkMaster;
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
