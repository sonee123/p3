import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    CurrencyService,
    CurrencyPopupService,
    CurrencyComponent,
    CurrencyDetailComponent,
    CurrencyDialogComponent,
    CurrencyPopupComponent,
    CurrencyDeletePopupComponent,
    CurrencyDeleteDialogComponent,
    currencyRoute,
    currencyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...currencyRoute,
    ...currencyPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CurrencyComponent,
        CurrencyDetailComponent,
        CurrencyDialogComponent,
        CurrencyDeleteDialogComponent,
        CurrencyPopupComponent,
        CurrencyDeletePopupComponent,
    ],
    entryComponents: [
        CurrencyComponent,
        CurrencyDialogComponent,
        CurrencyPopupComponent,
        CurrencyDeleteDialogComponent,
        CurrencyDeletePopupComponent,
    ],
    providers: [
        CurrencyService,
        CurrencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsCurrencyModule {}
