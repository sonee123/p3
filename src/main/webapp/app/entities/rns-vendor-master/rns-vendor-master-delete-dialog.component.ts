import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorMaster } from './rns-vendor-master.model';
import { RnsVendorMasterPopupService } from './rns-vendor-master-popup.service';
import { RnsVendorMasterService } from './rns-vendor-master.service';

@Component({
    selector: 'jhi-rns-vendor-master-delete-dialog',
    templateUrl: './rns-vendor-master-delete-dialog.component.html'
})
export class RnsVendorMasterDeleteDialogComponent {
    rnsVendorMaster: RnsVendorMaster;

    constructor(
        private rnsVendorMasterService: RnsVendorMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsVendorMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsVendorMasterListModification',
                content: 'Deleted an rnsVendorMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-vendor-master-delete-popup',
    template: ''
})
export class RnsVendorMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorMasterPopupService: RnsVendorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsVendorMasterPopupService.open(RnsVendorMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
