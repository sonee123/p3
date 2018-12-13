import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationVariant } from './rns-quotation-variant.model';
import { RnsQuotationVariantPopupService } from './rns-quotation-variant-popup.service';
import { RnsQuotationVariantService } from './rns-quotation-variant.service';

@Component({
    selector: 'jhi-rns-quotation-variant-delete-dialog',
    templateUrl: './rns-quotation-variant-delete-dialog.component.html'
})
export class RnsQuotationVariantDeleteDialogComponent {
    rnsQuotationVariant: RnsQuotationVariant;

    constructor(
        private rnsQuotationVariantService: RnsQuotationVariantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationVariantService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationVariantListModification',
                content: 'Deleted an rnsQuotationVariant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-variant-delete-popup',
    template: ''
})
export class RnsQuotationVariantDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationVariantPopupService: RnsQuotationVariantPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationVariantPopupService.open(RnsQuotationVariantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
