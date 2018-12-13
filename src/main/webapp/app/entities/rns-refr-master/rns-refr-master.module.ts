import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsRefrMasterService,
    RnsRefrMasterPopupService,
    RnsRefrMasterComponent,
    RnsRefrMasterDetailComponent,
    RnsRefrMasterDialogComponent,
    RnsRefrMasterPopupComponent,
    RnsRefrMasterDeletePopupComponent,
    RnsRefrMasterDeleteDialogComponent,
    rnsRefrMasterRoute,
    rnsRefrMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsRefrMasterRoute,
    ...rnsRefrMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsRefrMasterComponent,
        RnsRefrMasterDetailComponent,
        RnsRefrMasterDialogComponent,
        RnsRefrMasterDeleteDialogComponent,
        RnsRefrMasterPopupComponent,
        RnsRefrMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsRefrMasterComponent,
        RnsRefrMasterDialogComponent,
        RnsRefrMasterPopupComponent,
        RnsRefrMasterDeleteDialogComponent,
        RnsRefrMasterDeletePopupComponent,
    ],
    providers: [
        RnsRefrMasterService,
        RnsRefrMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsRefrMasterModule {}
