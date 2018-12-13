import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsCatgMaster } from './rns-catg-master.model';
import { RnsCatgMasterService } from './rns-catg-master.service';

@Component({
    selector: 'jhi-rns-catg-master-detail',
    templateUrl: './rns-catg-master-detail.component.html'
})
export class RnsCatgMasterDetailComponent implements OnInit, OnDestroy {
    rnsCatgMaster: RnsCatgMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsCatgMasterService: RnsCatgMasterService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsCatgMasters();
    }

    load(id) {
        this.rnsCatgMasterService.find(id).subscribe(rnsCatgMaster => {
            this.rnsCatgMaster = rnsCatgMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsCatgMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsCatgMasterListModification', response => this.load(this.rnsCatgMaster.id));
    }
}
