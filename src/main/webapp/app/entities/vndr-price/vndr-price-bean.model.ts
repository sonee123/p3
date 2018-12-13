import { BaseEntity } from 'app/core';

export class VndrPriceBean implements BaseEntity {
    constructor(public id?: number, public variantId?: number, public vrntcode?: string) {}
}
