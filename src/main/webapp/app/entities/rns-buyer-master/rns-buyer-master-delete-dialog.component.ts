import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsBuyerMaster } from './rns-buyer-master.model';
import { RnsBuyerMasterPopupService } from './rns-buyer-master-popup.service';
import { RnsBuyerMasterService } from './rns-buyer-master.service';

@Component({
    selector: 'jhi-rns-buyer-master-delete-dialog',
    templateUrl: './rns-buyer-master-delete-dialog.component.html'
})
export class RnsBuyerMasterDeleteDialogComponent {
    rnsBuyerMaster: RnsBuyerMaster;

    constructor(
        private rnsBuyerMasterService: RnsBuyerMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsBuyerMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsBuyerMasterListModification',
                content: 'Deleted an rnsBuyerMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-buyer-master-delete-popup',
    template: ''
})
export class RnsBuyerMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsBuyerMasterPopupService: RnsBuyerMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsBuyerMasterPopupService.open(RnsBuyerMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
