import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplatePopupService } from './template-popup.service';
import { TemplateService } from './template.service';
import { RnsCatgMaster, RnsCatgMasterService } from '../rns-catg-master';

@Component({
    selector: 'jhi-template-copy-dialog',
    templateUrl: './template-copy-dialog.component.html'
})
export class TemplateCopyDialogComponent implements OnInit {
    template: Template;
    templates: Template[];
    isSaving: boolean;
    hideOne: boolean;
    hideTwo: boolean;
    hideThree: boolean;
    hideFour: boolean;
    hideFive: boolean;
    hideSix: boolean;
    hideSeven: boolean;
    hideEight: boolean;
    hideNine: boolean;
    hideTen: boolean;
    rnscatgmasters: RnsCatgMaster[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private templateService: TemplateService,
        private rnsCatgMasterService: RnsCatgMasterService,
        private eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.rnsCatgMasterService.query().subscribe(
            (res: HttpResponse<RnsCatgMaster[]>) => {
                this.rnscatgmasters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.compareDropdown(this.template.specificationOneShowAs, 'DROPDOWN', 'vbvb', 1);
        this.compareDropdown(this.template.specificationTwoShowAs, 'DROPDOWN', 'vbvb', 2);
        this.compareDropdown(this.template.specificationThreeShowAs, 'DROPDOWN', 'vbvb', 3);
        this.compareDropdown(this.template.specificationFourShowAs, 'DROPDOWN', 'vbvb', 4);
        this.compareDropdown(this.template.specificationFiveShowAs, 'DROPDOWN', 'vbvb', 5);
        this.compareDropdown(this.template.specificationSixShowAs, 'DROPDOWN', 'vbvb', 6);
        this.compareDropdown(this.template.specificationSevenShowAs, 'DROPDOWN', 'vbvb', 7);
        this.compareDropdown(this.template.specificationEightShowAs, 'DROPDOWN', 'vbvb', 8);
        this.compareDropdown(this.template.specificationNineShowAs, 'DROPDOWN', 'vbvb', 9);
        this.compareDropdown(this.template.specificationTenShowAs, 'DROPDOWN', 'vbvb', 10);

        if (!this.template.id) {
            this.template.showInRfqOneRequired = true;
            this.template.showInRfqTwoRequired = true;
            this.template.showInRfqThreeRequired = true;
            this.template.showInRfqFourRequired = true;
            this.template.showInRfqFiveRequired = true;
            this.template.showInRfqSixRequired = true;
            this.template.showInRfqSevenRequired = true;
            this.template.showInRfqEightRequired = true;
            this.template.showInRfqNineRequired = true;
            this.template.showInRfqTenRequired = true;

            this.template.showInAuctionOneRequired = true;
            this.template.showInAuctionTwoRequired = true;
            this.template.showInAuctionThreeRequired = true;
            this.template.showInAuctionFourRequired = true;
            this.template.showInAuctionFiveRequired = true;
            this.template.showInAuctionSixRequired = true;
            this.template.showInAuctionSevenRequired = true;
            this.template.showInAuctionEightRequired = true;
            this.template.showInAuctionNineRequired = true;
            this.template.showInAuctionTenRequired = true;
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.template.flag = true;
        if (this.template.id !== undefined) {
            this.subscribeToSaveResponse(this.templateService.update(this.template));
        } else {
            this.subscribeToSaveResponse(this.templateService.create(this.template));
        }
    }

    compareDropdown(specificationShowAs, value, specificationValue, hideposition) {
        // console.log('::::::::::::::', specificationShowAs, specificationShowAs == 'DROPDOWN');

        if (specificationShowAs === 'DROPDOWN') {
            if (hideposition === 1) {
                this.hideOne = true;
            } else if (hideposition === 2) {
                this.hideTwo = true;
            } else if (hideposition === 3) {
                this.hideThree = true;
            } else if (hideposition === 4) {
                this.hideFour = true;
            } else if (hideposition === 5) {
                this.hideFive = true;
            } else if (hideposition === 6) {
                this.hideSix = true;
            } else if (hideposition === 7) {
                this.hideSeven = true;
            } else if (hideposition === 8) {
                this.hideEight = true;
            } else if (hideposition === 9) {
                this.hideNine = true;
            } else if (hideposition === 10) {
                this.hideTen = true;
            }
        } else {
            if (hideposition === 1) {
                this.hideOne = false;
            } else if (hideposition === 2) {
                this.hideTwo = false;
            } else if (hideposition === 3) {
                this.hideThree = false;
            } else if (hideposition === 4) {
                this.hideFour = false;
            } else if (hideposition === 5) {
                this.hideFive = false;
            } else if (hideposition === 6) {
                this.hideSix = false;
            } else if (hideposition === 7) {
                this.hideSeven = false;
            } else if (hideposition === 8) {
                this.hideEight = false;
            } else if (hideposition === 9) {
                this.hideNine = false;
            } else if (hideposition === 10) {
                this.hideTen = false;
            }
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Template>>) {
        result.subscribe((res: HttpResponse<Template>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<Template>) {
        this.eventManager.broadcast({ name: 'templateListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result.body);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsCatgMasterById(index: number, item: RnsCatgMaster) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-template-copy-popup',
    template: ''
})
export class TemplateCopyPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private templatePopupService: TemplatePopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.templatePopupService.copy(TemplateCopyDialogComponent as Component, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
