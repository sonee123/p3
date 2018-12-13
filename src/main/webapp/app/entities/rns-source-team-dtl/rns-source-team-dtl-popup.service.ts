import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RnsSourceTeamDtl } from './rns-source-team-dtl.model';
import { RnsSourceTeamDtlService } from './rns-source-team-dtl.service';

@Injectable()
export class RnsSourceTeamDtlPopupService {
    private ngbModalRef: NgbModalRef;
    private rnsSourceTeamDtl: RnsSourceTeamDtl;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsSourceTeamDtlService: RnsSourceTeamDtlService
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
                this.rnsSourceTeamDtlService.find(id).subscribe(rnsSourceTeamDtl => {
                    this.rnsSourceTeamDtl = rnsSourceTeamDtl.body;
                    this.rnsSourceTeamDtl.createdDate = this.datePipe.transform(this.rnsSourceTeamDtl.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rnsSourceTeamDtlModalRef(component, this.rnsSourceTeamDtl);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rnsSourceTeamDtlModalRef(component, new RnsSourceTeamDtl());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rnsSourceTeamDtlModalRef(component: Component, rnsSourceTeamDtl: RnsSourceTeamDtl): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsSourceTeamDtl = rnsSourceTeamDtl;
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
