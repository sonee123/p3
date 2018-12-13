import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsEmpMasterService,
    RnsEmpMasterPopupService,
    RnsEmpMasterComponent,
    RnsEmpMasterDetailComponent,
    RnsEmpMasterDialogComponent,
    RnsEmpMasterPopupComponent,
    RnsEmpMasterDeletePopupComponent,
    RnsEmpMasterDeleteDialogComponent,
    rnsEmpMasterRoute,
    rnsEmpMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsEmpMasterRoute,
    ...rnsEmpMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsEmpMasterComponent,
        RnsEmpMasterDetailComponent,
        RnsEmpMasterDialogComponent,
        RnsEmpMasterDeleteDialogComponent,
        RnsEmpMasterPopupComponent,
        RnsEmpMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsEmpMasterComponent,
        RnsEmpMasterDialogComponent,
        RnsEmpMasterPopupComponent,
        RnsEmpMasterDeleteDialogComponent,
        RnsEmpMasterDeletePopupComponent,
    ],
    providers: [
        RnsEmpMasterService,
        RnsEmpMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsEmpMasterModule {}
