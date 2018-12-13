import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsSourceTeamDtl } from './rns-source-team-dtl.model';
import { RnsSourceTeamDtlPopupService } from './rns-source-team-dtl-popup.service';
import { RnsSourceTeamDtlService } from './rns-source-team-dtl.service';

@Component({
    selector: 'jhi-rns-source-team-dtl-delete-dialog',
    templateUrl: './rns-source-team-dtl-delete-dialog.component.html'
})
export class RnsSourceTeamDtlDeleteDialogComponent {
    rnsSourceTeamDtl: RnsSourceTeamDtl;

    constructor(
        private rnsSourceTeamDtlService: RnsSourceTeamDtlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsSourceTeamDtlService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsSourceTeamDtlListModification',
                content: 'Deleted an rnsSourceTeamDtl'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-source-team-dtl-delete-popup',
    template: ''
})
export class RnsSourceTeamDtlDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsSourceTeamDtlPopupService: RnsSourceTeamDtlPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsSourceTeamDtlPopupService.open(RnsSourceTeamDtlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
