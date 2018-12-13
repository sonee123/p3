import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    VndrPriceService,
    VndrPricePopupService,
    VndrPriceComponent,
    VndrPriceDetailComponent,
    VndrPriceDialogComponent,
    VndrPricePopupComponent,
    VndrPriceDeletePopupComponent,
    VndrPriceDeleteDialogComponent,
    vndrPriceRoute,
    vndrPricePopupRoute,
    VndrPriceSocketService,
    VndrPriceSelectDialogComponent,
    VndrPriceSelectPopupComponent
} from './';
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

const ENTITY_STATES = [...vndrPriceRoute, ...vndrPricePopupRoute];

@NgModule({
    imports: [P3SharedModule, RouterModule.forChild(ENTITY_STATES), NotifierModule.withConfig(customNotifierOptions)],
    declarations: [
        VndrPriceComponent,
        VndrPriceDetailComponent,
        VndrPriceDialogComponent,
        VndrPriceSelectDialogComponent,
        VndrPriceDeleteDialogComponent,
        VndrPricePopupComponent,
        VndrPriceSelectPopupComponent,
        VndrPriceDeletePopupComponent
    ],
    entryComponents: [
        VndrPriceComponent,
        VndrPriceDialogComponent,
        VndrPriceSelectDialogComponent,
        VndrPricePopupComponent,
        VndrPriceSelectPopupComponent,
        VndrPriceDeleteDialogComponent,
        VndrPriceDeletePopupComponent
    ],
    providers: [VndrPriceService, VndrPricePopupService, VndrPriceSocketService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsVndrPriceModule {}
