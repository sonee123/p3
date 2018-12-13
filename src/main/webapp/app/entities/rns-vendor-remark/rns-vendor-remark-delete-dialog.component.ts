import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorRemark } from './rns-vendor-remark.model';
import { RnsVendorRemarkPopupService } from './rns-vendor-remark-popup.service';
import { RnsVendorRemarkService } from './rns-vendor-remark.service';

@Component({
    selector: 'jhi-rns-vendor-remark-delete-dialog',
    templateUrl: './rns-vendor-remark-delete-dialog.component.html'
})
export class RnsVendorRemarkDeleteDialogComponent {
    rnsVendorRemark: RnsVendorRemark;

    constructor(
        private rnsVendorRemarkService: RnsVendorRemarkService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsVendorRemarkService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsVendorRemarkListModification',
                content: 'Deleted an rnsVendorRemark'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-vendor-remark-delete-popup',
    template: ''
})
export class RnsVendorRemarkDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsVendorRemarkPopupService: RnsVendorRemarkPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsVendorRemarkPopupService.open(RnsVendorRemarkDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
