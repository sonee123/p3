import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    AuctionVarDetailsService,
    AuctionVarDetailsPopupService,
    AuctionVarDetailsComponent,
    AuctionVarDetailsDetailComponent,
    AuctionVarDetailsDialogComponent,
    AuctionVarDetailsPopupComponent,
    AuctionVarDetailsDeletePopupComponent,
    AuctionVarDetailsDeleteDialogComponent,
    auctionVarDetailsRoute,
    auctionVarDetailsPopupRoute
} from './';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';

const ENTITY_STATES = [...auctionVarDetailsRoute, ...auctionVarDetailsPopupRoute];

@NgModule({
    imports: [P3SharedModule, OwlDateTimeModule, OwlNativeDateTimeModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AuctionVarDetailsComponent,
        AuctionVarDetailsDetailComponent,
        AuctionVarDetailsDialogComponent,
        AuctionVarDetailsDeleteDialogComponent,
        AuctionVarDetailsPopupComponent,
        AuctionVarDetailsDeletePopupComponent
    ],
    entryComponents: [
        AuctionVarDetailsComponent,
        AuctionVarDetailsDialogComponent,
        AuctionVarDetailsPopupComponent,
        AuctionVarDetailsDeleteDialogComponent,
        AuctionVarDetailsDeletePopupComponent
    ],
    providers: [AuctionVarDetailsService, AuctionVarDetailsPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsAuctionVarDetailsModule {}
