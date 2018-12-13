import { BaseEntity, User } from 'app/core';

export class RnsVendorMaster implements BaseEntity {
    constructor(
        public id?: number,
        public vendorCode?: string,
        public vendorMasterCode?: string,
        public vendorName?: string,
        public vendorUserId?: number,
        public userName?: string,
        public email?: string,
        public mobileNumber?: string,
        public vendors?: BaseEntity[],
        public user?: User,
        public favourite?: boolean
    ) {}
}
