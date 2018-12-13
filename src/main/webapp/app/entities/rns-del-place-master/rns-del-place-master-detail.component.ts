import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsDelPlaceMaster } from './rns-del-place-master.model';
import { RnsDelPlaceMasterService } from './rns-del-place-master.service';

@Component({
    selector: 'jhi-rns-del-place-master-detail',
    templateUrl: './rns-del-place-master-detail.component.html'
})
export class RnsDelPlaceMasterDetailComponent implements OnInit, OnDestroy {
    rnsDelPlaceMaster: RnsDelPlaceMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsDelPlaceMasterService: RnsDelPlaceMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsDelPlaceMasters();
    }

    load(id) {
        this.rnsDelPlaceMasterService.find(id).subscribe(rnsDelPlaceMaster => {
            this.rnsDelPlaceMaster = rnsDelPlaceMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsDelPlaceMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsDelPlaceMasterListModification', response =>
            this.load(this.rnsDelPlaceMaster.id)
        );
    }
}
