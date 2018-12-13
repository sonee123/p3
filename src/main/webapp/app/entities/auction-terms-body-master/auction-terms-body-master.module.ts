import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    AuctionTermsBodyMasterService,
    AuctionTermsBodyMasterPopupService,
    AuctionTermsBodyMasterComponent,
    AuctionTermsBodyMasterDetailComponent,
    AuctionTermsBodyMasterDialogComponent,
    AuctionTermsBodyMasterPopupComponent,
    AuctionTermsBodyMasterDeletePopupComponent,
    AuctionTermsBodyMasterDeleteDialogComponent,
    auctionTermsBodyMasterRoute,
    auctionTermsBodyMasterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auctionTermsBodyMasterRoute,
    ...auctionTermsBodyMasterPopupRoute,
];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AuctionTermsBodyMasterComponent,
        AuctionTermsBodyMasterDetailComponent,
        AuctionTermsBodyMasterDialogComponent,
        AuctionTermsBodyMasterDeleteDialogComponent,
        AuctionTermsBodyMasterPopupComponent,
        AuctionTermsBodyMasterDeletePopupComponent,
    ],
    entryComponents: [
        AuctionTermsBodyMasterComponent,
        AuctionTermsBodyMasterDialogComponent,
        AuctionTermsBodyMasterPopupComponent,
        AuctionTermsBodyMasterDeleteDialogComponent,
        AuctionTermsBodyMasterDeletePopupComponent,
    ],
    providers: [
        AuctionTermsBodyMasterService,
        AuctionTermsBodyMasterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsAuctionTermsBodyMasterModule {}
