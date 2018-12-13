import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsSourceTeamDtlService,
    RnsSourceTeamDtlPopupService,
    RnsSourceTeamDtlComponent,
    RnsSourceTeamDtlDetailComponent,
    RnsSourceTeamDtlDialogComponent,
    RnsSourceTeamDtlPopupComponent,
    RnsSourceTeamDtlDeletePopupComponent,
    RnsSourceTeamDtlDeleteDialogComponent,
    rnsSourceTeamDtlRoute,
    rnsSourceTeamDtlPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsSourceTeamDtlRoute,
    ...rnsSourceTeamDtlPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsSourceTeamDtlComponent,
        RnsSourceTeamDtlDetailComponent,
        RnsSourceTeamDtlDialogComponent,
        RnsSourceTeamDtlDeleteDialogComponent,
        RnsSourceTeamDtlPopupComponent,
        RnsSourceTeamDtlDeletePopupComponent,
    ],
    entryComponents: [
        RnsSourceTeamDtlComponent,
        RnsSourceTeamDtlDialogComponent,
        RnsSourceTeamDtlPopupComponent,
        RnsSourceTeamDtlDeleteDialogComponent,
        RnsSourceTeamDtlDeletePopupComponent,
    ],
    providers: [
        RnsSourceTeamDtlService,
        RnsSourceTeamDtlPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsSourceTeamDtlModule {}
