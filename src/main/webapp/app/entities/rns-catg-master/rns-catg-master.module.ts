import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DualListBoxModule } from 'ng2-dual-list-box';
import {
    RnsCatgMasterService,
    RnsCatgMasterPopupService,
    RnsCatgMasterComponent,
    RnsCatgMasterDetailComponent,
    RnsCatgMasterDialogComponent,
    RnsCatgMasterPopupComponent,
    RnsCatgMasterDeletePopupComponent,
    RnsCatgMasterDeleteDialogComponent,
    RnsCatgUserDialogComponent,
    RnsCatgUserPopupComponent,
    rnsCatgMasterRoute,
    rnsCatgMasterPopupRoute
} from './';
import { P3SharedModule } from 'app/shared';
import { ReactiveFormsModule } from '@angular/forms';
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

const ENTITY_STATES = [...rnsCatgMasterRoute, ...rnsCatgMasterPopupRoute];

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(ENTITY_STATES),
        DualListBoxModule.forRoot(),
        ReactiveFormsModule,
        NotifierModule.withConfig(customNotifierOptions)
    ],
    declarations: [
        RnsCatgMasterComponent,
        RnsCatgMasterDetailComponent,
        RnsCatgMasterDialogComponent,
        RnsCatgUserDialogComponent,
        RnsCatgMasterDeleteDialogComponent,
        RnsCatgMasterPopupComponent,
        RnsCatgUserPopupComponent,
        RnsCatgMasterDeletePopupComponent
    ],
    entryComponents: [
        RnsCatgMasterComponent,
        RnsCatgMasterDialogComponent,
        RnsCatgMasterPopupComponent,
        RnsCatgUserDialogComponent,
        RnsCatgUserPopupComponent,
        RnsCatgMasterDeleteDialogComponent,
        RnsCatgMasterDeletePopupComponent
    ],
    providers: [RnsCatgMasterService, RnsCatgMasterPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsCatgMasterModule {}
