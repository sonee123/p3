import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsFileUpload } from './rns-file-upload.model';
import { RnsFileUploadPopupService } from './rns-file-upload-popup.service';
import { RnsFileUploadService } from './rns-file-upload.service';

@Component({
    selector: 'jhi-rns-file-upload-delete-dialog',
    templateUrl: './rns-file-upload-delete-dialog.component.html'
})
export class RnsFileUploadDeleteDialogComponent {
    rnsFileUpload: RnsFileUpload;

    constructor(
        private rnsFileUploadService: RnsFileUploadService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsFileUploadService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsFileUploadListModification',
                content: 'Deleted an rnsFileUpload'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-file-upload-delete-popup',
    template: ''
})
export class RnsFileUploadDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsFileUploadPopupService: RnsFileUploadPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsFileUploadPopupService.open(RnsFileUploadDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
