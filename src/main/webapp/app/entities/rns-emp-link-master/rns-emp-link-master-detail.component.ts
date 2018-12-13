import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsEmpLinkMaster } from './rns-emp-link-master.model';
import { RnsEmpLinkMasterService } from './rns-emp-link-master.service';

@Component({
    selector: 'jhi-rns-emp-link-master-detail',
    templateUrl: './rns-emp-link-master-detail.component.html'
})
export class RnsEmpLinkMasterDetailComponent implements OnInit, OnDestroy {
    rnsEmpLinkMaster: RnsEmpLinkMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsEmpLinkMasterService: RnsEmpLinkMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsEmpLinkMasters();
    }

    load(id) {
        this.rnsEmpLinkMasterService.find(id).subscribe(rnsEmpLinkMaster => {
            this.rnsEmpLinkMaster = rnsEmpLinkMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsEmpLinkMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsEmpLinkMasterListModification', response =>
            this.load(this.rnsEmpLinkMaster.id)
        );
    }
}
