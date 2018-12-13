import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AuctionVrnt } from './auction-vrnt.model';
import { AuctionVrntPopupService } from './auction-vrnt-popup.service';
import { AuctionVrntService } from './auction-vrnt.service';

@Component({
    selector: 'jhi-auction-vrnt-delete-dialog',
    templateUrl: './auction-vrnt-delete-dialog.component.html'
})
export class AuctionVrntDeleteDialogComponent {
    auctionVrnt: AuctionVrnt;

    constructor(
        private auctionVrntService: AuctionVrntService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auctionVrntService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auctionVrntListModification',
                content: 'Deleted an auctionVrnt'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auction-vrnt-delete-popup',
    template: ''
})
export class AuctionVrntDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionVrntPopupService: AuctionVrntPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.auctionVrntPopupService.open(AuctionVrntDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
