import { BaseEntity } from 'app/core';

export class RnsRfqPrice implements BaseEntity {
    constructor(
        public id?: number,
        public priceOne?: number,
        public priceTwo?: number,
        public priceThree?: number,
        public priceFour?: number,
        public priceFive?: number,
        public priceSix?: number,
        public priceSeven?: number,
        public priceEight?: number,
        public priceNine?: number,
        public priceTen?: number,
        public vendorId?: number,
        public createdBy?: string,
        public createdDate?: any,
        public paymentTerms?: string,
        public deliveryTerms?: string,
        public confDelDate?: any,
        public currency?: string,
        public regularRate?: number,
        public rfqUserType?: string
    ) {}
}
