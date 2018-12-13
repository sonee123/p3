import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsSectorMaster } from './rns-sector-master.model';
import { RnsSectorMasterPopupService } from './rns-sector-master-popup.service';
import { RnsSectorMasterService } from './rns-sector-master.service';

@Component({
    selector: 'jhi-rns-sector-master-delete-dialog',
    templateUrl: './rns-sector-master-delete-dialog.component.html'
})
export class RnsSectorMasterDeleteDialogComponent {
    rnsSectorMaster: RnsSectorMaster;

    constructor(
        private rnsSectorMasterService: RnsSectorMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsSectorMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsSectorMasterListModification',
                content: 'Deleted an rnsSectorMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-sector-master-delete-popup',
    template: ''
})
export class RnsSectorMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsSectorMasterPopupService: RnsSectorMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsSectorMasterPopupService.open(RnsSectorMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
