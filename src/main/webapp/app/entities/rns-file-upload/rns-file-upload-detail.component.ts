import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsFileUpload } from './rns-file-upload.model';
import { RnsFileUploadService } from './rns-file-upload.service';

@Component({
    selector: 'jhi-rns-file-upload-detail',
    templateUrl: './rns-file-upload-detail.component.html'
})
export class RnsFileUploadDetailComponent implements OnInit, OnDestroy {
    rnsFileUpload: RnsFileUpload;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    private isWait: boolean;

    constructor(private eventManager: JhiEventManager, private rnsFileUploadService: RnsFileUploadService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isWait = false;
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsFileUploads();
    }

    load(id) {
        this.rnsFileUploadService.find(id).subscribe(rnsFileUpload => {
            this.rnsFileUpload = rnsFileUpload.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsFileUploads() {
        this.eventSubscriber = this.eventManager.subscribe('rnsFileUploadListModification', response => this.load(this.rnsFileUpload.id));
    }
}
