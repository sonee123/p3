import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsRefrDetails } from './rns-refr-details.model';
import { RnsRefrDetailsService } from './rns-refr-details.service';

@Injectable()
export class RnsRefrDetailsPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsRefrDetails: RnsRefrDetails;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsRefrDetailsService: RnsRefrDetailsService
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
                this.rnsRefrDetailsService.find(id).subscribe(rnsRefrDetails => {
                    this.rnsRefrDetails = rnsRefrDetails.body;
                    this.rnsRefrDetails.createdDate = this.datePipe.transform(this.rnsRefrDetails.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.rnsRefrDetails.lastModifiedBy = this.datePipe.transform(this.rnsRefrDetails.lastModifiedBy, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsRefrDetailsModalRef(component, this.rnsRefrDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsRefrDetailsModalRef(component, new RnsRefrDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsRefrDetailsModalRef(component: Component, rnsRefrDetails: RnsRefrDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsRefrDetails = rnsRefrDetails;
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
