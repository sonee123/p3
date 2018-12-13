import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsTaxTermsMaster } from './rns-tax-terms-master.model';
import { RnsTaxTermsMasterPopupService } from './rns-tax-terms-master-popup.service';
import { RnsTaxTermsMasterService } from './rns-tax-terms-master.service';

@Component({
    selector: 'jhi-rns-tax-terms-master-delete-dialog',
    templateUrl: './rns-tax-terms-master-delete-dialog.component.html'
})
export class RnsTaxTermsMasterDeleteDialogComponent {
    rnsTaxTermsMaster: RnsTaxTermsMaster;

    constructor(
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsTaxTermsMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsTaxTermsMasterListModification',
                content: 'Deleted an rnsTaxTermsMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-tax-terms-master-delete-popup',
    template: ''
})
export class RnsTaxTermsMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsTaxTermsMasterPopupService: RnsTaxTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsTaxTermsMasterPopupService.open(RnsTaxTermsMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
