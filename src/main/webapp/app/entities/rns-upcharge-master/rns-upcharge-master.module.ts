import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {P3SharedModule} from '../../shared';
import {
    RnsUpchargeMasterService,
    RnsUpchargeMasterPopupService,
    RnsUpchargeMasterComponent,
    RnsUpchargeMasterDetailComponent,
    RnsUpchargeMasterDialogComponent,
    RnsUpchargeMasterPopupComponent,
    RnsUpchargeMasterDeletePopupComponent,
    RnsUpchargeMasterDeleteDialogComponent,
    rnsUpchargeMasterRoute,
    rnsUpchargeMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsUpchargeMasterRoute,
    ...rnsUpchargeMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsUpchargeMasterComponent,
        RnsUpchargeMasterDetailComponent,
        RnsUpchargeMasterDialogComponent,
        RnsUpchargeMasterDeleteDialogComponent,
        RnsUpchargeMasterPopupComponent,
        RnsUpchargeMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsUpchargeMasterComponent,
        RnsUpchargeMasterDialogComponent,
        RnsUpchargeMasterPopupComponent,
        RnsUpchargeMasterDeleteDialogComponent,
        RnsUpchargeMasterDeletePopupComponent,
    ],
    providers: [
        RnsUpchargeMasterService,
        RnsUpchargeMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsUpchargeMasterModule {
}
