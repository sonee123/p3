import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsSourceTeamMaster } from './rns-source-team-master.model';
import { RnsSourceTeamMasterPopupService } from './rns-source-team-master-popup.service';
import { RnsSourceTeamMasterService } from './rns-source-team-master.service';

@Component({
    selector: 'jhi-rns-source-team-master-delete-dialog',
    templateUrl: './rns-source-team-master-delete-dialog.component.html'
})
export class RnsSourceTeamMasterDeleteDialogComponent {
    rnsSourceTeamMaster: RnsSourceTeamMaster;

    constructor(
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsSourceTeamMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsSourceTeamMasterListModification',
                content: 'Deleted an rnsSourceTeamMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-source-team-master-delete-popup',
    template: ''
})
export class RnsSourceTeamMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsSourceTeamMasterPopupService: RnsSourceTeamMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsSourceTeamMasterPopupService.open(RnsSourceTeamMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
