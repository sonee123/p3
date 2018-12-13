import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsQuotationDealTermsService,
    RnsQuotationDealTermsPopupService,
    RnsQuotationDealTermsComponent,
    RnsQuotationDealTermsDetailComponent,
    RnsQuotationDealTermsDialogComponent,
    RnsQuotationDealTermsPopupComponent,
    RnsQuotationDealTermsDeletePopupComponent,
    RnsQuotationDealTermsDeleteDialogComponent,
    rnsQuotationDealTermsRoute,
    rnsQuotationDealTermsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsQuotationDealTermsRoute,
    ...rnsQuotationDealTermsPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsQuotationDealTermsComponent,
        RnsQuotationDealTermsDetailComponent,
        RnsQuotationDealTermsDialogComponent,
        RnsQuotationDealTermsDeleteDialogComponent,
        RnsQuotationDealTermsPopupComponent,
        RnsQuotationDealTermsDeletePopupComponent,
    ],
    entryComponents: [
        RnsQuotationDealTermsComponent,
        RnsQuotationDealTermsDialogComponent,
        RnsQuotationDealTermsPopupComponent,
        RnsQuotationDealTermsDeleteDialogComponent,
        RnsQuotationDealTermsDeletePopupComponent,
    ],
    providers: [
        RnsQuotationDealTermsService,
        RnsQuotationDealTermsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationDealTermsModule {}
