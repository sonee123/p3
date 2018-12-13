import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RnsRfqPrice } from './rns-rfq-price.model';
import { RnsRfqPricePopupService } from './rns-rfq-price-popup.service';
import { RnsRfqPriceService } from './rns-rfq-price.service';

@Component({
    selector: 'jhi-rns-rfq-price-dialog',
    templateUrl: './rns-rfq-price-dialog.component.html'
})
export class RnsRfqPriceDialogComponent implements OnInit {
    rnsRfqPrice: RnsRfqPrice;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rnsRfqPriceService: RnsRfqPriceService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rnsRfqPrice.id !== undefined) {
            this.subscribeToSaveResponse(this.rnsRfqPriceService.update(this.rnsRfqPrice));
        } else {
            this.subscribeToSaveResponse(this.rnsRfqPriceService.create(this.rnsRfqPrice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsRfqPrice>>) {
        result.subscribe((res: HttpResponse<RnsRfqPrice>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsRfqPrice>) {
        this.eventManager.broadcast({ name: 'rnsRfqPriceListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-rfq-price-popup',
    template: ''
})
export class RnsRfqPricePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsRfqPricePopupService: RnsRfqPricePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.rnsRfqPricePopupService.open(RnsRfqPriceDialogComponent as Component, params['id']);
            } else {
                this.rnsRfqPricePopupService.open(RnsRfqPriceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
