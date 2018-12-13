import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { RnsUpchargeDtlService } from './rns-upcharge-dtl.service';

@Component({
    selector: 'jhi-rns-upcharge-dtl-detail',
    templateUrl: './rns-upcharge-dtl-detail.component.html'
})
export class RnsUpchargeDtlDetailComponent implements OnInit, OnDestroy {
    rnsUpchargeDtl: RnsUpchargeDtl;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsUpchargeDtlService: RnsUpchargeDtlService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsUpchargeDtls();
    }

    load(id) {
        this.rnsUpchargeDtlService.find(id).subscribe(rnsUpchargeDtl => {
            this.rnsUpchargeDtl = rnsUpchargeDtl.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsUpchargeDtls() {
        this.eventSubscriber = this.eventManager.subscribe('rnsUpchargeDtlListModification', response => this.load(this.rnsUpchargeDtl.id));
    }
}
