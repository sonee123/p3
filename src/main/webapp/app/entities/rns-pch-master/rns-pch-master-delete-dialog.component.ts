import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsPchMaster } from './rns-pch-master.model';
import { RnsPchMasterPopupService } from './rns-pch-master-popup.service';
import { RnsPchMasterService } from './rns-pch-master.service';

@Component({
    selector: 'jhi-rns-pch-master-delete-dialog',
    templateUrl: './rns-pch-master-delete-dialog.component.html'
})
export class RnsPchMasterDeleteDialogComponent {

    rnsPchMaster: RnsPchMaster;

    constructor(
        private rnsPchMasterService: RnsPchMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsPchMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsPchMasterListModification',
                content: 'Deleted an rnsPchMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-pch-master-delete-popup',
    template: ''
})
export class RnsPchMasterDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rnsPchMasterPopupService: RnsPchMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsPchMasterPopupService
                .open(RnsPchMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
