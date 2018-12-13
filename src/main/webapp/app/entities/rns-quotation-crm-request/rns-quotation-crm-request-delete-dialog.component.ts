import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationCrmRequest } from './rns-quotation-crm-request.model';
import { RnsQuotationCrmRequestPopupService } from './rns-quotation-crm-request-popup.service';
import { RnsQuotationCrmRequestService } from './rns-quotation-crm-request.service';

@Component({
    selector: 'jhi-rns-quotation-crm-request-delete-dialog',
    templateUrl: './rns-quotation-crm-request-delete-dialog.component.html'
})
export class RnsQuotationCrmRequestDeleteDialogComponent {
    rnsQuotationCrmRequest: RnsQuotationCrmRequest;

    constructor(
        private rnsQuotationCrmRequestService: RnsQuotationCrmRequestService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationCrmRequestService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationCrmRequestListModification',
                content: 'Deleted an rnsQuotationCrmRequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-crm-request-delete-popup',
    template: ''
})
export class RnsQuotationCrmRequestDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationCrmRequestPopupService: RnsQuotationCrmRequestPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationCrmRequestPopupService.open(RnsQuotationCrmRequestDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
