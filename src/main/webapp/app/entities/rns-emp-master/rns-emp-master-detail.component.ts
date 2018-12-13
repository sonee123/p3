import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsEmpMaster } from './rns-emp-master.model';
import { RnsEmpMasterService } from './rns-emp-master.service';

@Component({
    selector: 'jhi-rns-emp-master-detail',
    templateUrl: './rns-emp-master-detail.component.html'
})
export class RnsEmpMasterDetailComponent implements OnInit, OnDestroy {
    rnsEmpMaster: RnsEmpMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsEmpMasterService: RnsEmpMasterService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsEmpMasters();
    }

    load(id) {
        this.rnsEmpMasterService.find(id).subscribe(rnsEmpMaster => {
            this.rnsEmpMaster = rnsEmpMaster.body;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsEmpMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsEmpMasterListModification', response => this.load(this.rnsEmpMaster.id));
    }
}
