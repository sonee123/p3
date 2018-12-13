import { BaseEntity } from 'app/core';

export class RnsVendorRemark implements BaseEntity {
    constructor(
        public id?: number,
        public remarkText?: string,
        public vendorEmail?: string,
        public staffEmail?: string,
        public fromEmail?: string,
        public toEmail?: string,
        public read?: boolean,
        public quotation?: BaseEntity
    ) {
        this.read = false;
    }
}
