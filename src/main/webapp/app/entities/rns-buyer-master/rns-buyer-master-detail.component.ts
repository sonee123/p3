import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsBuyerMaster } from './rns-buyer-master.model';
import { RnsBuyerMasterService } from './rns-buyer-master.service';

@Component({
    selector: 'jhi-rns-buyer-master-detail',
    templateUrl: './rns-buyer-master-detail.component.html'
})
export class RnsBuyerMasterDetailComponent implements OnInit, OnDestroy {
    rnsBuyerMaster: RnsBuyerMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsBuyerMasterService: RnsBuyerMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsBuyerMasters();
    }

    load(id) {
        this.rnsBuyerMasterService.find(id).subscribe(rnsBuyerMaster => {
            this.rnsBuyerMaster = rnsBuyerMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsBuyerMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsBuyerMasterListModification', response => this.load(this.rnsBuyerMaster.id));
    }
}
