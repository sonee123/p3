import { BaseEntity } from 'app/core';

export class RnsQuotationDealTerms implements BaseEntity {
    constructor(
        public id?: number,
        public completionBy?: any,
        public validUntil?: number,
        public deliverAt?: string,
        public text2?: string,
        public quotationDealTerms?: BaseEntity,
        public paymentTerms?: BaseEntity,
        public deliveryTerms?: BaseEntity
    ) {}
}
