import { BaseEntity } from 'app/core';
import { BiddingCompareVendorModel } from './bidding-compare-vendor.model';

export class BiddingCompareModel implements BaseEntity {
    constructor(
        public id?: any,
        public textOne?: string,
        public textTwo?: string,
        public textThree?: string,
        public textFour?: string,
        public textFive?: string,
        public textSix?: string,
        public textSeven?: string,
        public textEight?: string,
        public textNine?: string,
        public textTen?: string,
        public convFactOne?: number,
        public convFactTwo?: number,
        public convFactThree?: number,
        public convFactFour?: number,
        public convFactFive?: number,
        public convFactSix?: number,
        public convFactSeven?: number,
        public convFactEight?: number,
        public convFactNine?: number,
        public convFactTen?: number,
        public uomOne?: string,
        public uomTwo?: string,
        public uomThree?: string,
        public uomFour?: string,
        public uomFive?: string,
        public uomSix?: string,
        public uomSeven?: string,
        public uomEight?: string,
        public uomNine?: string,
        public uomTen?: string,
        public vendorBeans?: BiddingCompareVendorModel[]
    ) {}
}
