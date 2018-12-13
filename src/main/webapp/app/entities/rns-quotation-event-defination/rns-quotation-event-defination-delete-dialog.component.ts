import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationEventDefination } from './rns-quotation-event-defination.model';
import { RnsQuotationEventDefinationPopupService } from './rns-quotation-event-defination-popup.service';
import { RnsQuotationEventDefinationService } from './rns-quotation-event-defination.service';

@Component({
    selector: 'jhi-rns-quotation-event-defination-delete-dialog',
    templateUrl: './rns-quotation-event-defination-delete-dialog.component.html'
})
export class RnsQuotationEventDefinationDeleteDialogComponent {
    rnsQuotationEventDefination: RnsQuotationEventDefination;

    constructor(
        private rnsQuotationEventDefinationService: RnsQuotationEventDefinationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationEventDefinationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationEventDefinationListModification',
                content: 'Deleted an rnsQuotationEventDefination'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-event-defination-delete-popup',
    template: ''
})
export class RnsQuotationEventDefinationDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationEventDefinationPopupService: RnsQuotationEventDefinationPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationEventDefinationPopupService.open(RnsQuotationEventDefinationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
