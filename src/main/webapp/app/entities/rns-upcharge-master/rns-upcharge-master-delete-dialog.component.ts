import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsUpchargeMaster } from './rns-upcharge-master.model';
import { RnsUpchargeMasterPopupService } from './rns-upcharge-master-popup.service';
import { RnsUpchargeMasterService } from './rns-upcharge-master.service';

@Component({
    selector: 'jhi-rns-upcharge-master-delete-dialog',
    templateUrl: './rns-upcharge-master-delete-dialog.component.html'
})
export class RnsUpchargeMasterDeleteDialogComponent {
    rnsUpchargeMaster: RnsUpchargeMaster;

    constructor(
        private rnsUpchargeMasterService: RnsUpchargeMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsUpchargeMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsUpchargeMasterListModification',
                content: 'Deleted an rnsUpchargeMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-upcharge-master-delete-popup',
    template: ''
})
export class RnsUpchargeMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUpchargeMasterPopupService: RnsUpchargeMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsUpchargeMasterPopupService.open(RnsUpchargeMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
