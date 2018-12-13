import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsQuotationVendors } from './rns-quotation-vendors.model';
import { RnsQuotationVendorsPopupService } from './rns-quotation-vendors-popup.service';
import { RnsQuotationVendorsService } from './rns-quotation-vendors.service';

@Component({
    selector: 'jhi-rns-quotation-vendors-delete-dialog',
    templateUrl: './rns-quotation-vendors-delete-dialog.component.html'
})
export class RnsQuotationVendorsDeleteDialogComponent {
    rnsQuotationVendors: RnsQuotationVendors;

    constructor(
        private rnsQuotationVendorsService: RnsQuotationVendorsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsQuotationVendorsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsQuotationVendorsListModification',
                content: 'Deleted an rnsQuotationVendors'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-quotation-vendors-delete-popup',
    template: ''
})
export class RnsQuotationVendorsDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsQuotationVendorsPopupService: RnsQuotationVendorsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsQuotationVendorsPopupService.open(RnsQuotationVendorsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
