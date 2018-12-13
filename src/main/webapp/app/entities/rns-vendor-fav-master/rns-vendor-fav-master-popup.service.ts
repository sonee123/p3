import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RnsVendorFavMaster } from './rns-vendor-fav-master.model';
import { RnsVendorFavMasterService } from './rns-vendor-fav-master.service';

@Injectable()
export class RnsVendorFavMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsVendorFavMaster: RnsVendorFavMaster;

    constructor(private modalService: NgbModal, private router: Router, private rnsVendorFavMasterService: RnsVendorFavMasterService) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.rnsVendorFavMasterService.find(id).subscribe(rnsVendorFavMaster => {
                    this.rnsVendorFavMaster = rnsVendorFavMaster.body;
                    if (this.rnsVendorFavMaster.createdDate) {
                        this.rnsVendorFavMaster.createdDate = {
                            year: this.rnsVendorFavMaster.createdDate.getFullYear(),
                            month: this.rnsVendorFavMaster.createdDate.getMonth() + 1,
                            day: this.rnsVendorFavMaster.createdDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.rnsVendorFavMasterModalRef(component, this.rnsVendorFavMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsVendorFavMasterModalRef(component, new RnsVendorFavMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsVendorFavMasterModalRef(component: Component, rnsVendorFavMaster: RnsVendorFavMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsVendorFavMaster = rnsVendorFavMaster;
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
