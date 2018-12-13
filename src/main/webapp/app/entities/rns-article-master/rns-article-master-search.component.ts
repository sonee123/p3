import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { RnsArticleMaster } from './rns-article-master.model';
import { RnsArticleMasterPopupService } from './rns-article-master-popup.service';
import { RnsArticleMasterService } from './rns-article-master.service';
import { Principal } from 'app/core';
import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';

@Component({
    selector: 'jhi-rns-article-master-search',
    templateUrl: './rns-article-master-search.component.html'
})
export class RnsArticleMasterSearchComponent implements OnInit, OnDestroy {
    rnsArticleMasters: RnsArticleMaster[];
    currentAccount: any;
    eventSubscriber: Subscription;
    searchArray: RnsArticleMaster[];
    searchArticleModel: String;
    searchtext: String;

    constructor(
        public activeModal: NgbActiveModal,
        private rnsArticleMasterService: RnsArticleMasterService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rnsArticleMasterService.query().subscribe(
            (res: HttpResponse<RnsArticleMaster[]>) => {
                this.rnsArticleMasters = res.body;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: true,
                bAutoWidth: false,
                bLengthChange: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [0, 'asc']
            });
        });
    }

    ngOnInit() {
        this.loadAll();
        this.registerChangeInRnsArticleMasters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RnsArticleMaster) {
        return item.id;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    selectArticle(article) {
        this.eventManager.broadcast({ name: 'selectedSearchArticle', content: article });
        this.activeModal.dismiss('cancel');
    }

    registerChangeInRnsArticleMasters() {
        this.eventSubscriber = this.eventManager.subscribe('rnsArticleMasterListModification', response => {
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-rns-article-master-popup',
    template: ''
})
export class RnsArticleMasterSearchPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsArticleMasterPopupService: RnsArticleMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['catgId']) {
                this.rnsArticleMasterPopupService.open(RnsArticleMasterSearchComponent as Component, false, params['catgId']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
