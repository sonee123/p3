import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsDelPlaceMasterService,
    RnsDelPlaceMasterPopupService,
    RnsDelPlaceMasterComponent,
    RnsDelPlaceMasterDetailComponent,
    RnsDelPlaceMasterDialogComponent,
    RnsDelPlaceMasterPopupComponent,
    RnsDelPlaceMasterDeletePopupComponent,
    RnsDelPlaceMasterDeleteDialogComponent,
    rnsDelPlaceMasterRoute,
    rnsDelPlaceMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsDelPlaceMasterRoute,
    ...rnsDelPlaceMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsDelPlaceMasterComponent,
        RnsDelPlaceMasterDetailComponent,
        RnsDelPlaceMasterDialogComponent,
        RnsDelPlaceMasterDeleteDialogComponent,
        RnsDelPlaceMasterPopupComponent,
        RnsDelPlaceMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsDelPlaceMasterComponent,
        RnsDelPlaceMasterDialogComponent,
        RnsDelPlaceMasterPopupComponent,
        RnsDelPlaceMasterDeleteDialogComponent,
        RnsDelPlaceMasterDeletePopupComponent,
    ],
    providers: [
        RnsDelPlaceMasterService,
        RnsDelPlaceMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsDelPlaceMasterModule {}
