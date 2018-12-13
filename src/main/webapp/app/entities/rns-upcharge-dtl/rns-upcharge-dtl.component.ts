import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { RnsUpchargeDtlService } from './rns-upcharge-dtl.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-rns-upcharge-dtl',
    templateUrl: './rns-upcharge-dtl.component.html'
})
export class RnsUpchargeDtlComponent implements OnInit, OnDestroy {
    rnsUpchargeDtls: RnsUpchargeDtl[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rnsUpchargeDtlService: RnsUpchargeDtlService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsUpchargeDtlService.query().subscribe(
            (res: HttpResponse<RnsUpchargeDtl[]>) => {
                this.rnsUpchargeDtls = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRnsUpchargeDtls();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsUpchargeDtl) {
        return item.id;
    }
    registerChangeInRnsUpchargeDtls() {
        this.eventSubscriber = this.eventManager.subscribe('rnsUpchargeDtlListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
