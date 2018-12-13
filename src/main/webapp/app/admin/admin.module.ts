import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { P3SharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
    adminState,
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    VendorMgmtComponent,
    VendorMgmtDetailComponent,
    VendorMgmtUpdateComponent,
    VendorMgmtDeleteDialogComponent,
    VendorPasswordStrengthBarComponent,
    VendorPasswordComponent,
    VendorPasswordDialogComponent,
    VendorModalService,
    LogsComponent,
    JhiMetricsMonitoringModalComponent,
    JhiMetricsMonitoringComponent,
    JhiHealthModalComponent,
    JhiHealthCheckComponent,
    JhiConfigurationComponent,
    JhiDocsComponent,
    JhiTrackerComponent
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

@NgModule({
    imports: [
        P3SharedModule,
        RouterModule.forChild(adminState),
        NotifierModule.withConfig(customNotifierOptions)
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserMgmtDetailComponent,
        UserMgmtUpdateComponent,
        UserMgmtDeleteDialogComponent,
        VendorMgmtComponent,
        VendorMgmtDetailComponent,
        VendorMgmtUpdateComponent,
        VendorMgmtDeleteDialogComponent,
        VendorPasswordStrengthBarComponent,
        VendorPasswordComponent,
        VendorPasswordDialogComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiTrackerComponent,
        JhiMetricsMonitoringComponent,
        JhiMetricsMonitoringModalComponent
    ],
    entryComponents: [
        UserMgmtDeleteDialogComponent,
        VendorMgmtDeleteDialogComponent,
        VendorPasswordComponent,
        VendorPasswordDialogComponent,
        JhiHealthModalComponent,
        JhiMetricsMonitoringModalComponent
    ],
    providers: [VendorModalService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class P3AdminModule {}
