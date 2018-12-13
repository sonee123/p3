import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsVendorRemarkCommentService,
    RnsVendorRemarkCommentPopupService,
    RnsVendorRemarkCommentComponent,
    RnsVendorRemarkCommentDetailComponent,
    RnsVendorRemarkCommentDialogComponent,
    RnsVendorRemarkCommentPopupComponent,
    RnsVendorRemarkCommentDeletePopupComponent,
    RnsVendorRemarkCommentDeleteDialogComponent,
    rnsVendorRemarkCommentRoute,
    rnsVendorRemarkCommentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsVendorRemarkCommentRoute,
    ...rnsVendorRemarkCommentPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsVendorRemarkCommentComponent,
        RnsVendorRemarkCommentDetailComponent,
        RnsVendorRemarkCommentDialogComponent,
        RnsVendorRemarkCommentDeleteDialogComponent,
        RnsVendorRemarkCommentPopupComponent,
        RnsVendorRemarkCommentDeletePopupComponent,
    ],
    entryComponents: [
        RnsVendorRemarkCommentComponent,
        RnsVendorRemarkCommentDialogComponent,
        RnsVendorRemarkCommentPopupComponent,
        RnsVendorRemarkCommentDeleteDialogComponent,
        RnsVendorRemarkCommentDeletePopupComponent,
    ],
    providers: [
        RnsVendorRemarkCommentService,
        RnsVendorRemarkCommentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsVendorRemarkCommentModule {}
