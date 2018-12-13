import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    EmailTemplateBodyService,
    EmailTemplateBodyPopupService,
    EmailTemplateBodyComponent,
    EmailTemplateBodyDetailComponent,
    EmailTemplateBodyDialogComponent,
    EmailTemplateBodyPopupComponent,
    EmailTemplateBodyDeletePopupComponent,
    EmailTemplateBodyDeleteDialogComponent,
    emailTemplateBodyRoute,
    emailTemplateBodyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emailTemplateBodyRoute,
    ...emailTemplateBodyPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmailTemplateBodyComponent,
        EmailTemplateBodyDetailComponent,
        EmailTemplateBodyDialogComponent,
        EmailTemplateBodyDeleteDialogComponent,
        EmailTemplateBodyPopupComponent,
        EmailTemplateBodyDeletePopupComponent,
    ],
    entryComponents: [
        EmailTemplateBodyComponent,
        EmailTemplateBodyDialogComponent,
        EmailTemplateBodyPopupComponent,
        EmailTemplateBodyDeleteDialogComponent,
        EmailTemplateBodyDeletePopupComponent,
    ],
    providers: [
        EmailTemplateBodyService,
        EmailTemplateBodyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsEmailTemplateBodyModule {}
