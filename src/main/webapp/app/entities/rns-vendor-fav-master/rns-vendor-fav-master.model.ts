import { BaseEntity } from 'app/core';

export class RnsVendorFavMaster implements BaseEntity {
    constructor(public id?: number, public vendorCode?: string, public createdBy?: string, public createdDate?: any) {}
}
