import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsForwardTypeMaster } from './rns-forward-type-master.model';
import { RnsForwardTypeMasterService } from './rns-forward-type-master.service';

@Component({
    selector: 'jhi-rns-forward-type-master-detail',
    templateUrl: './rns-forward-type-master-detail.component.html'
})
export class RnsForwardTypeMasterDetailComponent implements OnInit, OnDestroy {
    rnsForwardTypeMaster: RnsForwardTypeMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsForwardTypeMasterService: RnsForwardTypeMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsForwardTypeMasters();
    }

    load(id) {
        this.rnsForwardTypeMasterService.find(id).subscribe(rnsForwardTypeMaster => {
            this.rnsForwardTypeMaster = rnsForwardTypeMaster.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsForwardTypeMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsForwardTypeMasterListModification', response =>
            this.load(this.rnsForwardTypeMaster.id)
        );
    }
}
