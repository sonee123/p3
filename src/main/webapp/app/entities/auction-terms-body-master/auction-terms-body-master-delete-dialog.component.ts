import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionTermsBodyMaster } from './auction-terms-body-master.model';
import { AuctionTermsBodyMasterPopupService } from './auction-terms-body-master-popup.service';
import { AuctionTermsBodyMasterService } from './auction-terms-body-master.service';

@Component({
    selector: 'jhi-auction-terms-body-master-delete-dialog',
    templateUrl: './auction-terms-body-master-delete-dialog.component.html'
})
export class AuctionTermsBodyMasterDeleteDialogComponent {
    auctionTermsBodyMaster: AuctionTermsBodyMaster;

    constructor(
        private auctionTermsBodyMasterService: AuctionTermsBodyMasterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auctionTermsBodyMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auctionTermsBodyMasterListModification',
                content: 'Deleted an auctionTermsBodyMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auction-terms-body-master-delete-popup',
    template: ''
})
export class AuctionTermsBodyMasterDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionTermsBodyMasterPopupService: AuctionTermsBodyMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.auctionTermsBodyMasterPopupService.open(AuctionTermsBodyMasterDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
