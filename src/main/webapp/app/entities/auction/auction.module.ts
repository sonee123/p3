import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { P3SharedModule } from '../../shared';
import {
    AuctionService,
    AuctionPopupService,
    AuctionComponent,
    AuctionDetailComponent,
    AuctionDialogComponent,
    AuctionPopupComponent,
    AuctionDeletePopupComponent,
    AuctionDeleteDialogComponent,
    auctionRoute,
    auctionPopupRoute
} from './';

import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { NotifierModule, NotifierOptions } from 'angular-notifier';

/**
 * Custom angular notifier options
 */
const customNotifierOptions: NotifierOptions = {
    position: {
        horizontal: {
            position: 'middle',
            distance: 12
        },
        vertical: {
            position: 'top',
            distance: 12,
            gap: 10
        }
    },
    theme: 'material',
    behaviour: {
        autoHide: 2000,
        onClick: 'hide',
        onMouseover: 'pauseAutoHide',
        showDismissButton: true,
        stacking: 4
    },
    animations: {
        enabled: true,
        show: {
            preset: 'slide',
            speed: 300,
            easing: 'ease'
        },
        hide: {
            preset: 'fade',
            speed: 300,
            easing: 'ease',
            offset: 50
        },
        shift: {
            speed: 300,
            easing: 'ease'
        },
        overlap: 150
    }
};
const ENTITY_STATES = [...auctionRoute, ...auctionPopupRoute];

@NgModule({
    imports: [
        P3SharedModule,
        OwlDateTimeModule,
        OwlNativeDateTimeModule,
        RouterModule.forChild(ENTITY_STATES),
        NotifierModule.withConfig(customNotifierOptions)
    ],
    declarations: [
        AuctionComponent,
        AuctionDetailComponent,
        AuctionDialogComponent,
        AuctionDeleteDialogComponent,
        AuctionPopupComponent,
        AuctionDeletePopupComponent
    ],
    entryComponents: [
        AuctionComponent,
        AuctionDialogComponent,
        AuctionPopupComponent,
        AuctionDeleteDialogComponent,
        AuctionDeletePopupComponent
    ],
    providers: [AuctionService, AuctionPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsAuctionModule {}
