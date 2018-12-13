import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsEmpLinkMaster } from './rns-emp-link-master.model';
import { RnsEmpLinkMasterPopupService } from './rns-emp-link-master-popup.service';
import { RnsEmpLinkMasterService } from './rns-emp-link-master.service';

@Component({
    selector: 'jhi-rns-emp-link-master-delete-dialog',
    templateUrl: './rns-emp-link-master-delete-dialog.component.html'
})
export class RnsEmpLinkMasterDeleteDialogComponent {
    rnsEmpLinkMaster: RnsEmpLinkMaster;

    constructor(
        private rnsEmpLinkMasterService: RnsEmpLinkMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsEmpLinkMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsEmpLinkMasterListModification',
                content: 'Deleted an rnsEmpLinkMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-emp-link-master-delete-popup',
    template: ''
})
export class RnsEmpLinkMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsEmpLinkMasterPopupService: RnsEmpLinkMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsEmpLinkMasterPopupService.open(RnsEmpLinkMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
