import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsPchMaster } from './rns-pch-master.model';
import { RnsPchMasterService } from './rns-pch-master.service';

@Component({
    selector: 'jhi-rns-pch-master-detail',
    templateUrl: './rns-pch-master-detail.component.html'
})
export class RnsPchMasterDetailComponent implements OnInit, OnDestroy {
    rnsPchMaster: RnsPchMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsPchMasterService: RnsPchMasterService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsPchMasters();
    }

    load(id) {
        this.rnsPchMasterService.find(id).subscribe(rnsPchMaster => {
            this.rnsPchMaster = rnsPchMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsPchMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsPchMasterListModification', response => this.load(this.rnsPchMaster.id));
    }
}
