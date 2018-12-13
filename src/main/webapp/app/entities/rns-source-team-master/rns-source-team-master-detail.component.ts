import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsSourceTeamMaster } from './rns-source-team-master.model';
import { RnsSourceTeamMasterService } from './rns-source-team-master.service';

@Component({
    selector: 'jhi-rns-source-team-master-detail',
    templateUrl: './rns-source-team-master-detail.component.html'
})
export class RnsSourceTeamMasterDetailComponent implements OnInit, OnDestroy {
    rnsSourceTeamMaster: RnsSourceTeamMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsSourceTeamMasters();
    }

    load(id) {
        this.rnsSourceTeamMasterService.find(id).subscribe(rnsSourceTeamMaster => {
            this.rnsSourceTeamMaster = rnsSourceTeamMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsSourceTeamMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsSourceTeamMasterListModification', response =>
            this.load(this.rnsSourceTeamMaster.id)
        );
    }
}
