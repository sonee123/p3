import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsUpchargeDtlService,
    RnsUpchargeDtlPopupService,
    RnsUpchargeDtlComponent,
    RnsUpchargeDtlDetailComponent,
    RnsUpchargeDtlDialogComponent,
    RnsUpchargeDtlPopupComponent,
    RnsUpchargeDtlSelectPopupComponent,
    RnsUpchargeDtlDeletePopupComponent,
    RnsUpchargeDtlDeleteDialogComponent,
    rnsUpchargeDtlRoute,
    rnsUpchargeDtlPopupRoute
} from './';
import { RnsUpchargeDtlSelectDialogComponent } from './rns-upcharge-dtl-select-dialog.component';
import { RnsQuotationComponent } from '../../rnsmain';
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
const ENTITY_STATES = [...rnsUpchargeDtlRoute, ...rnsUpchargeDtlPopupRoute];

@NgModule({
    imports: [P3SharedModule, RouterModule.forChild(ENTITY_STATES), NotifierModule.withConfig(customNotifierOptions)],
    declarations: [
        RnsUpchargeDtlComponent,
        RnsUpchargeDtlDetailComponent,
        RnsUpchargeDtlDialogComponent,
        RnsUpchargeDtlSelectDialogComponent,
        RnsUpchargeDtlDeleteDialogComponent,
        RnsUpchargeDtlPopupComponent,
        RnsUpchargeDtlSelectPopupComponent,
        RnsUpchargeDtlDeletePopupComponent
    ],
    entryComponents: [
        RnsUpchargeDtlComponent,
        RnsUpchargeDtlDialogComponent,
        RnsUpchargeDtlSelectDialogComponent,
        RnsUpchargeDtlPopupComponent,
        RnsUpchargeDtlSelectPopupComponent,
        RnsUpchargeDtlDeleteDialogComponent,
        RnsUpchargeDtlDeletePopupComponent
    ],
    providers: [RnsUpchargeDtlService, RnsUpchargeDtlPopupService, RnsQuotationComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsUpchargeDtlModule {}
