import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { P3SharedModule } from 'app/shared';
import { P3CoreModule } from 'app/core';
import { P3AppRoutingModule } from './app-routing.module';
import { P3AccountModule } from './account/account.module';
import * as moment from 'moment';
import { NgSelectModule } from '@ng-select/ng-select';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    JhiMainComponent,
    NavbarComponent,
    SidebarComponent,
    FooterComponent,
    PageRibbonComponent,
    ErrorComponent,
    UserDetailComponent
} from './layouts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { P3HomeModule } from 'app/home';
import { P3EntityModule } from 'app/entities/entity.module';
import { RnsMainModule } from 'app/rnsmain';
import { VendorMainModule } from 'app/vendor/vendormain.module';
import { NgbDateMomentParserFormatter } from 'app/ngb-date-moment-parser-formatter.pipe';
import { CKEditorModule } from 'ng2-ckeditor';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { P3ReportModule } from 'app/report';
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
        BrowserModule,
        BrowserAnimationsModule,
        P3AppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        P3SharedModule,
        P3CoreModule,
        P3HomeModule,
        P3AccountModule,
        P3EntityModule,
        RnsMainModule,
        VendorMainModule,
        P3ReportModule,
        CKEditorModule,
        OwlDateTimeModule,
        OwlNativeDateTimeModule,
        NgSelectModule,
        NotifierModule.withConfig(customNotifierOptions)
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        SidebarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        UserDetailComponent
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: NgbDateParserFormatter,
            useClass: NgbDateMomentParserFormatter
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class P3AppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
