import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-detail',
    templateUrl: './template-detail.component.html'
})
export class TemplateDetailComponent implements OnInit, OnDestroy {
    template: Template;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
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

    constructor(private eventManager: JhiEventManager, private templateService: TemplateService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInTemplates();
    }

    load(id) {
        this.templateService.find(id).subscribe(template => {
            this.template = template.body;
        });
    }
    previousState() {
        window.history.back();
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('templateListModification', response => this.load(this.template.id));
    }
}
