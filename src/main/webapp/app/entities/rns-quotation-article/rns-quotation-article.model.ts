import { BaseEntity } from 'app/core';
import { IRnsBuyerMaster } from 'app/entities/rns-buyer-master';

export class RnsQuotationArticle implements BaseEntity {
    constructor(public id?: number, public articleCode?: string, public articleName?: string, public buyerCode?: IRnsBuyerMaster) {}
}
