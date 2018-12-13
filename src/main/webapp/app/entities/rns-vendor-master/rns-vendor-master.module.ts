import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { P3SharedModule } from '../../shared';
import {
    RnsVendorMasterService,
    RnsVendorMasterPopupService,
    RnsVendorMasterComponent,
    RnsVendorMasterDetailComponent,
    RnsVendorMasterDialogComponent,
    RnsVendorMasterPopupComponent,
    RnsVendorMasterSearchSelectComponent,
    RnsVendorMasterDeletePopupComponent,
    RnsVendorMasterDeleteDialogComponent,
    RnsVendorMasterSearchSelectPopupComponent,
    rnsVendorMasterRoute,
    rnsVendorMasterPopupRoute
} from './';
import { VendorSearchMasterComponent, VendorSearchMasterPopupComponent } from './vendor-search-master.component';

const ENTITY_STATES = [...rnsVendorMasterRoute, ...rnsVendorMasterPopupRoute];
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
    imports: [P3SharedModule, RouterModule.forChild(ENTITY_STATES), NotifierModule.withConfig(customNotifierOptions)],
    declarations: [
        RnsVendorMasterComponent,
        RnsVendorMasterDetailComponent,
        RnsVendorMasterDialogComponent,
        RnsVendorMasterSearchSelectComponent,
        RnsVendorMasterDeleteDialogComponent,
        RnsVendorMasterPopupComponent,
        RnsVendorMasterDeletePopupComponent,
        RnsVendorMasterSearchSelectPopupComponent,
        VendorSearchMasterComponent,
        VendorSearchMasterPopupComponent
    ],
    entryComponents: [
        RnsVendorMasterComponent,
        RnsVendorMasterDialogComponent,
        RnsVendorMasterPopupComponent,
        RnsVendorMasterSearchSelectComponent,
        RnsVendorMasterDeleteDialogComponent,
        RnsVendorMasterDeletePopupComponent,
        RnsVendorMasterSearchSelectPopupComponent,
        VendorSearchMasterComponent,
        VendorSearchMasterPopupComponent
    ],
    providers: [RnsVendorMasterService, RnsVendorMasterPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsVendorMasterModule {}
