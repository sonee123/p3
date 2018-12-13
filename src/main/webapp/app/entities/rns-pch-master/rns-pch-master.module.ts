import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsPchMasterService,
    RnsPchMasterPopupService,
    RnsPchMasterComponent,
    RnsPchMasterDetailComponent,
    RnsPchMasterDialogComponent,
    RnsPchMasterPopupComponent,
    RnsPchMasterDeletePopupComponent,
    RnsPchMasterDeleteDialogComponent,
    rnsPchMasterRoute,
    rnsPchMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsPchMasterRoute,
    ...rnsPchMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsPchMasterComponent,
        RnsPchMasterDetailComponent,
        RnsPchMasterDialogComponent,
        RnsPchMasterDeleteDialogComponent,
        RnsPchMasterPopupComponent,
        RnsPchMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsPchMasterComponent,
        RnsPchMasterDialogComponent,
        RnsPchMasterPopupComponent,
        RnsPchMasterDeleteDialogComponent,
        RnsPchMasterDeletePopupComponent,
    ],
    providers: [
        RnsPchMasterService,
        RnsPchMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsPchMasterModule {}
