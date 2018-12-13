import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsUomMaster } from './rns-uom-master.model';
import { RnsUomMasterPopupService } from './rns-uom-master-popup.service';
import { RnsUomMasterService } from './rns-uom-master.service';

@Component({
    selector: 'jhi-rns-uom-master-delete-dialog',
    templateUrl: './rns-uom-master-delete-dialog.component.html'
})
export class RnsUomMasterDeleteDialogComponent {
    rnsUomMaster: RnsUomMaster;

    constructor(
        private rnsUomMasterService: RnsUomMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsUomMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsUomMasterListModification',
                content: 'Deleted an rnsUomMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-uom-master-delete-popup',
    template: ''
})
export class RnsUomMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUomMasterPopupService: RnsUomMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsUomMasterPopupService.open(RnsUomMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
