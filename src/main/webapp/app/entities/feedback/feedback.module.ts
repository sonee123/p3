import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    FeedbackService,
    FeedbackPopupService,
    FeedbackComponent,
    FeedbackDetailComponent,
    FeedbackDialogComponent,
    FeedbackPopupComponent,
    FeedbackDeletePopupComponent,
    FeedbackDeleteDialogComponent,
    feedbackRoute,
    feedbackPopupRoute,
} from './';

const ENTITY_STATES = [
    ...feedbackRoute,
    ...feedbackPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FeedbackComponent,
        FeedbackDetailComponent,
        FeedbackDialogComponent,
        FeedbackDeleteDialogComponent,
        FeedbackPopupComponent,
        FeedbackDeletePopupComponent,
    ],
    entryComponents: [
        FeedbackComponent,
        FeedbackDialogComponent,
        FeedbackPopupComponent,
        FeedbackDeleteDialogComponent,
        FeedbackDeletePopupComponent,
    ],
    providers: [
        FeedbackService,
        FeedbackPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsFeedbackModule {}
