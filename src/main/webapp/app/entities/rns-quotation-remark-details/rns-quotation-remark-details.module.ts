import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { P3SharedModule } from '../../shared';
import {
    RnsQuotationRemarkDetailsService,
    RnsQuotationRemarkDetailsPopupService,
    RnsQuotationRemarkDetailsComponent,
    RnsQuotationRemarkDetailsDetailComponent,
    RnsQuotationRemarkDetailsDialogComponent,
    RnsQuotationRemarkDetailsPopupComponent,
    RnsQuotationRemarkDetailsDeletePopupComponent,
    RnsQuotationRemarkDetailsDeleteDialogComponent,
    RnsQuotationRemarkDetailsCallRemarkComponent,
    RnsQuotationRemarkDetailsCallRemarkPopupComponent,
    rnsQuotationRemarkDetailsRoute,
    rnsQuotationRemarkDetailsPopupRoute
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
const ENTITY_STATES = [...rnsQuotationRemarkDetailsRoute, ...rnsQuotationRemarkDetailsPopupRoute];

@NgModule({
    imports: [P3SharedModule, RouterModule.forChild(ENTITY_STATES), NotifierModule.withConfig(customNotifierOptions)],
    declarations: [
        RnsQuotationRemarkDetailsComponent,
        RnsQuotationRemarkDetailsDetailComponent,
        RnsQuotationRemarkDetailsDialogComponent,
        RnsQuotationRemarkDetailsDeleteDialogComponent,
        RnsQuotationRemarkDetailsPopupComponent,
        RnsQuotationRemarkDetailsDeletePopupComponent,
        RnsQuotationRemarkDetailsCallRemarkComponent,
        RnsQuotationRemarkDetailsCallRemarkPopupComponent
    ],
    entryComponents: [
        RnsQuotationRemarkDetailsComponent,
        RnsQuotationRemarkDetailsDialogComponent,
        RnsQuotationRemarkDetailsPopupComponent,
        RnsQuotationRemarkDetailsDeleteDialogComponent,
        RnsQuotationRemarkDetailsDeletePopupComponent,
        RnsQuotationRemarkDetailsCallRemarkComponent,
        RnsQuotationRemarkDetailsCallRemarkPopupComponent
    ],
    providers: [RnsQuotationRemarkDetailsService, RnsQuotationRemarkDetailsPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsQuotationRemarkDetailsModule {}
