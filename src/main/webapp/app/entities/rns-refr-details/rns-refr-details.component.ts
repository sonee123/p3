import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsRefrDetails } from './rns-refr-details.model';
import { RnsRefrDetailsService } from './rns-refr-details.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-refr-details',
    templateUrl: './rns-refr-details.component.html'
})
export class RnsRefrDetailsComponent implements OnInit, OnDestroy {
    rnsRefrDetails: RnsRefrDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsRefrDetailsService: RnsRefrDetailsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsRefrDetailsService.query().subscribe(
            (res: HttpResponse<RnsRefrDetails[]>) => {
                this.rnsRefrDetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsRefrDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsRefrDetails) {
        return item.id;
    }
    registerChangeInRnsRefrDetails() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRefrDetailsListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
