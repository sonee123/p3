import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionVarDetails } from './auction-var-details.model';
import { AuctionVarDetailsPopupService } from './auction-var-details-popup.service';
import { AuctionVarDetailsService } from './auction-var-details.service';

@Component({
    selector: 'jhi-auction-var-details-delete-dialog',
    templateUrl: './auction-var-details-delete-dialog.component.html'
})
export class AuctionVarDetailsDeleteDialogComponent {
    auctionVarDetails: AuctionVarDetails;

    constructor(
        private auctionVarDetailsService: AuctionVarDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auctionVarDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auctionVarDetailsListModification',
                content: 'Deleted an auctionVarDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auction-var-details-delete-popup',
    template: ''
})
export class AuctionVarDetailsDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionVarDetailsPopupService: AuctionVarDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.auctionVarDetailsPopupService.open(AuctionVarDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
