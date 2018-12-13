import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    EmailTemplateService,
    EmailTemplatePopupService,
    EmailTemplateComponent,
    EmailTemplateDetailComponent,
    EmailTemplateDialogComponent,
    EmailTemplatePopupComponent,
    EmailTemplateDeletePopupComponent,
    EmailTemplateDeleteDialogComponent,
    emailTemplateRoute,
    emailTemplatePopupRoute,
} from './';

import { CKEditorModule } from 'ng2-ckeditor';

const ENTITY_STATES = [
    ...emailTemplateRoute,
    ...emailTemplatePopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES),
        CKEditorModule
    ],
    declarations: [
        EmailTemplateComponent,
        EmailTemplateDetailComponent,
        EmailTemplateDialogComponent,
        EmailTemplateDeleteDialogComponent,
        EmailTemplatePopupComponent,
        EmailTemplateDeletePopupComponent,
    ],
    entryComponents: [
        EmailTemplateComponent,
        EmailTemplateDialogComponent,
        EmailTemplatePopupComponent,
        EmailTemplateDeleteDialogComponent,
        EmailTemplateDeletePopupComponent,
    ],
    providers: [
        EmailTemplateService,
        EmailTemplatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsEmailTemplateModule {}
