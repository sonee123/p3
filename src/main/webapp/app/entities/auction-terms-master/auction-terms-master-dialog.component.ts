import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AuctionTermsMaster } from './auction-terms-master.model';
import { AuctionTermsMasterPopupService } from './auction-terms-master-popup.service';
import { AuctionTermsMasterService } from './auction-terms-master.service';
import { RnsCatgMaster } from '../rns-catg-master/rns-catg-master.model';
import { RnsCatgMasterService } from '../rns-catg-master/rns-catg-master.service';
import { RnsTypeMasterService } from '../rns-type-master/rns-type-master.service';
import { RnsSourceTeamMaster, RnsSourceTeamMasterService } from '../rns-source-team-master';
import { RnsTypeMaster } from '../rns-type-master/rns-type-master.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-auction-terms-master-dialog',
    templateUrl: './auction-terms-master-dialog.component.html'
})
export class AuctionTermsMasterDialogComponent implements OnInit {
    rnsCatgMasters: RnsCatgMaster[];
    rnsTypeMasters: RnsTypeMaster[];
    rnsSourceTeamMasters: RnsSourceTeamMaster[];
    auctionTermsMaster: AuctionTermsMaster;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private auctionTermsMasterService: AuctionTermsMasterService,
        private eventManager: JhiEventManager,
        private rnsCatgMasterService: RnsCatgMasterService,
        private rnsTypeMasterService: RnsTypeMasterService,
        private rnsSourceTeamMasterService: RnsSourceTeamMasterService
    ) {}

    ngOnInit() {
        this.rnsCatgMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnsCatgMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.rnsSourceTeamMasterService.querylogin().subscribe(
            (res: HttpResponse<RnsSourceTeamMaster[]>) => {
                this.rnsSourceTeamMasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        if (this.auctionTermsMaster.rnsCatgMaster != null) {
            const code = this.auctionTermsMaster.rnsCatgMaster.id;
            this.rnsTypeMasterService.queryByCatg(code).subscribe(
                (res: HttpResponse<RnsTypeMaster[]>) => {
                    this.rnsTypeMasters = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.auctionTermsMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.auctionTermsMasterService.update(this.auctionTermsMaster));
        } else {
            this.subscribeToSaveResponse(this.auctionTermsMasterService.create(this.auctionTermsMaster));
        }
    }

    changeCategory() {
        if (this.auctionTermsMaster.rnsCatgMaster != null) {
            const code = this.auctionTermsMaster.rnsCatgMaster.id;
            this.rnsTypeMasterService.queryByCatg(code).subscribe(
                (res: HttpResponse<AuctionTermsMaster[]>) => {
                    this.rnsTypeMasters = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AuctionTermsMaster>>) {
        result.subscribe(
            (res: HttpResponse<AuctionTermsMaster>) => this.onSaveSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(result: HttpResponse<AuctionTermsMaster>) {
        this.eventManager.broadcast({ name: 'auctionTermsMasterListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-auction-terms-master-popup',
    template: ''
})
export class AuctionTermsMasterPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private auctionTermsMasterPopupService: AuctionTermsMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.auctionTermsMasterPopupService.open(AuctionTermsMasterDialogComponent as Component, params['id']);
            } else {
                this.auctionTermsMasterPopupService.open(AuctionTermsMasterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
