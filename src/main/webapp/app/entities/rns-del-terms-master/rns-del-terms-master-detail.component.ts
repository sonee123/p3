import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsDelTermsMaster } from './rns-del-terms-master.model';
import { RnsDelTermsMasterService } from './rns-del-terms-master.service';

@Component({
    selector: 'jhi-rns-del-terms-master-detail',
    templateUrl: './rns-del-terms-master-detail.component.html'
})
export class RnsDelTermsMasterDetailComponent implements OnInit, OnDestroy {
    rnsDelTermsMaster: RnsDelTermsMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsDelTermsMasterService: RnsDelTermsMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsDelTermsMasters();
    }

    load(id) {
        this.rnsDelTermsMasterService.find(id).subscribe(rnsDelTermsMaster => {
            this.rnsDelTermsMaster = rnsDelTermsMaster.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsDelTermsMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsDelTermsMasterListModification', response =>
            this.load(this.rnsDelTermsMaster.id)
        );
    }
}
