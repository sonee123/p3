import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionTermsMaster } from './auction-terms-master.model';
import { AuctionTermsMasterPopupService } from './auction-terms-master-popup.service';
import { AuctionTermsMasterService } from './auction-terms-master.service';

@Component({
    selector: 'jhi-auction-terms-master-delete-dialog',
    templateUrl: './auction-terms-master-delete-dialog.component.html'
})
export class AuctionTermsMasterDeleteDialogComponent {
    auctionTermsMaster: AuctionTermsMaster;

    constructor(
        private auctionTermsMasterService: AuctionTermsMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auctionTermsMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auctionTermsMasterListModification',
                content: 'Deleted an auctionTermsMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auction-terms-master-delete-popup',
    template: ''
})
export class AuctionTermsMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionTermsMasterPopupService: AuctionTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.auctionTermsMasterPopupService.open(AuctionTermsMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
