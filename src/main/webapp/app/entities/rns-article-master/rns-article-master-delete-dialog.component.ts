import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsArticleMaster } from './rns-article-master.model';
import { RnsArticleMasterPopupService } from './rns-article-master-popup.service';
import { RnsArticleMasterService } from './rns-article-master.service';

@Component({
    selector: 'jhi-rns-article-master-delete-dialog',
    templateUrl: './rns-article-master-delete-dialog.component.html'
})
export class RnsArticleMasterDeleteDialogComponent {
    rnsArticleMaster: RnsArticleMaster;

    constructor(
        private rnsArticleMasterService: RnsArticleMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsArticleMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsArticleMasterListModification',
                content: 'Deleted an rnsArticleMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-article-master-delete-popup',
    template: ''
})
export class RnsArticleMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsArticleMasterPopupService: RnsArticleMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsArticleMasterPopupService.open(RnsArticleMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
