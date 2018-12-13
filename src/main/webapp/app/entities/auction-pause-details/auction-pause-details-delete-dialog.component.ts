import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionPauseDetails } from './auction-pause-details.model';
import { AuctionPauseDetailsPopupService } from './auction-pause-details-popup.service';
import { AuctionPauseDetailsService } from './auction-pause-details.service';

@Component({
    selector: 'jhi-auction-pause-details-delete-dialog',
    templateUrl: './auction-pause-details-delete-dialog.component.html'
})
export class AuctionPauseDetailsDeleteDialogComponent {
    auctionPauseDetails: AuctionPauseDetails;

    constructor(
        private auctionPauseDetailsService: AuctionPauseDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auctionPauseDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auctionPauseDetailsListModification',
                content: 'Deleted an auctionPauseDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auction-pause-details-delete-popup',
    template: ''
})
export class AuctionPauseDetailsDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionPauseDetailsPopupService: AuctionPauseDetailsPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.auctionPauseDetailsPopupService.open(AuctionPauseDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
