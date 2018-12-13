import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsSourceTeamDtl } from './rns-source-team-dtl.model';
import { RnsSourceTeamDtlService } from './rns-source-team-dtl.service';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from '../rns-source-team-master';

@Component({
    selector: 'jhi-rns-source-team-dtl-detail',
    templateUrl: './rns-source-team-dtl-detail.component.html'
})
export class RnsSourceTeamDtlDetailComponent implements OnInit, OnDestroy {
    rnsSourceTeamDtl: RnsSourceTeamDtl;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    rnsSourceTeamMaster: RnsSourceTeamMaster;

    constructor(
        private eventManager: JhiEventManager,
        private rnsSourceTeamDtlService: RnsSourceTeamDtlService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsSourceTeamDtls();
    }

    load(id) {
        this.rnsSourceTeamDtlService.find(id).subscribe(rnsSourceTeamDtl => {
            this.rnsSourceTeamDtl = rnsSourceTeamDtl.body;
            if (this.rnsSourceTeamDtl) {
                this.rnsSourceTeamMasterService.find(this.rnsSourceTeamDtl.masterId).subscribe(rnsSourceTeamMaster => {
                    this.rnsSourceTeamMaster = rnsSourceTeamMaster.body;
                });
            }
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsSourceTeamDtls() {
        this.eventSubscriber = this.eventManager.subscribe('rnsSourceTeamDtlListModification', response =>
            this.load(this.rnsSourceTeamDtl.id)
        );
    }
}
