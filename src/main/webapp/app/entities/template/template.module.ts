import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    TemplateService,
    TemplatePopupService,
    TemplateComponent,
    TemplateDetailComponent,
    TemplateDialogComponent,
    TemplatePopupComponent,
    TemplateDeletePopupComponent,
    TemplateDeleteDialogComponent,
    TemplateCopyDialogComponent,
    TemplateCopyPopupComponent,
    templateRoute,
    templatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...templateRoute,
    ...templatePopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TemplateComponent,
        TemplateDetailComponent,
        TemplateDialogComponent,
        TemplateCopyDialogComponent,
        TemplateDeleteDialogComponent,
        TemplatePopupComponent,
        TemplateCopyPopupComponent,
        TemplateDeletePopupComponent,
    ],
    entryComponents: [
        TemplateComponent,
        TemplateDialogComponent,
        TemplateCopyDialogComponent,
        TemplatePopupComponent,
        TemplateCopyPopupComponent,
        TemplateDeleteDialogComponent,
        TemplateDeletePopupComponent,
    ],
    providers: [
        TemplateService,
        TemplatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsTemplateModule {}
