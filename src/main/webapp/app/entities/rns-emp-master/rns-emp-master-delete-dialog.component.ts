import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsEmpMaster } from './rns-emp-master.model';
import { RnsEmpMasterPopupService } from './rns-emp-master-popup.service';
import { RnsEmpMasterService } from './rns-emp-master.service';

@Component({
    selector: 'jhi-rns-emp-master-delete-dialog',
    templateUrl: './rns-emp-master-delete-dialog.component.html'
})
export class RnsEmpMasterDeleteDialogComponent {
    rnsEmpMaster: RnsEmpMaster;

    constructor(
        private rnsEmpMasterService: RnsEmpMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsEmpMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsEmpMasterListModification',
                content: 'Deleted an rnsEmpMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-emp-master-delete-popup',
    template: ''
})
export class RnsEmpMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsEmpMasterPopupService: RnsEmpMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsEmpMasterPopupService.open(RnsEmpMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
