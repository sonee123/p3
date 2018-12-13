import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsTaxTermsMasterService,
    RnsTaxTermsMasterPopupService,
    RnsTaxTermsMasterComponent,
    RnsTaxTermsMasterDetailComponent,
    RnsTaxTermsMasterDialogComponent,
    RnsTaxTermsMasterPopupComponent,
    RnsTaxTermsMasterDeletePopupComponent,
    RnsTaxTermsMasterDeleteDialogComponent,
    rnsTaxTermsMasterRoute,
    rnsTaxTermsMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rnsTaxTermsMasterRoute,
    ...rnsTaxTermsMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RnsTaxTermsMasterComponent,
        RnsTaxTermsMasterDetailComponent,
        RnsTaxTermsMasterDialogComponent,
        RnsTaxTermsMasterDeleteDialogComponent,
        RnsTaxTermsMasterPopupComponent,
        RnsTaxTermsMasterDeletePopupComponent,
    ],
    entryComponents: [
        RnsTaxTermsMasterComponent,
        RnsTaxTermsMasterDialogComponent,
        RnsTaxTermsMasterPopupComponent,
        RnsTaxTermsMasterDeleteDialogComponent,
        RnsTaxTermsMasterDeletePopupComponent,
    ],
    providers: [
        RnsTaxTermsMasterService,
        RnsTaxTermsMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsTaxTermsMasterModule {}
