import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RnsVendorRemark } from './rns-vendor-remark.model';
import { RnsVendorRemarkService } from './rns-vendor-remark.service';

@Component({
    selector: 'jhi-rns-vendor-remark-detail',
    templateUrl: './rns-vendor-remark-detail.component.html'
})
export class RnsVendorRemarkDetailComponent implements OnInit, OnDestroy {
    rnsVendorRemark: RnsVendorRemark;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rnsVendorRemarkService: RnsVendorRemarkService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInRnsVendorRemarks();
    }

    load(id) {
        this.rnsVendorRemarkService.find(id).subscribe(rnsVendorRemark => {
            this.rnsVendorRemark = rnsVendorRemark.body;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRnsVendorRemarks() {
        this.eventSubscriber = this.eventManager.subscribe('rnsVendorRemarkListModification', response =>
            this.load(this.rnsVendorRemark.id)
        );
    }
}
