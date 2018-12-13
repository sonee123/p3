import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { RnsUpchargeDtlPopupService } from './rns-upcharge-dtl-popup.service';
import { RnsUpchargeDtlService } from './rns-upcharge-dtl.service';

@Component({
    selector: 'jhi-rns-upcharge-dtl-delete-dialog',
    templateUrl: './rns-upcharge-dtl-delete-dialog.component.html'
})
export class RnsUpchargeDtlDeleteDialogComponent {
    rnsUpchargeDtl: RnsUpchargeDtl;

    constructor(
        private rnsUpchargeDtlService: RnsUpchargeDtlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rnsUpchargeDtlService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rnsUpchargeDtlListModification',
                content: 'Deleted an rnsUpchargeDtl'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rns-upcharge-dtl-delete-popup',
    template: ''
})
export class RnsUpchargeDtlDeletePopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsUpchargeDtlPopupService: RnsUpchargeDtlPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.rnsUpchargeDtlPopupService.open(RnsUpchargeDtlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
