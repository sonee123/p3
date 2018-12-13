import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsSectorMasterService,
    RnsSectorMasterPopupService,
    RnsSectorMasterComponent,
    RnsSectorMasterDetailComponent,
    RnsSectorMasterDialogComponent,
    RnsSectorMasterPopupComponent,
    RnsSectorMasterDeletePopupComponent,
    RnsSectorMasterDeleteDialogComponent,
    rnsSectorMasterRoute,
    rnsSectorMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsSectorMasterRoute,
    ...rnsSectorMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsSectorMasterComponent,
        RnsSectorMasterDetailComponent,
        RnsSectorMasterDialogComponent,
        RnsSectorMasterDeleteDialogComponent,
        RnsSectorMasterPopupComponent,
        RnsSectorMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsSectorMasterComponent,
        RnsSectorMasterDialogComponent,
        RnsSectorMasterPopupComponent,
        RnsSectorMasterDeleteDialogComponent,
        RnsSectorMasterDeletePopupComponent,
    ],
    providers: [
        RnsSectorMasterService,
        RnsSectorMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsSectorMasterModule {}
