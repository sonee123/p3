import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsEmpMaster } from './rns-emp-master.model';
import { RnsEmpMasterService } from './rns-emp-master.service';

@Injectable()
export class RnsEmpMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsEmpMasterService: RnsEmpMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsEmpMasterService.find(id).subscribe(rnsEmpMaster => {
                    this.ngbModalRef = this.rnsEmpMasterModalRef(component, rnsEmpMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsEmpMasterModalRef(component, new RnsEmpMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsEmpMasterModalRef(component: Component, rnsEmpMaster: RnsEmpMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsEmpMaster = rnsEmpMaster;
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
