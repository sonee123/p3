import { BaseEntity } from 'app/core';

export class RnsVendorRemarkComment implements BaseEntity {
    constructor(
        public id?: number,
        public remarkText?: string,
        public vendorId?: number,
        public vendorRemarkId?: number,
        public read?: boolean,
        public rnsVendorRemark?: BaseEntity
    ) {
        this.read = false;
    }
}
