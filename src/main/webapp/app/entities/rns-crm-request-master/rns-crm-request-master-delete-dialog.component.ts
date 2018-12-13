import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsCrmRequestMaster } from './rns-crm-request-master.model';
import { RnsCrmRequestMasterPopupService } from './rns-crm-request-master-popup.service';
import { RnsCrmRequestMasterService } from './rns-crm-request-master.service';

@Component({
    selector: 'jhi-rns-crm-request-master-delete-dialog',
    templateUrl: './rns-crm-request-master-delete-dialog.component.html'
})
export class RnsCrmRequestMasterDeleteDialogComponent {
    rnsCrmRequestMaster: RnsCrmRequestMaster;

    constructor(
        private rnsCrmRequestMasterService: RnsCrmRequestMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsCrmRequestMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsCrmRequestMasterListModification',
                content: 'Deleted an rnsCrmRequestMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-crm-request-master-delete-popup',
    template: ''
})
export class RnsCrmRequestMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsCrmRequestMasterPopupService: RnsCrmRequestMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsCrmRequestMasterPopupService.open(RnsCrmRequestMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
