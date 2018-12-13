import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorFavMaster } from './rns-vendor-fav-master.model';
import { RnsVendorFavMasterService } from './rns-vendor-fav-master.service';

@Component({
    selector: 'jhi-rns-vendor-fav-master-detail',
    templateUrl: './rns-vendor-fav-master-detail.component.html'
})
export class RnsVendorFavMasterDetailComponent implements OnInit, OnDestroy {
    rnsVendorFavMaster: RnsVendorFavMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsVendorFavMasterService: RnsVendorFavMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsVendorFavMasters();
    }

    load(id) {
        this.rnsVendorFavMasterService.find(id).subscribe(rnsVendorFavMaster => {
            this.rnsVendorFavMaster = rnsVendorFavMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsVendorFavMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorFavMasterListModification', response =>
            this.load(this.rnsVendorFavMaster.id)
        );
    }
}
