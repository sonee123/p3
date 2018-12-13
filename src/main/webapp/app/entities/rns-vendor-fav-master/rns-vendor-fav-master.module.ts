import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsVendorFavMasterService,
    RnsVendorFavMasterPopupService,
    RnsVendorFavMasterComponent,
    RnsVendorFavMasterDetailComponent,
    RnsVendorFavMasterDialogComponent,
    RnsVendorFavMasterPopupComponent,
    RnsVendorFavMasterDeletePopupComponent,
    RnsVendorFavMasterDeleteDialogComponent,
    rnsVendorFavMasterRoute,
    rnsVendorFavMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsVendorFavMasterRoute,
    ...rnsVendorFavMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsVendorFavMasterComponent,
        RnsVendorFavMasterDetailComponent,
        RnsVendorFavMasterDialogComponent,
        RnsVendorFavMasterDeleteDialogComponent,
        RnsVendorFavMasterPopupComponent,
        RnsVendorFavMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsVendorFavMasterComponent,
        RnsVendorFavMasterDialogComponent,
        RnsVendorFavMasterPopupComponent,
        RnsVendorFavMasterDeleteDialogComponent,
        RnsVendorFavMasterDeletePopupComponent,
    ],
    providers: [
        RnsVendorFavMasterService,
        RnsVendorFavMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsVendorFavMasterModule {}
