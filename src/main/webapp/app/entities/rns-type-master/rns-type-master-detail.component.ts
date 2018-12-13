import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsTypeMaster } from './rns-type-master.model';
import { RnsTypeMasterService } from './rns-type-master.service';

@Component({
    selector: 'jhi-rns-type-master-detail',
    templateUrl: './rns-type-master-detail.component.html'
})
export class RnsTypeMasterDetailComponent implements OnInit, OnDestroy {
    rnsTypeMaster: RnsTypeMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsTypeMasterService: RnsTypeMasterService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsTypeMasters();
    }

    load(id) {
        this.rnsTypeMasterService.find(id).subscribe(rnsTypeMaster => {
            this.rnsTypeMaster = rnsTypeMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsTypeMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsTypeMasterListModification', response => this.load(this.rnsTypeMaster.id));
    }
}
