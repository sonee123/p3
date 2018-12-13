import { BaseEntity } from 'app/core';

export class BiddingCompareVendorModel implements BaseEntity {
    constructor(
        public id?: any,
        public vendorCode?: string,
        public vendorName?: string,
        public firstName?: string,
        public lastName?: string,
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
        public amountOne?: number,
        public amountTwo?: number,
        public amountThree?: number,
        public amountFour?: number,
        public amountFive?: number,
        public amountSix?: number,
        public amountSeven?: number,
        public amountEight?: number,
        public amountNine?: number,
        public amountTen?: number,
        public priceTotal?: number,
        public amountTotal?: number
    ) {}
}
