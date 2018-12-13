import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsDelTermsMasterService,
    RnsDelTermsMasterPopupService,
    RnsDelTermsMasterComponent,
    RnsDelTermsMasterDetailComponent,
    RnsDelTermsMasterDialogComponent,
    RnsDelTermsMasterPopupComponent,
    RnsDelTermsMasterDeletePopupComponent,
    RnsDelTermsMasterDeleteDialogComponent,
    rnsDelTermsMasterRoute,
    rnsDelTermsMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsDelTermsMasterRoute,
    ...rnsDelTermsMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsDelTermsMasterComponent,
        RnsDelTermsMasterDetailComponent,
        RnsDelTermsMasterDialogComponent,
        RnsDelTermsMasterDeleteDialogComponent,
        RnsDelTermsMasterPopupComponent,
        RnsDelTermsMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsDelTermsMasterComponent,
        RnsDelTermsMasterDialogComponent,
        RnsDelTermsMasterPopupComponent,
        RnsDelTermsMasterDeleteDialogComponent,
        RnsDelTermsMasterDeletePopupComponent,
    ],
    providers: [
        RnsDelTermsMasterService,
        RnsDelTermsMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsDelTermsMasterModule {}
