import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsDelTermsMaster } from './rns-del-terms-master.model';
import { RnsDelTermsMasterPopupService } from './rns-del-terms-master-popup.service';
import { RnsDelTermsMasterService } from './rns-del-terms-master.service';

@Component({
    selector: 'jhi-rns-del-terms-master-delete-dialog',
    templateUrl: './rns-del-terms-master-delete-dialog.component.html'
})
export class RnsDelTermsMasterDeleteDialogComponent {
    rnsDelTermsMaster: RnsDelTermsMaster;

    constructor(
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsDelTermsMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsDelTermsMasterListModification',
                content: 'Deleted an rnsDelTermsMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-del-terms-master-delete-popup',
    template: ''
})
export class RnsDelTermsMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsDelTermsMasterPopupService: RnsDelTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsDelTermsMasterPopupService.open(RnsDelTermsMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
