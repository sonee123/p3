import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    AuctionPauseDetailsService,
    AuctionPauseDetailsPopupService,
    AuctionPauseDetailsComponent,
    AuctionPauseDetailsDetailComponent,
    AuctionPauseDetailsDialogComponent,
    AuctionPauseDetailsPopupComponent,
    AuctionPauseDetailsDeletePopupComponent,
    AuctionPauseDetailsDeleteDialogComponent,
    auctionPauseDetailsRoute,
    auctionPauseDetailsPopupRoute,
    AuctionPausedSocketService
} from './';

const ENTITY_STATES = [
    ...auctionPauseDetailsRoute,
    ...auctionPauseDetailsPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AuctionPauseDetailsComponent,
        AuctionPauseDetailsDetailComponent,
        AuctionPauseDetailsDialogComponent,
        AuctionPauseDetailsDeleteDialogComponent,
        AuctionPauseDetailsPopupComponent,
        AuctionPauseDetailsDeletePopupComponent,
    ],
    entryComponents: [
        AuctionPauseDetailsComponent,
        AuctionPauseDetailsDialogComponent,
        AuctionPauseDetailsPopupComponent,
        AuctionPauseDetailsDeleteDialogComponent,
        AuctionPauseDetailsDeletePopupComponent,
    ],
    providers: [
        AuctionPauseDetailsService,
        AuctionPauseDetailsPopupService,
        AuctionPausedSocketService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsAuctionPauseDetailsModule {}
