import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsUpchargeMaster } from './rns-upcharge-master.model';
import { RnsUpchargeMasterService } from './rns-upcharge-master.service';

@Component({
    selector: 'jhi-rns-upcharge-master-detail',
    templateUrl: './rns-upcharge-master-detail.component.html'
})
export class RnsUpchargeMasterDetailComponent implements OnInit, OnDestroy {
    rnsUpchargeMaster: RnsUpchargeMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsUpchargeMasterService: RnsUpchargeMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsUpchargeMasters();
    }

    load(id) {
        this.rnsUpchargeMasterService.find(id).subscribe(rnsUpchargeMaster => {
            this.rnsUpchargeMaster = rnsUpchargeMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsUpchargeMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsUpchargeMasterListModification', response =>
            this.load(this.rnsUpchargeMaster.id)
        );
    }
}
