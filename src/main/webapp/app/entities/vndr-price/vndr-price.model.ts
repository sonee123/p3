import { BaseEntity } from 'app/core';
import { ICurrency } from 'app/entities/currency';
import { IRnsQuotationVariant } from 'app/entities/rns-quotation-variant';

export class VndrPrice implements BaseEntity {
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
        public vendorCode?: string,
        public variant?: IRnsQuotationVariant,
        public vndrQuotation?: BaseEntity,
        public currency?: ICurrency,
        public createdOn?: any,
        public surrogate?: boolean
    ) {}
}
