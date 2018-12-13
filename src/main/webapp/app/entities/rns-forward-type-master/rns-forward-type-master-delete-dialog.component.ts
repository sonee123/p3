import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsForwardTypeMaster } from './rns-forward-type-master.model';
import { RnsForwardTypeMasterPopupService } from './rns-forward-type-master-popup.service';
import { RnsForwardTypeMasterService } from './rns-forward-type-master.service';

@Component({
    selector: 'jhi-rns-forward-type-master-delete-dialog',
    templateUrl: './rns-forward-type-master-delete-dialog.component.html'
})
export class RnsForwardTypeMasterDeleteDialogComponent {
    rnsForwardTypeMaster: RnsForwardTypeMaster;

    constructor(
        private rnsForwardTypeMasterService: RnsForwardTypeMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsForwardTypeMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsForwardTypeMasterListModification',
                content: 'Deleted an rnsForwardTypeMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-forward-type-master-delete-popup',
    template: ''
})
export class RnsForwardTypeMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsForwardTypeMasterPopupService: RnsForwardTypeMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsForwardTypeMasterPopupService.open(RnsForwardTypeMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
