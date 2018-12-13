import {BaseEntity} from '../index';

export class BaseEntityVndrPrice {
    // using type any to avoid methods complaining of invalid type
    id?: any;
    title?: any;
    vendorCode?: any;
    date?: any;
    value?: number;
    priceOne?: number;
    priceTwo?: number;
    priceThree?: number;
    priceFour?: number;
    priceFive?: number;
    priceSix?: number;
    priceSeven?: number;
    priceEight?: number;
    priceNine?: number;
    priceTen?: number;
    vendorName?: string;
    dateformated?: any;
    surrogate?: boolean;
}
