import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { P3SharedModule } from 'app/shared';
import {
    RnsCrmRequestMasterService,
    RnsCrmRequestMasterPopupService,
    RnsCrmRequestMasterComponent,
    RnsCrmRequestMasterDetailComponent,
    RnsCrmRequestMasterDialogComponent,
    RnsCrmRequestMasterPopupComponent,
    RnsCrmRequestMasterDeletePopupComponent,
    RnsCrmRequestMasterSearchDialogComponent,
    RnsCrmRequestMasterPopupSearchComponent,
    RnsCrmRequestMasterDeleteDialogComponent,
    rnsCrmRequestMasterRoute,
    rnsCrmRequestMasterPopupRoute
} from './';

const ENTITY_STATES = [...rnsCrmRequestMasterRoute, ...rnsCrmRequestMasterPopupRoute];

@NgModule({
    imports: [P3SharedModule, OwlDateTimeModule, OwlNativeDateTimeModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RnsCrmRequestMasterComponent,
        RnsCrmRequestMasterDetailComponent,
        RnsCrmRequestMasterDialogComponent,
        RnsCrmRequestMasterDeleteDialogComponent,
        RnsCrmRequestMasterSearchDialogComponent,
        RnsCrmRequestMasterPopupComponent,
        RnsCrmRequestMasterDeletePopupComponent,
        RnsCrmRequestMasterPopupSearchComponent
    ],
    entryComponents: [
        RnsCrmRequestMasterComponent,
        RnsCrmRequestMasterDialogComponent,
        RnsCrmRequestMasterPopupComponent,
        RnsCrmRequestMasterDeleteDialogComponent,
        RnsCrmRequestMasterSearchDialogComponent,
        RnsCrmRequestMasterDeletePopupComponent,
        RnsCrmRequestMasterPopupSearchComponent
    ],
    providers: [RnsCrmRequestMasterService, RnsCrmRequestMasterPopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsRnsCrmRequestMasterModule {}
