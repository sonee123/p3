import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsPayTermsMaster } from './rns-pay-terms-master.model';
import { RnsPayTermsMasterPopupService } from './rns-pay-terms-master-popup.service';
import { RnsPayTermsMasterService } from './rns-pay-terms-master.service';

@Component({
    selector: 'jhi-rns-pay-terms-master-delete-dialog',
    templateUrl: './rns-pay-terms-master-delete-dialog.component.html'
})
export class RnsPayTermsMasterDeleteDialogComponent {
    rnsPayTermsMaster: RnsPayTermsMaster;

    constructor(
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsPayTermsMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsPayTermsMasterListModification',
                content: 'Deleted an rnsPayTermsMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-pay-terms-master-delete-popup',
    template: ''
})
export class RnsPayTermsMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsPayTermsMasterPopupService: RnsPayTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsPayTermsMasterPopupService.open(RnsPayTermsMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
