import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsSectorMaster } from './rns-sector-master.model';
import { RnsSectorMasterService } from './rns-sector-master.service';

@Component({
    selector: 'jhi-rns-sector-master-detail',
    templateUrl: './rns-sector-master-detail.component.html'
})
export class RnsSectorMasterDetailComponent implements OnInit, OnDestroy {
    rnsSectorMaster: RnsSectorMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsSectorMasterService: RnsSectorMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsSectorMasters();
    }

    load(id) {
        this.rnsSectorMasterService.find(id).subscribe(rnsSectorMaster => {
            this.rnsSectorMaster = rnsSectorMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsSectorMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsSectorMasterListModification', response =>
            this.load(this.rnsSectorMaster.id)
        );
    }
}
