import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRefrDetails } from './rns-refr-details.model';
import { RnsRefrDetailsPopupService } from './rns-refr-details-popup.service';
import { RnsRefrDetailsService } from './rns-refr-details.service';

@Component({
    selector: 'jhi-rns-refr-details-delete-dialog',
    templateUrl: './rns-refr-details-delete-dialog.component.html'
})
export class RnsRefrDetailsDeleteDialogComponent {
    rnsRefrDetails: RnsRefrDetails;

    constructor(
        private rnsRefrDetailsService: RnsRefrDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsRefrDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsRefrDetailsListModification',
                content: 'Deleted an rnsRefrDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-refr-details-delete-popup',
    template: ''
})
export class RnsRefrDetailsDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRefrDetailsPopupService: RnsRefrDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsRefrDetailsPopupService.open(RnsRefrDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
