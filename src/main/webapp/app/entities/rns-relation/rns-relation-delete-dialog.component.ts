import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRelation } from './rns-relation.model';
import { RnsRelationPopupService } from './rns-relation-popup.service';
import { RnsRelationService } from './rns-relation.service';

@Component({
    selector: 'jhi-rns-relation-delete-dialog',
    templateUrl: './rns-relation-delete-dialog.component.html'
})
export class RnsRelationDeleteDialogComponent {
    rnsRelation: RnsRelation;

    constructor(
        private rnsRelationService: RnsRelationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsRelationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsRelationListModification',
                content: 'Deleted an rnsRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-relation-delete-popup',
    template: ''
})
export class RnsRelationDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRelationPopupService: RnsRelationPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsRelationPopupService.open(RnsRelationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
