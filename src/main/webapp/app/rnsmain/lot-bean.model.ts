import {BaseEntity, BaseEntityVndrPrice} from '../core';
import {RnsQuotationVariant} from '../entities/rns-quotation-variant';

export class LotBean implements BaseEntity {
    constructor(
        public id?: number,
        public variantName?: string) {
    }
}
