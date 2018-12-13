import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    MessageBodyService,
    MessageBodyPopupService,
    MessageBodyComponent,
    MessageBodyDetailComponent,
    MessageBodyDialogComponent,
    MessageBodyPopupComponent,
    MessageBodyDeletePopupComponent,
    MessageBodyDeleteDialogComponent,
    messageBodyRoute,
    messageBodyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...messageBodyRoute,
    ...messageBodyPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MessageBodyComponent,
        MessageBodyDetailComponent,
        MessageBodyDialogComponent,
        MessageBodyDeleteDialogComponent,
        MessageBodyPopupComponent,
        MessageBodyDeletePopupComponent,
    ],
    entryComponents: [
        MessageBodyComponent,
        MessageBodyDialogComponent,
        MessageBodyPopupComponent,
        MessageBodyDeleteDialogComponent,
        MessageBodyDeletePopupComponent,
    ],
    providers: [
        MessageBodyService,
        MessageBodyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsMessageBodyModule {}
