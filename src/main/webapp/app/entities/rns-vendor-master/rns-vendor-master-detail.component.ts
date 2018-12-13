import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorMaster } from './rns-vendor-master.model';
import { RnsVendorMasterService } from './rns-vendor-master.service';

@Component({
    selector: 'jhi-rns-vendor-master-detail',
    templateUrl: './rns-vendor-master-detail.component.html'
})
export class RnsVendorMasterDetailComponent implements OnInit, OnDestroy {
    rnsVendorMaster: RnsVendorMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsVendorMasterService: RnsVendorMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsVendorMasters();
    }

    load(id) {
        this.rnsVendorMasterService.find(id).subscribe(rnsVendorMaster => {
            this.rnsVendorMaster = rnsVendorMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsVendorMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorMasterListModification', response =>
            this.load(this.rnsVendorMaster.id)
        );
    }
}
