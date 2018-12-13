import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsDelPlaceMaster } from './rns-del-place-master.model';
import { RnsDelPlaceMasterPopupService } from './rns-del-place-master-popup.service';
import { RnsDelPlaceMasterService } from './rns-del-place-master.service';

@Component({
    selector: 'jhi-rns-del-place-master-delete-dialog',
    templateUrl: './rns-del-place-master-delete-dialog.component.html'
})
export class RnsDelPlaceMasterDeleteDialogComponent {
    rnsDelPlaceMaster: RnsDelPlaceMaster;

    constructor(
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsDelPlaceMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsDelPlaceMasterListModification',
                content: 'Deleted an rnsDelPlaceMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-del-place-master-delete-popup',
    template: ''
})
export class RnsDelPlaceMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsDelPlaceMasterPopupService: RnsDelPlaceMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsDelPlaceMasterPopupService.open(RnsDelPlaceMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
