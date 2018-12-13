import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { IRnsFileUpload, RnsFileUpload } from './rns-file-upload.model';
import { RnsFileUploadService } from './rns-file-upload.service';
import { HttpResponse } from '@angular/common/http';
@Injectable()
export class RnsFileUploadPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rnsFileUploadService: RnsFileUploadService
    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any, type?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }
            if (id && type) {
                const rnsFileUpload = new RnsFileUpload();
                rnsFileUpload.variantId = id;
                rnsFileUpload.uploadType = type;
                this.rnsFileUploadService.findbyType(id, type).subscribe((rnsFileUploads: HttpResponse<IRnsFileUpload[]>) => {
                    this.ngbModalRef = this.rnsFileUploadModalRef(component, rnsFileUploads.body, rnsFileUpload);
                    resolve(this.ngbModalRef);
                });
            }
        });
    }

    new(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }
            const rnsFileUpload = new RnsFileUpload();
            rnsFileUpload.id = id;
            const rnsFileUploads: any = [];
            setTimeout(() => {
                this.ngbModalRef = this.rnsFileUploadModalRef(component, rnsFileUploads, rnsFileUpload);
                resolve(this.ngbModalRef);
            }, 0);
        });
    }

    rnsFileUploadModalRef(component: Component, rnsFileUploads: RnsFileUpload[], rnsFileUpload: RnsFileUpload): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.rnsFileUploads = rnsFileUploads;
        modalRef.componentInstance.rnsFileUpload = rnsFileUpload;
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
