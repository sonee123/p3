import { BaseEntity } from 'app/core';

export class AuctionVarDetails implements BaseEntity {
    constructor(
        public id?: number,
        public lotStartTime?: any,
        public lotEndTime?: any,
        public overtimeMinutes?: number,
        public variantId?: number,
        public quotationId?: number,
        public createdBy?: string,
        public createdDate?: any,
        public updatedBy?: string,
        public updatedDate?: any,
        public labelName?: string,
        public lotMinutes?: string,
        public type?: string
    ) {}
}
