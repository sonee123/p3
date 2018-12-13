import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsArticleMaster } from './rns-article-master.model';
import { RnsArticleMasterService } from './rns-article-master.service';

@Component({
    selector: 'jhi-rns-article-master-detail',
    templateUrl: './rns-article-master-detail.component.html'
})
export class RnsArticleMasterDetailComponent implements OnInit, OnDestroy {
    rnsArticleMaster: RnsArticleMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsArticleMasterService: RnsArticleMasterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsArticleMasters();
    }

    load(id) {
        this.rnsArticleMasterService.find(id).subscribe(rnsArticleMaster => {
            this.rnsArticleMaster = rnsArticleMaster.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsArticleMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsArticleMasterListModification', response =>
            this.load(this.rnsArticleMaster.id)
        );
    }
}
