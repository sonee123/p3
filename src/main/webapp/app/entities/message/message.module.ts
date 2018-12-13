import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
    MessageService,
    MessagePopupService,
    MessageComponent,
    MessageDetailComponent,
    MessageDialogComponent,
    MessagePopupComponent,
    MessageDeletePopupComponent,
    MessageDeleteDialogComponent,
    messageRoute,
    messagePopupRoute,
    MessageQuotationDialogComponent,
    MessageQuotationPopupComponent
} from './';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { P3SharedModule } from 'app/shared';
import { MonthDateMessagePickerComponent } from './month-date-picker.component';

const ENTITY_STATES = [...messageRoute, ...messagePopupRoute];

@NgModule({
    imports: [P3SharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule, ReactiveFormsModule, FormsModule],
    declarations: [
        MessageComponent,
        MessageDetailComponent,
        MessageDialogComponent,
        MessageQuotationDialogComponent,
        MessageDeleteDialogComponent,
        MessagePopupComponent,
        MessageQuotationPopupComponent,
        MessageDeletePopupComponent,
        MonthDateMessagePickerComponent
    ],
    entryComponents: [
        MessageComponent,
        MessageDialogComponent,
        MessagePopupComponent,
        MessageDeleteDialogComponent,
        MessageDeletePopupComponent,
        MessageQuotationDialogComponent,
        MessageQuotationPopupComponent
    ],
    providers: [MessageService, MessagePopupService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RnsMessageModule {}
