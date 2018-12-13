import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsPayTermsMasterService,
    RnsPayTermsMasterPopupService,
    RnsPayTermsMasterComponent,
    RnsPayTermsMasterDetailComponent,
    RnsPayTermsMasterDialogComponent,
    RnsPayTermsMasterPopupComponent,
    RnsPayTermsMasterDeletePopupComponent,
    RnsPayTermsMasterDeleteDialogComponent,
    rnsPayTermsMasterRoute,
    rnsPayTermsMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsPayTermsMasterRoute,
    ...rnsPayTermsMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsPayTermsMasterComponent,
        RnsPayTermsMasterDetailComponent,
        RnsPayTermsMasterDialogComponent,
        RnsPayTermsMasterDeleteDialogComponent,
        RnsPayTermsMasterPopupComponent,
        RnsPayTermsMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsPayTermsMasterComponent,
        RnsPayTermsMasterDialogComponent,
        RnsPayTermsMasterPopupComponent,
        RnsPayTermsMasterDeleteDialogComponent,
        RnsPayTermsMasterDeletePopupComponent,
    ],
    providers: [
        RnsPayTermsMasterService,
        RnsPayTermsMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsPayTermsMasterModule {}
