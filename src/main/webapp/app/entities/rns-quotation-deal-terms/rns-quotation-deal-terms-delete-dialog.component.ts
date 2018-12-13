import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationDealTerms } from './rns-quotation-deal-terms.model';
import { RnsQuotationDealTermsPopupService } from './rns-quotation-deal-terms-popup.service';
import { RnsQuotationDealTermsService } from './rns-quotation-deal-terms.service';

@Component({
    selector: 'jhi-rns-quotation-deal-terms-delete-dialog',
    templateUrl: './rns-quotation-deal-terms-delete-dialog.component.html'
})
export class RnsQuotationDealTermsDeleteDialogComponent {
    rnsQuotationDealTerms: RnsQuotationDealTerms;

    constructor(
        private rnsQuotationDealTermsService: RnsQuotationDealTermsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationDealTermsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationDealTermsListModification',
                content: 'Deleted an rnsQuotationDealTerms'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-deal-terms-delete-popup',
    template: ''
})
export class RnsQuotationDealTermsDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationDealTermsPopupService: RnsQuotationDealTermsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationDealTermsPopupService.open(RnsQuotationDealTermsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
