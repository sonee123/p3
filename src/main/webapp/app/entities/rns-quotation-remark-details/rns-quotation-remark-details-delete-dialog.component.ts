import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { RnsQuotationRemarkDetailsPopupService } from './rns-quotation-remark-details-popup.service';
import { RnsQuotationRemarkDetailsService } from './rns-quotation-remark-details.service';

@Component({
    selector: 'jhi-rns-quotation-remark-details-delete-dialog',
    templateUrl: './rns-quotation-remark-details-delete-dialog.component.html'
})
export class RnsQuotationRemarkDetailsDeleteDialogComponent {
    rnsQuotationRemarkDetails: RnsQuotationRemarkDetails;

    constructor(
        private rnsQuotationRemarkDetailsService: RnsQuotationRemarkDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationRemarkDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationRemarkDetailsListModification',
                content: 'Deleted an rnsQuotationRemarkDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-remark-details-delete-popup',
    template: ''
})
export class RnsQuotationRemarkDetailsDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationRemarkDetailsPopupService: RnsQuotationRemarkDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationRemarkDetailsPopupService.open(RnsQuotationRemarkDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
