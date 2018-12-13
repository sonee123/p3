import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { P3SharedModule } from 'app/shared';
import {
    RnsQuotationService,
    RnsQuotationPopupService,
    RnsQuotationComponent,
    RnsQuotationDetailComponent,
    RnsQuotationDialogComponent,
    RnsQuotationPopupComponent,
    RnsQuotationDeletePopupComponent,
    RnsQuotationDeleteDialogComponent,
    RnsQuotationCompareDialogComponent,
    RnsQuotationComparePopupComponent,
    rnsQuotationRoute,
    rnsQuotationPopupRoute
} from './';

const ENTITY_STATES = [...rnsQuotationRoute, ...rnsQuotationPopupRoute];

@NgModule({
    imports: [P3SharedModule, OwlDateTimeModule, OwlNativeDateTimeModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RnsQuotationComponent,
        RnsQuotationDetailComponent,
        RnsQuotationDialogComponent,
        RnsQuotationCompareDialogComponent,
        RnsQuotationDeleteDialogComponent,
        RnsQuotationPopupComponent,
        RnsQuotationComparePopupComponent,
        RnsQuotationDeletePopupComponent
    ],
    entryComponents: [
        RnsQuotationComponent,
        RnsQuotationDialogComponent,
        RnsQuotationPopupComponent,
        RnsQuotationCompareDialogComponent,
        RnsQuotationComparePopupComponent,
        RnsQuotationDeleteDialogComponent,
        RnsQuotationDeletePopupComponent
    ],
    providers: [RnsQuotationService, RnsQuotationPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationModule {}
