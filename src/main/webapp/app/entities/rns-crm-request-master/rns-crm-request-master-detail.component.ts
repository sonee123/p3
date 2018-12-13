import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsCrmRequestMaster } from './rns-crm-request-master.model';
import { RnsCrmRequestMasterService } from './rns-crm-request-master.service';

@Component({
    selector: 'jhi-rns-crm-request-master-detail',
    templateUrl: './rns-crm-request-master-detail.component.html'
})
export class RnsCrmRequestMasterDetailComponent implements OnInit, OnDestroy {
    rnsCrmRequestMaster: RnsCrmRequestMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsCrmRequestMasterService: RnsCrmRequestMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsCrmRequestMasters();
    }

    load(id) {
        this.rnsCrmRequestMasterService.find(id).subscribe(rnsCrmRequestMaster => {
            this.rnsCrmRequestMaster = rnsCrmRequestMaster.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsCrmRequestMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsCrmRequestMasterListModification', response =>
            this.load(this.rnsCrmRequestMaster.id)
        );
    }
}
