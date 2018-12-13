import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsBuyerMasterService,
    RnsBuyerMasterPopupService,
    RnsBuyerMasterComponent,
    RnsBuyerMasterDetailComponent,
    RnsBuyerMasterDialogComponent,
    RnsBuyerMasterPopupComponent,
    RnsBuyerMasterDeletePopupComponent,
    RnsBuyerMasterDeleteDialogComponent,
    rnsBuyerMasterRoute,
    rnsBuyerMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsBuyerMasterRoute,
    ...rnsBuyerMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsBuyerMasterComponent,
        RnsBuyerMasterDetailComponent,
        RnsBuyerMasterDialogComponent,
        RnsBuyerMasterDeleteDialogComponent,
        RnsBuyerMasterPopupComponent,
        RnsBuyerMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsBuyerMasterComponent,
        RnsBuyerMasterDialogComponent,
        RnsBuyerMasterPopupComponent,
        RnsBuyerMasterDeleteDialogComponent,
        RnsBuyerMasterDeletePopupComponent,
    ],
    providers: [
        RnsBuyerMasterService,
        RnsBuyerMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsBuyerMasterModule {}
