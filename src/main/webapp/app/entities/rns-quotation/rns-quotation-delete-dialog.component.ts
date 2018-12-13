import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotation } from './rns-quotation.model';
import { RnsQuotationPopupService } from './rns-quotation-popup.service';
import { RnsQuotationService } from './rns-quotation.service';

@Component({
    selector: 'jhi-rns-quotation-delete-dialog',
    templateUrl: './rns-quotation-delete-dialog.component.html'
})
export class RnsQuotationDeleteDialogComponent {
    rnsQuotation: RnsQuotation;

    constructor(
        private rnsQuotationService: RnsQuotationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationListModification',
                content: 'Deleted an rnsQuotation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-delete-popup',
    template: ''
})
export class RnsQuotationDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationPopupService: RnsQuotationPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationPopupService.open(RnsQuotationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
