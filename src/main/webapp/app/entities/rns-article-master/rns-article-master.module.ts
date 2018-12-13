import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsArticleMasterService,
    RnsArticleMasterPopupService,
    RnsArticleMasterComponent,
    RnsArticleMasterDetailComponent,
    RnsArticleMasterDialogComponent,
    RnsArticleMasterPopupComponent,
    RnsArticleMasterDeletePopupComponent,
    RnsArticleMasterDeleteDialogComponent,
    rnsArticleMasterRoute,
    rnsArticleMasterPopupRoute,
    RnsArticleMasterSearchPopupComponent,
    RnsArticleMasterSearchComponent,
} from './';

const ENTITY_STATES = [
    ...rnsArticleMasterRoute,
    ...rnsArticleMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsArticleMasterComponent,
        RnsArticleMasterDetailComponent,
        RnsArticleMasterDialogComponent,
        RnsArticleMasterDeleteDialogComponent,
        RnsArticleMasterPopupComponent,
        RnsArticleMasterDeletePopupComponent,
        RnsArticleMasterSearchPopupComponent,
        RnsArticleMasterSearchComponent,
    ],
    entryComponents: [
        RnsArticleMasterComponent,
        RnsArticleMasterDialogComponent,
        RnsArticleMasterPopupComponent,
        RnsArticleMasterDeleteDialogComponent,
        RnsArticleMasterDeletePopupComponent,
        RnsArticleMasterSearchPopupComponent,
        RnsArticleMasterSearchComponent,
    ],
    providers: [
        RnsArticleMasterService,
        RnsArticleMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsArticleMasterModule {}
