import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IRnsFileUpload, RnsFileUpload } from './rns-file-upload.model';
import { RnsFileUploadService } from './rns-file-upload.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Observable, Subscription } from 'rxjs';
import { RnsFileUploadPopupService } from 'app/entities/rns-file-upload/rns-file-upload-popup.service';
import * as FileSaver from 'file-saver';
import { Principal } from 'app/core';
import { NotifierService } from 'angular-notifier';
import { RnsQuotationVariantService } from 'app/entities/rns-quotation-variant';
import { RnsQuotation } from 'app/entities/rns-quotation';

@Component({
    selector: 'jhi-rns-file-upload-dialog',
    templateUrl: './rns-file-upload-dialog.component.html'
})
export class RnsFileUploadDialogComponent implements OnInit, OnDestroy {
    rnsFileUpload: IRnsFileUpload;
    rnsFileUploads: IRnsFileUpload[];
    selectedFiles: FileList;
    currentFileUpload: File;
    isSaving: boolean;
    isWait: boolean;
    currentAccount: any;
    eventSubscriber: Subscription;
    rnsQuotation: RnsQuotation;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsFileUploadService: RnsFileUploadService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private notifier: NotifierService,
        private rnsQuotationVariantService: RnsQuotationVariantService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsFileUploads();
        this.isSaving = false;
        this.rnsQuotationVariantService.find(this.rnsFileUpload.variantId).subscribe(variant => {
            this.rnsQuotation = variant.body.quotation;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.selectedFiles != null) {
            this.currentFileUpload = this.selectedFiles.item(0);
        } else {
            this.notifier.notify('error', 'Please choose file!!!');
            this.isSaving = false;
            return false;
        }
        this.rnsFileUpload.id = null;
        this.subscribeToSaveResponse(this.rnsFileUploadService.create(this.rnsFileUpload, this.currentFileUpload));
    }

    selectFile(event) {
        this.selectedFiles = event.target.files;
        console.log(this.selectedFiles);
    }

    loadAll() {
        this.rnsFileUploadService.findbyType(this.rnsFileUpload.variantId, this.rnsFileUpload.uploadType).subscribe(
            (res: HttpResponse<IRnsFileUpload[]>) => {
                this.rnsFileUploads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    download(rnsFileUpload: RnsFileUpload): any {
        this.isWait = true;
        this.rnsFileUploadService.download(rnsFileUpload.id).subscribe(
            res => {
                FileSaver.saveAs(res, rnsFileUpload.displayName);
                this.isWait = false;
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    delete(rnsFileUpload: RnsFileUpload): any {
        if (confirm('Do you want to delete?')) {
            this.rnsFileUploadService.delete(rnsFileUpload.id).subscribe(
                res => {
                    this.loadAll();
                    this.notifier.notify('success', 'Delete successfully');
                },
                res => {
                    this.onError(res.message);
                }
            );
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRnsFileUpload[]>>) {
        result.subscribe((res: HttpResponse<IRnsFileUpload[]>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onError(res));
    }

    private onSaveSuccess(result: HttpResponse<IRnsFileUpload[]>) {
        this.eventManager.broadcast({ name: 'rnsFileUploadListModification', content: 'OK' });
        this.rnsFileUploads = result.body;
        this.notifier.notify('success', 'Upload successfully');
        this.isSaving = false;
    }

    registerChangeInRnsFileUploads() {
        this.eventSubscriber = this.eventManager.subscribe('rnsFileUploadListModification', response => this.loadAll());
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.isSaving = false;
        this.jhiAlertService.error(error.message, null, null);
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }
}

@Component({
    selector: 'jhi-rns-file-upload-popup',
    template: ''
})
export class RnsFileUploadPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsFileUploadPopupService: RnsFileUploadPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id'] && params['type']) {
                this.rnsFileUploadPopupService.open(RnsFileUploadDialogComponent as Component, params['id'], params['type']);
            } else {
                this.rnsFileUploadPopupService.open(RnsFileUploadDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
