import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Template } from './template.model';
import { TemplateService } from './template.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import * as $ from 'jquery';
import 'datatables.net';
import 'datatables.net-dt';
import 'datatables.net-fixedcolumns';
import 'datatables.net-buttons';
import 'datatables.net-buttons/js/buttons.colVis.min';
import 'datatables.net-buttons/js/dataTables.buttons.min';
import 'datatables.net-buttons/js/buttons.flash.min';
import 'jszip/dist/jszip.min';
import 'pdfmake/build/pdfmake.min';
import 'pdfmake/build/vfs_fonts';
import 'datatables.net-buttons/js/buttons.html5.min';
import 'datatables.net-buttons/js/buttons.print.min';
import { RnsQuotation } from 'app/entities/rns-quotation';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'jhi-template',
    templateUrl: './template.component.html',
    styles: [
        `
        .templatePlus td:first-child {
            background: url('content/images/details_open.png') no-repeat center center;
            cursor: pointer;
        }

        tr.shown td:first-child {
            background: url('content/images/details_close.png') no-repeat center center;
        }
    `
    ]
})
export class TemplateComponent implements OnInit, OnDestroy {
    templates: Template[];
    currentAccount: any;
    eventSubscriber: Subscription;
    rnsQuotations: RnsQuotation[];
    rnsQuotation: RnsQuotation;
    error: any;
    success: any;
    isWait: boolean;

    constructor(
        private templateService: TemplateService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.templates = [];
        this.templateService.query().subscribe(
            (res: HttpResponse<Template[]>) => {
                this.templates = res.body;
                this.afterViewInit(res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    download(type): any {
        this.isWait = true;
        this.templateService.download(type).subscribe(
            res => {
                this.isWait = false;
                FileSaver.saveAs(res, `templateMaster.${type}`);
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    setActive(template, isActivated) {
        template.flag = isActivated;

        this.templateService.updateFlag(template).subscribe(response => {
            if (response.status === 200) {
                this.error = null;
                this.success = 'OK';
                (<any>$('#exampleFixed')).DataTable().destroy();
                this.loadAll();
            } else {
                this.success = null;
                this.error = 'ERROR';
            }
        });
    }

    ngOnInit() {
        this.isWait = false;
        const pdfMake = require('pdfmake/build/pdfmake.js');
        const pdfFonts = require('pdfmake/build/vfs_fonts.js');
        pdfMake.vfs = pdfFonts.pdfMake.vfs;
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTemplates();
    }

    private afterViewInit(templates: Template[]) {
        $(document).ready(function() {
            const table = (<any>$('#exampleFixed')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: true,
                bAutoWidth: true,
                bLengthChange: false,
                scrollX: false,
                scrollCollapse: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [1, 'desc']
            });
            $('#exampleFixed tbody').on('click', 'td:first-child', function() {
                const tr = $(this).closest('tr');
                const templateCode: any = $(this).attr('id');
                let templateData: any;
                templates.forEach(template => {
                    if (template.id === Number(templateCode)) {
                        templateData = template;
                    }
                });
                const row = table.row(tr);

                if (row.child.isShown()) {
                    // This row is already open - close it.
                    row.child.hide();
                    tr.removeClass('shown');
                } else {
                    let html = '<table class="table table-sm">';

                    // One
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;1</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationOne != null && templateData.specificationOneRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationOne + '</strong>';
                    } else if (templateData.specificationOne != null) {
                        html += '<strong>' + templateData.specificationOne + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationOneShowAs != null) {
                        html += '<small>' + templateData.specificationOneShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationOneValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationOneValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Two
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;2</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationTwo != null && templateData.specificationTwoRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationTwo + '</strong>';
                    } else if (templateData.specificationTwo != null) {
                        html += '<strong>' + templateData.specificationTwo + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationTwoShowAs != null) {
                        html += '<small>' + templateData.specificationTwoShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationTwoValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationTwoValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Three
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;3</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationThree != null && templateData.specificationThreeRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationThree + '</strong>';
                    } else if (templateData.specificationThree != null) {
                        html += '<strong>' + templateData.specificationThree + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationThreeShowAs != null) {
                        html += '<small>' + templateData.specificationThreeShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationThreeValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationThreeValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Four
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;4</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationFour != null && templateData.specificationFourRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationFour + '</strong>';
                    } else if (templateData.specificationFour != null) {
                        html += '<strong>' + templateData.specificationFour + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationFourShowAs != null) {
                        html += '<small>' + templateData.specificationFourShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationFourValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationFourValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Five
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;5</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationFive != null && templateData.specificationFiveRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationFive + '</strong>';
                    } else if (templateData.specificationFive != null) {
                        html += '<strong>' + templateData.specificationFive + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationFiveShowAs != null) {
                        html += '<small>' + templateData.specificationFiveShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationFiveValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationFiveValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Six
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;6</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationSix != null && templateData.specificationSixRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationSix + '</strong>';
                    } else if (templateData.specificationSix != null) {
                        html += '<strong>' + templateData.specificationSix + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationSixShowAs != null) {
                        html += '<small>' + templateData.specificationSixShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationSixValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationSixValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Seven
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;7</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationSeven != null && templateData.specificationSevenRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationSeven + '</strong>';
                    } else if (templateData.specificationSeven != null) {
                        html += '<strong>' + templateData.specificationSeven + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationSevenShowAs != null) {
                        html += '<small>' + templateData.specificationSevenShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationSevenValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationSevenValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Eight
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;8</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationEight != null && templateData.specificationEightRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationEight + '</strong>';
                    } else if (templateData.specificationEight != null) {
                        html += '<strong>' + templateData.specificationEight + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationEightShowAs != null) {
                        html += '<small>' + templateData.specificationEightShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationEightValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationEightValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Nine
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;9</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationNine != null && templateData.specificationNineRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationNine + '</strong>';
                    } else if (templateData.specificationNine != null) {
                        html += '<strong>' + templateData.specificationNine + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationNineShowAs != null) {
                        html += '<small>' + templateData.specificationNineShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationNineValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationNineValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    // Ten
                    html += '<tr>';
                    html += '<td class="form-control-label" style="font-weight: bold;"><span>Spec&nbsp;10</span></td>';
                    html += '<td class="form-control-label"><div>';
                    if (templateData.specificationTen != null && templateData.specificationTenRequired === true) {
                        html += '<strong><span style="color: red;">*</span>' + templateData.specificationTen + '</strong>';
                    } else if (templateData.specificationTen != null) {
                        html += '<strong>' + templateData.specificationTen + '</strong>';
                    }
                    html += '<div class="clearfix"></div>';
                    if (templateData.specificationTenShowAs != null) {
                        html += '<small>' + templateData.specificationTenShowAs + '</small>';
                    }
                    html += '</div></td>';
                    if (templateData.specificationTenValue != null) {
                        html += '<td class="form-control-label"><strong>' + templateData.specificationTenValue + '</strong></td>';
                    } else {
                        html += '<td class="form-control-label">&nbsp;</td>';
                    }
                    html += '</tr>';

                    html += '</table>';
                    // Open row.
                    row.child(html).show();
                    tr.addClass('shown');
                }
            });
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Template) {
        return item.id;
    }

    registerChangeInTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('templateListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
