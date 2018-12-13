import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import { P3AdminModule } from '../../admin/admin.module';
import {
    RnsQuotationVendorsService,
    RnsQuotationVendorsPopupService,
    RnsQuotationVendorsComponent,
    RnsQuotationVendorsDetailComponent,
    RnsQuotationVendorsDialogComponent,
    RnsQuotationVendorsPopupComponent,
    RnsQuotationVendorsDeletePopupComponent,
    RnsQuotationVendorsDeleteDialogComponent,
    rnsQuotationVendorsRoute,
    rnsQuotationVendorsPopupRoute,
    RnsQuotationVendorsSavePopupComponent,
    RnsQuotationVendorsPopupSaveComponent
} from './';

const ENTITY_STATES = [
    ...rnsQuotationVendorsRoute,
    ...rnsQuotationVendorsPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        P3AdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsQuotationVendorsComponent,
        RnsQuotationVendorsDetailComponent,
        RnsQuotationVendorsDialogComponent,
        RnsQuotationVendorsDeleteDialogComponent,
        RnsQuotationVendorsPopupComponent,
        RnsQuotationVendorsDeletePopupComponent,
        RnsQuotationVendorsSavePopupComponent,
        RnsQuotationVendorsPopupSaveComponent
    ],
    entryComponents: [
        RnsQuotationVendorsComponent,
        RnsQuotationVendorsDialogComponent,
        RnsQuotationVendorsPopupComponent,
        RnsQuotationVendorsDeleteDialogComponent,
        RnsQuotationVendorsDeletePopupComponent,
        RnsQuotationVendorsSavePopupComponent,
        RnsQuotationVendorsPopupSaveComponent
    ],
    providers: [
        RnsQuotationVendorsService,
        RnsQuotationVendorsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationVendorsModule {}
