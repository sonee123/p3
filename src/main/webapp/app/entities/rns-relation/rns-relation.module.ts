import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import { P3AdminModule } from '../../admin/admin.module';
import {
    RnsRelationService,
    RnsRelationPopupService,
    RnsRelationComponent,
    RnsRelationDetailComponent,
    RnsRelationDialogComponent,
    RnsRelationPopupComponent,
    RnsRelationDeletePopupComponent,
    RnsRelationDeleteDialogComponent,
    rnsRelationRoute,
    rnsRelationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsRelationRoute,
    ...rnsRelationPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        P3AdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsRelationComponent,
        RnsRelationDetailComponent,
        RnsRelationDialogComponent,
        RnsRelationDeleteDialogComponent,
        RnsRelationPopupComponent,
        RnsRelationDeletePopupComponent,
    ],
    entryComponents: [
        RnsRelationComponent,
        RnsRelationDialogComponent,
        RnsRelationPopupComponent,
        RnsRelationDeleteDialogComponent,
        RnsRelationDeletePopupComponent,
    ],
    providers: [
        RnsRelationService,
        RnsRelationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsRelationModule {}
