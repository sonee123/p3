import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsRelation } from './rns-relation.model';
import { RnsRelationService } from './rns-relation.service';

@Component({
    selector: 'jhi-rns-relation-detail',
    templateUrl: './rns-relation-detail.component.html'
})
export class RnsRelationDetailComponent implements OnInit, OnDestroy {
    rnsRelation: RnsRelation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager, private rnsRelationService: RnsRelationService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsRelations();
    }

    load(id) {
        this.rnsRelationService.find(id).subscribe(rnsRelation => {
            this.rnsRelation = rnsRelation.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsRelations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRelationListModification', response => this.load(this.rnsRelation.id));
    }
}
