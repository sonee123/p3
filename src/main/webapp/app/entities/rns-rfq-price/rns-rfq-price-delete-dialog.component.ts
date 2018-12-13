import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRfqPrice } from './rns-rfq-price.model';
import { RnsRfqPricePopupService } from './rns-rfq-price-popup.service';
import { RnsRfqPriceService } from './rns-rfq-price.service';

@Component({
    selector: 'jhi-rns-rfq-price-delete-dialog',
    templateUrl: './rns-rfq-price-delete-dialog.component.html'
})
export class RnsRfqPriceDeleteDialogComponent {
    rnsRfqPrice: RnsRfqPrice;

    constructor(
        private rnsRfqPriceService: RnsRfqPriceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsRfqPriceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsRfqPriceListModification',
                content: 'Deleted an rnsRfqPrice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-rfq-price-delete-popup',
    template: ''
})
export class RnsRfqPriceDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRfqPricePopupService: RnsRfqPricePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsRfqPricePopupService.open(RnsRfqPriceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
