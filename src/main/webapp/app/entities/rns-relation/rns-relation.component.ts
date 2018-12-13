import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsRelation } from './rns-relation.model';
import { RnsRelationService } from './rns-relation.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-relation',
    templateUrl: './rns-relation.component.html'
})
export class RnsRelationComponent implements OnInit, OnDestroy {
    rnsRelations: RnsRelation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsRelationService: RnsRelationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsRelationService.query().subscribe(
            (res: HttpResponse<RnsRelation[]>) => {
                this.rnsRelations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsRelations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsRelation) {
        return item.id;
    }
    registerChangeInRnsRelations() {
        this.eventSubscriber = this.eventManager.subscribe('rnsRelationListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
