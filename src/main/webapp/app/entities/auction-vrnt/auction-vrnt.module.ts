import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    AuctionVrntService,
    AuctionVrntPopupService,
    AuctionVrntComponent,
    AuctionVrntDetailComponent,
    AuctionVrntDialogComponent,
    AuctionVrntDialogGetByVariantComponent,
    AuctionVrntPopupComponent,
    AuctionVrntDeletePopupComponent,
    AuctionVrntGetByVariantPopupComponent,
    AuctionVrntDeleteDialogComponent,
    auctionVrntRoute,
    auctionVrntPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auctionVrntRoute,
    ...auctionVrntPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AuctionVrntComponent,
        AuctionVrntDetailComponent,
        AuctionVrntDialogComponent,
        AuctionVrntDialogGetByVariantComponent,
        AuctionVrntDeleteDialogComponent,
        AuctionVrntPopupComponent,
        AuctionVrntGetByVariantPopupComponent,
        AuctionVrntDeletePopupComponent,
    ],
    entryComponents: [
        AuctionVrntComponent,
        AuctionVrntDialogComponent,
        AuctionVrntDialogGetByVariantComponent,
        AuctionVrntPopupComponent,
        AuctionVrntGetByVariantPopupComponent,
        AuctionVrntDeleteDialogComponent,
        AuctionVrntDeletePopupComponent,
    ],
    providers: [
        AuctionVrntService,
        AuctionVrntPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsAuctionVrntModule {}
