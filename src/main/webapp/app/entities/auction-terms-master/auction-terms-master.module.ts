import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    AuctionTermsMasterService,
    AuctionTermsMasterPopupService,
    AuctionTermsMasterComponent,
    AuctionTermsMasterDetailComponent,
    AuctionTermsMasterDialogComponent,
    AuctionTermsMasterPopupComponent,
    AuctionTermsMasterAgreeDialogComponent,
    AuctionTermsMasterAgreePopupComponent,
    AuctionTermsMasterDeletePopupComponent,
    AuctionTermsMasterDeleteDialogComponent,
    auctionTermsMasterRoute,
    auctionTermsMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auctionTermsMasterRoute,
    ...auctionTermsMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AuctionTermsMasterComponent,
        AuctionTermsMasterDetailComponent,
        AuctionTermsMasterDialogComponent,
        AuctionTermsMasterAgreeDialogComponent,
        AuctionTermsMasterDeleteDialogComponent,
        AuctionTermsMasterPopupComponent,
        AuctionTermsMasterAgreePopupComponent,
        AuctionTermsMasterDeletePopupComponent,
    ],
    entryComponents: [
        AuctionTermsMasterComponent,
        AuctionTermsMasterDialogComponent,
        AuctionTermsMasterAgreeDialogComponent,
        AuctionTermsMasterPopupComponent,
        AuctionTermsMasterAgreePopupComponent,
        AuctionTermsMasterDeleteDialogComponent,
        AuctionTermsMasterDeletePopupComponent,
    ],
    providers: [
        AuctionTermsMasterService,
        AuctionTermsMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsAuctionTermsMasterModule {}
