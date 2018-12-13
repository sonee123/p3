import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsUpchargeMaster } from './rns-upcharge-master.model';
import { RnsUpchargeMasterService } from './rns-upcharge-master.service';

@Injectable()
export class RnsUpchargeMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsUpchargeMaster: RnsUpchargeMaster;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsUpchargeMasterService: RnsUpchargeMasterService
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
                this.rnsUpchargeMasterService.find(id).subscribe(rnsUpchargeMaster => {
                    this.rnsUpchargeMaster = rnsUpchargeMaster.body;
                    this.rnsUpchargeMaster.createdDate = this.datePipe.transform(this.rnsUpchargeMaster.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsUpchargeMasterModalRef(component, this.rnsUpchargeMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsUpchargeMasterModalRef(component, new RnsUpchargeMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsUpchargeMasterModalRef(component: Component, rnsUpchargeMaster: RnsUpchargeMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsUpchargeMaster = rnsUpchargeMaster;
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
