import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsRefrDetailsService,
    RnsRefrDetailsPopupService,
    RnsRefrDetailsComponent,
    RnsRefrDetailsDetailComponent,
    RnsRefrDetailsDialogComponent,
    RnsRefrDetailsPopupComponent,
    RnsRefrDetailsDeletePopupComponent,
    RnsRefrDetailsDeleteDialogComponent,
    rnsRefrDetailsRoute,
    rnsRefrDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsRefrDetailsRoute,
    ...rnsRefrDetailsPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsRefrDetailsComponent,
        RnsRefrDetailsDetailComponent,
        RnsRefrDetailsDialogComponent,
        RnsRefrDetailsDeleteDialogComponent,
        RnsRefrDetailsPopupComponent,
        RnsRefrDetailsDeletePopupComponent,
    ],
    entryComponents: [
        RnsRefrDetailsComponent,
        RnsRefrDetailsDialogComponent,
        RnsRefrDetailsPopupComponent,
        RnsRefrDetailsDeleteDialogComponent,
        RnsRefrDetailsDeletePopupComponent,
    ],
    providers: [
        RnsRefrDetailsService,
        RnsRefrDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsRefrDetailsModule {}
