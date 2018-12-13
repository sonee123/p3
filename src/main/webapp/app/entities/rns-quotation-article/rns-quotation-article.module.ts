import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsQuotationArticleService,
    RnsQuotationArticlePopupService,
    RnsQuotationArticleComponent,
    RnsQuotationArticleDetailComponent,
    RnsQuotationArticleDialogComponent,
    RnsQuotationArticlePopupComponent,
    RnsQuotationArticleDeletePopupComponent,
    RnsQuotationArticleDeleteDialogComponent,
    rnsQuotationArticleRoute,
    rnsQuotationArticlePopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsQuotationArticleRoute,
    ...rnsQuotationArticlePopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsQuotationArticleComponent,
        RnsQuotationArticleDetailComponent,
        RnsQuotationArticleDialogComponent,
        RnsQuotationArticleDeleteDialogComponent,
        RnsQuotationArticlePopupComponent,
        RnsQuotationArticleDeletePopupComponent,
    ],
    entryComponents: [
        RnsQuotationArticleComponent,
        RnsQuotationArticleDialogComponent,
        RnsQuotationArticlePopupComponent,
        RnsQuotationArticleDeleteDialogComponent,
        RnsQuotationArticleDeletePopupComponent,
    ],
    providers: [
        RnsQuotationArticleService,
        RnsQuotationArticlePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationArticleModule {}
