import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsUomMasterService,
    RnsUomMasterPopupService,
    RnsUomMasterComponent,
    RnsUomMasterDetailComponent,
    RnsUomMasterDialogComponent,
    RnsUomMasterPopupComponent,
    RnsUomMasterDeletePopupComponent,
    RnsUomMasterDeleteDialogComponent,
    rnsUomMasterRoute,
    rnsUomMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsUomMasterRoute,
    ...rnsUomMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsUomMasterComponent,
        RnsUomMasterDetailComponent,
        RnsUomMasterDialogComponent,
        RnsUomMasterDeleteDialogComponent,
        RnsUomMasterPopupComponent,
        RnsUomMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsUomMasterComponent,
        RnsUomMasterDialogComponent,
        RnsUomMasterPopupComponent,
        RnsUomMasterDeleteDialogComponent,
        RnsUomMasterDeletePopupComponent,
    ],
    providers: [
        RnsUomMasterService,
        RnsUomMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsUomMasterModule {}
