import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsTypeMasterService,
    RnsTypeMasterPopupService,
    RnsTypeMasterComponent,
    RnsTypeMasterDetailComponent,
    RnsTypeMasterDialogComponent,
    RnsTypeMasterPopupComponent,
    RnsTypeMasterDeletePopupComponent,
    RnsTypeMasterDeleteDialogComponent,
    rnsTypeMasterRoute,
    rnsTypeMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsTypeMasterRoute,
    ...rnsTypeMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsTypeMasterComponent,
        RnsTypeMasterDetailComponent,
        RnsTypeMasterDialogComponent,
        RnsTypeMasterDeleteDialogComponent,
        RnsTypeMasterPopupComponent,
        RnsTypeMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsTypeMasterComponent,
        RnsTypeMasterDialogComponent,
        RnsTypeMasterPopupComponent,
        RnsTypeMasterDeleteDialogComponent,
        RnsTypeMasterDeletePopupComponent,
    ],
    providers: [
        RnsTypeMasterService,
        RnsTypeMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsTypeMasterModule {}
