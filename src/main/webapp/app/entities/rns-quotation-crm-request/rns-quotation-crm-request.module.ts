import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { P3SharedModule } from 'app/shared';
import {
    RnsQuotationCrmRequestService,
    RnsQuotationCrmRequestPopupService,
    RnsQuotationCrmRequestComponent,
    RnsQuotationCrmRequestDetailComponent,
    RnsQuotationCrmRequestDialogComponent,
    RnsQuotationCrmRequestPopupComponent,
    RnsQuotationCrmRequestDeletePopupComponent,
    RnsQuotationCrmRequestDeleteDialogComponent,
    rnsQuotationCrmRequestRoute,
    rnsQuotationCrmRequestPopupRoute
} from './';

const ENTITY_STATES = [...rnsQuotationCrmRequestRoute, ...rnsQuotationCrmRequestPopupRoute];

@NgModule({
    imports: [P3SharedModule, OwlDateTimeModule, OwlNativeDateTimeModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RnsQuotationCrmRequestComponent,
        RnsQuotationCrmRequestDetailComponent,
        RnsQuotationCrmRequestDialogComponent,
        RnsQuotationCrmRequestDeleteDialogComponent,
        RnsQuotationCrmRequestPopupComponent,
        RnsQuotationCrmRequestDeletePopupComponent
    ],
    entryComponents: [
        RnsQuotationCrmRequestComponent,
        RnsQuotationCrmRequestDialogComponent,
        RnsQuotationCrmRequestPopupComponent,
        RnsQuotationCrmRequestDeleteDialogComponent,
        RnsQuotationCrmRequestDeletePopupComponent
    ],
    providers: [RnsQuotationCrmRequestService, RnsQuotationCrmRequestPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationCrmRequestModule {}
