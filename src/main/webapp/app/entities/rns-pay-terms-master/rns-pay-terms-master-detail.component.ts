import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsPayTermsMaster } from './rns-pay-terms-master.model';
import { RnsPayTermsMasterService } from './rns-pay-terms-master.service';

@Component({
    selector: 'jhi-rns-pay-terms-master-detail',
    templateUrl: './rns-pay-terms-master-detail.component.html'
})
export class RnsPayTermsMasterDetailComponent implements OnInit, OnDestroy {
    rnsPayTermsMaster: RnsPayTermsMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsPayTermsMasterService: RnsPayTermsMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsPayTermsMasters();
    }

    load(id) {
        this.rnsPayTermsMasterService.find(id).subscribe(rnsPayTermsMaster => {
            this.rnsPayTermsMaster = rnsPayTermsMaster.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsPayTermsMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsPayTermsMasterListModification', response =>
            this.load(this.rnsPayTermsMaster.id)
        );
    }
}
