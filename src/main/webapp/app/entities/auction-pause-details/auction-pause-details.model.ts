import { BaseEntity } from 'app/core';

export class AuctionPauseDetails implements BaseEntity {
    constructor(
        public id?: number,
        public pauseStartDate?: any,
        public pauseEndDate?: any,
        public variantId?: number,
        public quotationId?: number,
        public createdDate?: any,
        public createdBy?: string,
        public updatedDate?: any,
        public updatedBy?: string,
        public currentDate?: any,
        public timeAllow?: boolean
    ) {}
}
