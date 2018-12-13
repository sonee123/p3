import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRnsFileUpload } from './rns-file-upload.model';
import { Principal } from 'app/core';
import { RnsFileUploadService } from './rns-file-upload.service';

@Component({
    selector: 'jhi-rns-file-upload',
    templateUrl: './rns-file-upload.component.html'
})
export class RnsFileUploadComponent implements OnInit, OnDestroy {
    rnsFileUploads: IRnsFileUpload[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsFileUploadService: RnsFileUploadService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsFileUploadService.query().subscribe(
            (res: HttpResponse<IRnsFileUpload[]>) => {
                this.rnsFileUploads = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsFileUploads();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRnsFileUpload) {
        return item.id;
    }
    registerChangeInRnsFileUploads() {
        this.eventSubscriber = this.eventManager.subscribe('rnsFileUploadListModification', response => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
