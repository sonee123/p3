import { Component, OnInit, OnDestroy, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import 'rxjs/add/operator/debounceTime';
import { RnsCatgMasterPopupService } from './rns-catg-master-popup.service';
import { RnsCatgMasterService } from './rns-catg-master.service';
import { RnsRelation, RnsRelationService } from '../rns-relation';
import { RnsCatgUser } from './rns-catg-user.model';
import { RnsCatgMaster } from './rns-catg-master.model';
import { IItemsMovedEvent } from 'ng2-dual-list-box';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
const intersectionwith = require('lodash.intersectionwith');
const differenceWith = require('lodash.differencewith');
import { NotifierService } from 'angular-notifier';

@Component({
    selector: 'jhi-rns-catg-user-dialog',
    templateUrl: './rns-catg-user-dialog.component.html',
    styleUrls: ['./custom-select.css']
})
export class RnsCatgUserDialogComponent implements OnInit {
    // input to set search term for available list box from the outside
    @Input()
    set availableSearch(searchTerm: string) {
        this.searchTermAvailable = searchTerm;
        this.availableSearchInputControl.setValue(searchTerm);
    }
    // input to set search term for selected list box from the outside
    @Input()
    set selectedSearch(searchTerm: string) {
        this.searchTermSelected = searchTerm;
        this.selectedSearchInputControl.setValue(searchTerm);
    }
    // field to use for value of option
    @Input() valueField = 'id';
    // field to use for displaying option text
    @Input() textField = 'name';
    // text to display as title above component
    @Input() title: string;
    // time to debounce search output in ms
    @Input() debounceTime = 500;
    // show/hide button to move all items between boxes
    @Input() moveAllButton = true;
    // text displayed over the available items list box
    @Input() availableText = 'Available items';
    // text displayed over the selected items list box
    @Input() selectedText = 'Selected items';
    // set placeholder text in available items list box
    @Input() availableFilterPlaceholder = 'Filter...';
    // set placeholder text in selected items list box
    @Input() selectedFilterPlaceholder = 'Filter...';

    // event called when item or items from available items(left box) is selected
    @Output() onAvailableItemSelected: EventEmitter<{} | Array<{}>> = new EventEmitter<{} | Array<{}>>();
    // event called when item or items from selected items(right box) is selected
    @Output() onSelectedItemsSelected: EventEmitter<{} | Array<{}>> = new EventEmitter<{} | Array<{}>>();
    // event called when items are moved between boxes, returns state of both boxes and item moved
    @Output() onItemsMoved: EventEmitter<IItemsMovedEvent> = new EventEmitter<IItemsMovedEvent>();

    rnsCatgUser: RnsCatgUser;
    isSaving: boolean;

    // private variables to manage class
    searchTermAvailable = '';
    searchTermSelected = '';
    availableItems: Array<RnsCatgMaster> = [];
    selectedItems: Array<RnsCatgMaster> = [];
    listBoxForm: FormGroup;
    availableListBoxControl: FormControl = new FormControl();
    selectedListBoxControl: FormControl = new FormControl();
    availableSearchInputControl: FormControl = new FormControl();
    selectedSearchInputControl: FormControl = new FormControl();

    // control value accessors
    _onChange = (_: any) => {};
    _onTouched = () => {};

    constructor(
        public activeModal: NgbActiveModal,
        public jhiAlertService: JhiAlertService,
        public rnsCatgMasterService: RnsCatgMasterService,
        public fb: FormBuilder,
        private notifier: NotifierService
    ) {
        this.listBoxForm = this.fb.group({
            availableListBox: this.availableListBoxControl,
            selectedListBox: this.selectedListBoxControl,
            availableSearchInput: this.availableSearchInputControl,
            selectedSearchInput: this.selectedSearchInputControl
        });
    }

    ngOnInit() {
        this.isSaving = false;
        this.availableItems = [];
        this.availableText = 'Available Categories';
        this.selectedText = 'Selected Categories';
        this.availableItems = this.rnsCatgUser.rnsCatgMasterList;
        this.selectedItems = this.rnsCatgUser.rnsCatgMasterSelectedList;
        this.availableListBoxControl.valueChanges.subscribe((items: Array<{}>) => this.onAvailableItemSelected.emit(items));
        this.selectedListBoxControl.valueChanges.subscribe((items: Array<{}>) => this.onSelectedItemsSelected.emit(items));
        this.availableSearchInputControl.valueChanges
            .debounceTime(this.debounceTime)
            .distinctUntilChanged()
            .subscribe((search: string) => (this.searchTermAvailable = search));
        this.selectedSearchInputControl.valueChanges
            .debounceTime(this.debounceTime)
            .distinctUntilChanged()
            .subscribe((search: string) => (this.searchTermSelected = search));
    }

    /**
     * Move all items from available to selected
     */
    moveAllItemsToSelected(): void {
        if (!this.availableItems.length) {
            return;
        }
        this.selectedItems = [...this.selectedItems, ...this.availableItems];
        this.availableItems = [];
        this.onItemsMoved.emit({
            available: this.availableItems,
            selected: this.selectedItems,
            movedItems: this.availableListBoxControl.value,
            from: 'available',
            to: 'selected'
        });
        this.availableListBoxControl.setValue([]);
        this.writeValue(this.getValues());
    }

    /**
     * Move all items from selected to available
     */
    moveAllItemsToAvailable(): void {
        if (!this.selectedItems.length) {
            return;
        }
        this.availableItems = [...this.availableItems, ...this.selectedItems];
        this.selectedItems = [];
        this.onItemsMoved.emit({
            available: this.availableItems,
            selected: this.selectedItems,
            movedItems: this.selectedListBoxControl.value,
            from: 'selected',
            to: 'available'
        });
        this.selectedListBoxControl.setValue([]);
        this.writeValue([]);
    }

    /**
     * Move marked items from available items to selected items
     */
    moveMarkedAvailableItemsToSelected(): void {
        // first move items to selected
        this.selectedItems = [
            ...this.selectedItems,
            ...intersectionwith(
                this.availableItems,
                this.availableListBoxControl.value,
                (item: RnsCatgMaster, value: number) => item.id === value
            )
        ];
        // now filter available items to not include marked values
        this.availableItems = [
            ...differenceWith(
                this.availableItems,
                this.availableListBoxControl.value,
                (item: RnsCatgMaster, value: number) => item.id === value
            )
        ];
        // clear marked available items and emit event
        this.onItemsMoved.emit({
            available: this.availableItems,
            selected: this.selectedItems,
            movedItems: this.availableListBoxControl.value,
            from: 'available',
            to: 'selected'
        });
        this.availableListBoxControl.setValue([]);
        this.availableSearchInputControl.setValue('');
        this.writeValue(this.getValues());
    }

    /**
     * Move marked items from selected items to available items
     */
    moveMarkedSelectedItemsToAvailable(): void {
        // first move items to available
        this.availableItems = [
            ...this.availableItems,
            ...intersectionwith(
                this.selectedItems,
                this.selectedListBoxControl.value,
                (item: RnsCatgMaster, value: number) => item.id === value
            )
        ];
        // now filter available items to not include marked values
        this.selectedItems = [
            ...differenceWith(
                this.selectedItems,
                this.selectedListBoxControl.value,
                (item: RnsCatgMaster, value: number) => item.id === value
            )
        ];
        // clear marked available items and emit event
        this.onItemsMoved.emit({
            available: this.availableItems,
            selected: this.selectedItems,
            movedItems: this.selectedListBoxControl.value,
            from: 'selected',
            to: 'available'
        });
        this.selectedListBoxControl.setValue([]);
        this.selectedSearchInputControl.setValue('');
        this.writeValue(this.getValues());
    }

    /**
     * Move single item from available to selected
     * @param item
     */
    moveAvailableItemToSelected(item: RnsCatgMaster): void {
        this.availableItems = this.availableItems.filter((listItem: RnsCatgMaster) => listItem.id !== item.id);
        this.selectedItems = [...this.selectedItems, item];
        this.onItemsMoved.emit({
            available: this.availableItems,
            selected: this.selectedItems,
            movedItems: [item.id],
            from: 'available',
            to: 'selected'
        });
        this.availableSearchInputControl.setValue('');
        this.availableListBoxControl.setValue([]);
        this.writeValue(this.getValues());
    }

    /**
     * Move single item from selected to available
     * @param item
     */
    moveSelectedItemToAvailable(item: RnsCatgMaster): void {
        this.selectedItems = this.selectedItems.filter((listItem: RnsCatgMaster) => listItem.id !== item.id);
        this.availableItems = [...this.availableItems, item];
        this.onItemsMoved.emit({
            available: this.availableItems,
            selected: this.selectedItems,
            movedItems: [item.id],
            from: 'selected',
            to: 'available'
        });
        this.selectedSearchInputControl.setValue('');
        this.selectedListBoxControl.setValue([]);
        this.writeValue(this.getValues());
    }

    /**
     * Utility method to get values from
     * selected items
     */
    private getValues(): number[] {
        return (this.selectedItems || []).map((item: RnsCatgMaster) => item.id);
    }

    /**
     * Function to pass to ngFor to improve performance, tracks items
     * by the value field
     * @param index
     * @param item
     */
    trackByValue(index: number, item: {}): string {
        return item[this.valueField];
    }

    /* Methods from ControlValueAccessor interface, required for ngModel and formControlName - begin */
    writeValue(value: any): void {
        if (this.selectedItems && value && value.length > 0) {
            this.selectedItems = [
                ...this.selectedItems,
                ...intersectionwith(this.availableItems, value, (item: RnsCatgMaster, val: number) => item.id === val)
            ];
            this.availableItems = [...differenceWith(this.availableItems, value, (item: RnsCatgMaster, val: number) => item.id === val)];
        }
        this._onChange(value);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (confirm('Do you want to save??')) {
            this.rnsCatgUser.rnsCatgMasterSelectedList = this.selectedItems;
            this.subscribeToSaveResponse(this.rnsCatgMasterService.createUser(this.rnsCatgUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RnsCatgUser>>) {
        result.subscribe((res: HttpResponse<RnsCatgUser>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HttpResponse<RnsCatgUser>) {
        this.isSaving = false;
        this.rnsCatgUser = result.body;
        this.ngOnInit();
        this.notifier.notify('success', 'Record save successfully!!!');
        this.activeModal.dismiss('cancel');
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRnsRelationById(index: number, item: RnsRelation) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-rns-catg-user-popup',
    template: ''
})
export class RnsCatgUserPopupComponent implements OnInit, OnDestroy {
    routeSub: any;

    constructor(private route: ActivatedRoute, private rnsCatgMasterPopupService: RnsCatgMasterPopupService) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['login']) {
                this.rnsCatgMasterPopupService.openpost(RnsCatgUserDialogComponent as Component, params['login']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
