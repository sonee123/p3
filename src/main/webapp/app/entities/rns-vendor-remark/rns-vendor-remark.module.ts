import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsVendorRemarkService,
    RnsVendorRemarkPopupService,
    RnsVendorRemarkComponent,
    RnsVendorRemarkDetailComponent,
    RnsVendorRemarkDialogComponent,
    RnsVendorRemarkPopupComponent,
    RnsVendorRemarkDeletePopupComponent,
    RnsVendorRemarkDeleteDialogComponent,
    rnsVendorRemarkRoute,
    rnsVendorRemarkPopupRoute,
    RnsVendorRemarkSocketService,
    RnsVendorRemarkEditComponent,
    RnsVendorRemarkEdittComponent
} from './';

const ENTITY_STATES = [
    ...rnsVendorRemarkRoute,
    ...rnsVendorRemarkPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsVendorRemarkComponent,
        RnsVendorRemarkDetailComponent,
        RnsVendorRemarkDialogComponent,
        RnsVendorRemarkDeleteDialogComponent,
        RnsVendorRemarkPopupComponent,
        RnsVendorRemarkDeletePopupComponent,
        RnsVendorRemarkEditComponent,
        RnsVendorRemarkEdittComponent
    ],
    entryComponents: [
        RnsVendorRemarkComponent,
        RnsVendorRemarkDialogComponent,
        RnsVendorRemarkPopupComponent,
        RnsVendorRemarkDeleteDialogComponent,
        RnsVendorRemarkDeletePopupComponent,
        RnsVendorRemarkEditComponent,
        RnsVendorRemarkEdittComponent
    ],
    providers: [
        RnsVendorRemarkService,
        RnsVendorRemarkPopupService,
        RnsVendorRemarkSocketService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsVendorRemarkModule {}
