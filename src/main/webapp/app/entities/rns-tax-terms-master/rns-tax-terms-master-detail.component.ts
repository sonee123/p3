import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsTaxTermsMaster } from './rns-tax-terms-master.model';
import { RnsTaxTermsMasterService } from './rns-tax-terms-master.service';

@Component({
    selector: 'jhi-rns-tax-terms-master-detail',
    templateUrl: './rns-tax-terms-master-detail.component.html'
})
export class RnsTaxTermsMasterDetailComponent implements OnInit, OnDestroy {
    rnsTaxTermsMaster: RnsTaxTermsMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsTaxTermsMasterService: RnsTaxTermsMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsTaxTermsMasters();
    }

    load(id) {
        this.rnsTaxTermsMasterService.find(id).subscribe(rnsTaxTermsMaster => {
            this.rnsTaxTermsMaster = rnsTaxTermsMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsTaxTermsMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsTaxTermsMasterListModification', response =>
            this.load(this.rnsTaxTermsMaster.id)
        );
    }
}
