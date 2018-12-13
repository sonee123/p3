import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsCatgMaster } from './rns-catg-master.model';
import { RnsCatgMasterPopupService } from './rns-catg-master-popup.service';
import { RnsCatgMasterService } from './rns-catg-master.service';

@Component({
    selector: 'jhi-rns-catg-master-delete-dialog',
    templateUrl: './rns-catg-master-delete-dialog.component.html'
})
export class RnsCatgMasterDeleteDialogComponent {
    rnsCatgMaster: RnsCatgMaster;

    constructor(
        private rnsCatgMasterService: RnsCatgMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsCatgMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsCatgMasterListModification',
                content: 'Deleted an rnsCatgMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-catg-master-delete-popup',
    template: ''
})
export class RnsCatgMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsCatgMasterPopupService: RnsCatgMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsCatgMasterPopupService.open(RnsCatgMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
