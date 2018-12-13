import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsForwardTypeMasterService,
    RnsForwardTypeMasterPopupService,
    RnsForwardTypeMasterComponent,
    RnsForwardTypeMasterDetailComponent,
    RnsForwardTypeMasterDialogComponent,
    RnsForwardTypeMasterPopupComponent,
    RnsForwardTypeMasterDeletePopupComponent,
    RnsForwardTypeMasterDeleteDialogComponent,
    rnsForwardTypeMasterRoute,
    rnsForwardTypeMasterPopupRoute,
    RnsForwardTypeMasterResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rnsForwardTypeMasterRoute,
    ...rnsForwardTypeMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsForwardTypeMasterComponent,
        RnsForwardTypeMasterDetailComponent,
        RnsForwardTypeMasterDialogComponent,
        RnsForwardTypeMasterDeleteDialogComponent,
        RnsForwardTypeMasterPopupComponent,
        RnsForwardTypeMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsForwardTypeMasterComponent,
        RnsForwardTypeMasterDialogComponent,
        RnsForwardTypeMasterPopupComponent,
        RnsForwardTypeMasterDeleteDialogComponent,
        RnsForwardTypeMasterDeletePopupComponent,
    ],
    providers: [
        RnsForwardTypeMasterService,
        RnsForwardTypeMasterPopupService,
        RnsForwardTypeMasterResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsForwardTypeMasterModule {}
