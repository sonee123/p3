import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsTypeMaster } from './rns-type-master.model';
import { RnsTypeMasterPopupService } from './rns-type-master-popup.service';
import { RnsTypeMasterService } from './rns-type-master.service';

@Component({
    selector: 'jhi-rns-type-master-delete-dialog',
    templateUrl: './rns-type-master-delete-dialog.component.html'
})
export class RnsTypeMasterDeleteDialogComponent {
    rnsTypeMaster: RnsTypeMaster;

    constructor(
        private rnsTypeMasterService: RnsTypeMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsTypeMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsTypeMasterListModification',
                content: 'Deleted an rnsTypeMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-type-master-delete-popup',
    template: ''
})
export class RnsTypeMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsTypeMasterPopupService: RnsTypeMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsTypeMasterPopupService.open(RnsTypeMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
