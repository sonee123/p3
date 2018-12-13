import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsTaxTermsMaster } from './rns-tax-terms-master.model';
import { RnsTaxTermsMasterService } from './rns-tax-terms-master.service';

@Injectable()
export class RnsTaxTermsMasterPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(private modalService: NgbModal, private router: Router, private rnsTaxTermsMasterService: RnsTaxTermsMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsTaxTermsMasterService.find(id).subscribe(rnsTaxTermsMaster => {
                    this.ngbModalRef = this.rnsTaxTermsMasterModalRef(component, rnsTaxTermsMaster.body);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsTaxTermsMasterModalRef(component, new RnsTaxTermsMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsTaxTermsMasterModalRef(component: Component, rnsTaxTermsMaster: RnsTaxTermsMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsTaxTermsMaster = rnsTaxTermsMaster;
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
