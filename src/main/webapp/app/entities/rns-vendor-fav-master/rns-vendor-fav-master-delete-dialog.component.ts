import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorFavMaster } from './rns-vendor-fav-master.model';
import { RnsVendorFavMasterPopupService } from './rns-vendor-fav-master-popup.service';
import { RnsVendorFavMasterService } from './rns-vendor-fav-master.service';

@Component({
    selector: 'jhi-rns-vendor-fav-master-delete-dialog',
    templateUrl: './rns-vendor-fav-master-delete-dialog.component.html'
})
export class RnsVendorFavMasterDeleteDialogComponent {
    rnsVendorFavMaster: RnsVendorFavMaster;

    constructor(
        private rnsVendorFavMasterService: RnsVendorFavMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsVendorFavMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsVendorFavMasterListModification',
                content: 'Deleted an rnsVendorFavMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-vendor-fav-master-delete-popup',
    template: ''
})
export class RnsVendorFavMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorFavMasterPopupService: RnsVendorFavMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsVendorFavMasterPopupService.open(RnsVendorFavMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
