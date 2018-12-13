import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { P3SharedModule } from '../shared';
import {
    RNSMAIN_ROUTE,
    RnsmainComponent,
    RnsQuotationComponent,
    RnsQuotationNewComponent,
    RnsmainviewComponent,
    RnsmainAuctionListingComponent
} from './';
import { AngularDateTimePickerModule } from 'angular2-datetimepicker';
import { VariantTrimPipe } from './varianttrim.pipe';
import { TrimPipe } from './trim.pipe';
import { ComParentChildService } from './com-parent-child.service';
import { LoginComponent } from './login.component';
import { DashboardComponent } from './dashboard.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MonthDatePickerComponent } from '../month-date-picker/month-date-picker.component';
import { NotifierModule, NotifierOptions } from 'angular-notifier';
import { NgSelectModule } from '@ng-select/ng-select';

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
        BrowserModule,
        ReactiveFormsModule,
        FormsModule,
        NgSelectModule,
        NotifierModule.withConfig(customNotifierOptions)
    ],
    declarations: [
        RnsmainComponent,
        RnsQuotationComponent,
        RnsQuotationNewComponent,
        RnsmainviewComponent,
        RnsmainAuctionListingComponent,
        DashboardComponent,
        VariantTrimPipe,
        TrimPipe,
        LoginComponent,
        MonthDatePickerComponent
    ],
    entryComponents: [RnsQuotationComponent, RnsQuotationNewComponent, LoginComponent, DashboardComponent],
    providers: [ComParentChildService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsMainModule {}
