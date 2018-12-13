import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRefrMaster } from './rns-refr-master.model';
import { RnsRefrMasterPopupService } from './rns-refr-master-popup.service';
import { RnsRefrMasterService } from './rns-refr-master.service';

@Component({
    selector: 'jhi-rns-refr-master-delete-dialog',
    templateUrl: './rns-refr-master-delete-dialog.component.html'
})
export class RnsRefrMasterDeleteDialogComponent {
    rnsRefrMaster: RnsRefrMaster;

    constructor(
        private rnsRefrMasterService: RnsRefrMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsRefrMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsRefrMasterListModification',
                content: 'Deleted an rnsRefrMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-refr-master-delete-popup',
    template: ''
})
export class RnsRefrMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRefrMasterPopupService: RnsRefrMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsRefrMasterPopupService.open(RnsRefrMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
