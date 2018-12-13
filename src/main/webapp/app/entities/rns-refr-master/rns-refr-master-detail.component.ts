import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRefrMaster } from './rns-refr-master.model';
import { RnsRefrMasterService } from './rns-refr-master.service';

@Component({
    selector: 'jhi-rns-refr-master-detail',
    templateUrl: './rns-refr-master-detail.component.html'
})
export class RnsRefrMasterDetailComponent implements OnInit, OnDestroy {
    rnsRefrMaster: RnsRefrMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsRefrMasterService: RnsRefrMasterService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsRefrMasters();
    }

    load(id) {
        this.rnsRefrMasterService.find(id).subscribe(rnsRefrMaster => {
            this.rnsRefrMaster = rnsRefrMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsRefrMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRefrMasterListModification', response => this.load(this.rnsRefrMaster.id));
    }
}
