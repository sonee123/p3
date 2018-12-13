import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
    RnsFileUploadService,
    RnsFileUploadPopupService,
    RnsFileUploadComponent,
    RnsFileUploadDetailComponent,
    RnsFileUploadDialogComponent,
    RnsFileUploadPopupComponent,
    RnsFileUploadDeletePopupComponent,
    RnsFileUploadDeleteDialogComponent,
    RnsFileImportDialogComponent,
    RnsFileImportPopupComponent,
    RnsRfqFileImportDialogComponent,
    RnsRfqFileImportPopupComponent,
    rnsFileUploadRoute,
    rnsFileUploadPopupRoute
} from './';
import { P3SharedModule } from 'app/shared';
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

const ENTITY_STATES = [...rnsFileUploadRoute, ...rnsFileUploadPopupRoute];

@NgModule({
    imports: [P3SharedModule, RouterModule.forChild(ENTITY_STATES), NotifierModule.withConfig(customNotifierOptions)],
    declarations: [
        RnsFileUploadComponent,
        RnsFileUploadDetailComponent,
        RnsFileUploadDialogComponent,
        RnsFileImportDialogComponent,
        RnsRfqFileImportDialogComponent,
        RnsFileUploadDeleteDialogComponent,
        RnsFileUploadPopupComponent,
        RnsFileImportPopupComponent,
        RnsRfqFileImportPopupComponent,
        RnsFileUploadDeletePopupComponent
    ],
    entryComponents: [
        RnsFileUploadComponent,
        RnsFileUploadDialogComponent,
        RnsFileUploadPopupComponent,
        RnsFileImportDialogComponent,
        RnsRfqFileImportDialogComponent,
        RnsFileImportPopupComponent,
        RnsRfqFileImportPopupComponent,
        RnsFileUploadDeleteDialogComponent,
        RnsFileUploadDeletePopupComponent
    ],
    providers: [RnsFileUploadService, RnsFileUploadPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsFileUploadModule {}
