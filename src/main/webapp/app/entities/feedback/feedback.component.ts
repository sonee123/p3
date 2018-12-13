import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Feedback } from './feedback.model';
import { FeedbackService } from './feedback.service';
import { Principal } from 'app/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import * as $ from 'jquery';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'jhi-feedback',
    templateUrl: './feedback.component.html'
})
export class FeedbackComponent implements OnInit, OnDestroy {
    feedbacks: Feedback[];
    currentAccount: any;
    eventSubscriber: Subscription;
    isWait: boolean;

    constructor(
        private feedbackService: FeedbackService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.feedbackService.query().subscribe(
            (res: HttpResponse<Feedback[]>) => {
                this.feedbacks = res.body;
                this.afterViewInit();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFeedbacks();
    }

    private afterViewInit() {
        $(document).ready(function() {
            let table = (<any>$('#exampleFixed')).DataTable();
            table.destroy();

            table = (<any>$('#exampleFixed')).DataTable({
                paging: true,
                bInfo: false,
                bFilter: true,
                bAutoWidth: true,
                bLengthChange: false,
                columnDefs: [{ targets: 'no-sort', orderable: false }],
                order: [0, 'desc'],
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'collection',
                        text: 'Export &#8964;',
                        buttons: [
                            {
                                extend: 'pdfHtml5',
                                orientation: 'landscape',
                                pageSize: 'LEGAL',
                                exportOptions: {
                                    columns: [0, 1, 2, 3]
                                }
                            },
                            {
                                extend: 'csvHtml5',
                                exportOptions: {
                                    columns: [0, 1, 2, 3]
                                }
                            },
                            {
                                extend: 'print',
                                orientation: 'landscape',
                                pageSize: 'LEGAL',
                                exportOptions: {
                                    columns: [0, 1, 2, 3]
                                }
                            }
                        ]
                    }
                ]
            });
        });
    }

    downloadPdf(feedback: Feedback): any {
        this.isWait = true;
        this.feedbackService.download(feedback.id).subscribe(
            res => {
                this.isWait = false;
                FileSaver.saveAs(res, `${feedback.displayAttachFile}`);
            },
            res => {
                this.isWait = false;
                this.onError(res.message);
            }
        );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Feedback) {
        return item.id;
    }

    registerChangeInFeedbacks() {
        this.eventSubscriber = this.eventManager.subscribe('feedbackListModification', response => {
            (<any>$('#exampleFixed')).DataTable().destroy();
            this.loadAll();
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
