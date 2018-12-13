import { BaseEntity } from 'app/core';

export class AuctionTermsBodyMaster implements BaseEntity {
    constructor(
        public id?: number,
        public termId?: number,
        public termsBody?: string,
        public createdBy?: string,
        public createdDate?: any
    ) {}
}
