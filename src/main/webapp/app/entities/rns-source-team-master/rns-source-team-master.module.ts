import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsSourceTeamMasterService,
    RnsSourceTeamMasterPopupService,
    RnsSourceTeamMasterComponent,
    RnsSourceTeamMasterDetailComponent,
    RnsSourceTeamMasterDialogComponent,
    RnsSourceTeamMasterPopupComponent,
    RnsSourceTeamMasterDeletePopupComponent,
    RnsSourceTeamMasterDeleteDialogComponent,
    rnsSourceTeamMasterRoute,
    rnsSourceTeamMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsSourceTeamMasterRoute,
    ...rnsSourceTeamMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsSourceTeamMasterComponent,
        RnsSourceTeamMasterDetailComponent,
        RnsSourceTeamMasterDialogComponent,
        RnsSourceTeamMasterDeleteDialogComponent,
        RnsSourceTeamMasterPopupComponent,
        RnsSourceTeamMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsSourceTeamMasterComponent,
        RnsSourceTeamMasterDialogComponent,
        RnsSourceTeamMasterPopupComponent,
        RnsSourceTeamMasterDeleteDialogComponent,
        RnsSourceTeamMasterDeletePopupComponent,
    ],
    providers: [
        RnsSourceTeamMasterService,
        RnsSourceTeamMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsSourceTeamMasterModule {}
