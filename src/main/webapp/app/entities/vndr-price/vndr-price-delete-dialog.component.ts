import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VndrPrice } from './vndr-price.model';
import { VndrPricePopupService } from './vndr-price-popup.service';
import { VndrPriceService } from './vndr-price.service';

@Component({
    selector: 'jhi-vndr-price-delete-dialog',
    templateUrl: './vndr-price-delete-dialog.component.html'
})
export class VndrPriceDeleteDialogComponent {
    vndrPrice: VndrPrice;

    constructor(private vndrPriceService: VndrPriceService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vndrPriceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vndrPriceListModification',
                content: 'Deleted an vndrPrice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vndr-price-delete-popup',
    template: ''
})
export class VndrPriceDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private vndrPricePopupService: VndrPricePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.vndrPricePopupService.open(VndrPriceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
