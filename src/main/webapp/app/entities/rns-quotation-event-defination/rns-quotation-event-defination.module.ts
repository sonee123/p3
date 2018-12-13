import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsQuotationEventDefinationService,
    RnsQuotationEventDefinationPopupService,
    RnsQuotationEventDefinationComponent,
    RnsQuotationEventDefinationDetailComponent,
    RnsQuotationEventDefinationDialogComponent,
    RnsQuotationEventDefinationPopupComponent,
    RnsQuotationEventDefinationDeletePopupComponent,
    RnsQuotationEventDefinationDeleteDialogComponent,
    rnsQuotationEventDefinationRoute,
    rnsQuotationEventDefinationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsQuotationEventDefinationRoute,
    ...rnsQuotationEventDefinationPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsQuotationEventDefinationComponent,
        RnsQuotationEventDefinationDetailComponent,
        RnsQuotationEventDefinationDialogComponent,
        RnsQuotationEventDefinationDeleteDialogComponent,
        RnsQuotationEventDefinationPopupComponent,
        RnsQuotationEventDefinationDeletePopupComponent,
    ],
    entryComponents: [
        RnsQuotationEventDefinationComponent,
        RnsQuotationEventDefinationDialogComponent,
        RnsQuotationEventDefinationPopupComponent,
        RnsQuotationEventDefinationDeleteDialogComponent,
        RnsQuotationEventDefinationDeletePopupComponent,
    ],
    providers: [
        RnsQuotationEventDefinationService,
        RnsQuotationEventDefinationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationEventDefinationModule {}
