import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsQuotationVariantService,
    RnsQuotationVariantPopupService,
    RnsQuotationVariantComponent,
    RnsQuotationVariantDetailComponent,
    RnsQuotationVariantDialogComponent,
    RnsQuotationVariantPopupComponent,
    RnsQuotationVariantDeletePopupComponent,
    RnsQuotationVariantDeleteDialogComponent,
    rnsQuotationVariantRoute,
    rnsQuotationVariantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsQuotationVariantRoute,
    ...rnsQuotationVariantPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsQuotationVariantComponent,
        RnsQuotationVariantDetailComponent,
        RnsQuotationVariantDialogComponent,
        RnsQuotationVariantDeleteDialogComponent,
        RnsQuotationVariantPopupComponent,
        RnsQuotationVariantDeletePopupComponent,
    ],
    entryComponents: [
        RnsQuotationVariantComponent,
        RnsQuotationVariantDialogComponent,
        RnsQuotationVariantPopupComponent,
        RnsQuotationVariantDeleteDialogComponent,
        RnsQuotationVariantDeletePopupComponent,
    ],
    providers: [
        RnsQuotationVariantService,
        RnsQuotationVariantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationVariantModule {}
