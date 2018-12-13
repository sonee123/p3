import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsSourceTeamMaster } from './rns-source-team-master.model';
import { RnsSourceTeamMasterService } from './rns-source-team-master.service';

@Injectable()
export class RnsSourceTeamMasterPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsSourceTeamMaster: RnsSourceTeamMaster;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService
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
                this.rnsSourceTeamMasterService.find(id).subscribe(rnsSourceTeamMaster => {
                    this.rnsSourceTeamMaster = rnsSourceTeamMaster.body;
                    this.rnsSourceTeamMaster.createdDate = this.datePipe.transform(
                        this.rnsSourceTeamMaster.createdDate,
                        'yyyy-MM-ddTHH:mm:ss'
                    );
                    this.ngbModalRef = this.rnsSourceTeamMasterModalRef(component, this.rnsSourceTeamMaster);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsSourceTeamMasterModalRef(component, new RnsSourceTeamMaster());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsSourceTeamMasterModalRef(component: Component, rnsSourceTeamMaster: RnsSourceTeamMaster): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsSourceTeamMaster = rnsSourceTeamMaster;
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
