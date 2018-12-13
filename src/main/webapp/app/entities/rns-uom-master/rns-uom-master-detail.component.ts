import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsUomMaster } from './rns-uom-master.model';
import { RnsUomMasterService } from './rns-uom-master.service';

@Component({
    selector: 'jhi-rns-uom-master-detail',
    templateUrl: './rns-uom-master-detail.component.html'
})
export class RnsUomMasterDetailComponent implements OnInit, OnDestroy {
    rnsUomMaster: RnsUomMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsUomMasterService: RnsUomMasterService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsUomMasters();
    }

    load(id) {
        this.rnsUomMasterService.find(id).subscribe(rnsUomMaster => {
            this.rnsUomMaster = rnsUomMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsUomMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsUomMasterListModification', response => this.load(this.rnsUomMaster.id));
    }
}
