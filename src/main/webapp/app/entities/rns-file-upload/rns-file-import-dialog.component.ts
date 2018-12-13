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
import { RnsQuotationService } from 'app/entities/rns-quotation';
import { ComParentChildService } from 'app/rnsmain/com-parent-child.service';
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-rns-file-import-dialog',
    templateUrl: './rns-file-import-dialog.component.html'
})
export class RnsFileImportDialogComponent implements OnInit, OnDestroy {
    rnsFileUpload: IRnsFileUpload;
    rnsFileUploads: any;
    selectedFiles: FileList;
    currentFileUpload: File;
    isSaving: boolean;
    isWait: boolean;
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsQuotationService: RnsQuotationService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private comparentchildservice: ComParentChildService,
        private notifier: NotifierService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsFileUploads();
        this.isSaving = false;
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
        this.subscribeToSaveResponse(this.rnsQuotationService.import(this.rnsFileUpload, this.currentFileUpload));
    }

    selectFile(event) {
        this.selectedFiles = event.target.files;
        console.log(this.selectedFiles);
    }

    loadAll() {}

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRnsFileUpload[]>>) {
        result.subscribe((res: HttpResponse<IRnsFileUpload[]>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onError(res));
    }

    private onSaveSuccess(result: HttpResponse<IRnsFileUpload[]>) {
        this.eventManager.broadcast({ name: 'rnsFileUploadListModification', content: 'OK' });
        this.comparentchildservice.publish('call-variant-parent');
        this.isSaving = false;
        this.notifier.notify('success', 'Upload successfully');
        this.activeModal.dismiss();
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
    selector: 'jhi-rns-file-import-popup',
    template: ''
})
export class RnsFileImportPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsFileUploadPopupService: RnsFileUploadPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsFileUploadPopupService.new(RnsFileImportDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
