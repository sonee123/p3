import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { P3SharedModule } from '../shared';
import { Ng2Webstorage } from 'ngx-webstorage';
import {
    RNSMAIN_ROUTE,
    RnsmainComponent,
    AuctionListComponent,
    RnsAuctionQuotationComponent,
    RnsQuotationComponent,
    RnsmainviewComponent,
    VariantDataService
} from './';

import { AngularDateTimePickerModule } from 'angular2-datetimepicker';
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
        autoHide: 3000,
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

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(RNSMAIN_ROUTE),
        AngularDateTimePickerModule,
        Ng2Webstorage,
        NotifierModule.withConfig(customNotifierOptions)
    ],
    declarations: [RnsmainComponent, AuctionListComponent, RnsQuotationComponent, RnsAuctionQuotationComponent, RnsmainviewComponent],
    entryComponents: [RnsQuotationComponent, RnsAuctionQuotationComponent],
    providers: [VariantDataService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VendorMainModule {}
