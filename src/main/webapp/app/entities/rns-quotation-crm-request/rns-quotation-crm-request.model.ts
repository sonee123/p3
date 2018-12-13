import { BaseEntity } from 'app/core';
import { IRnsBuyerMaster } from 'app/entities/rns-buyer-master';
import { IRnsPchMaster } from 'app/entities/rns-pch-master';

export class RnsQuotationCrmRequest implements BaseEntity {
    constructor(
        public id?: number,
        public crmRequestNumber?: string,
        public requestedBy?: string,
        public targetPcd?: any,
        public merchantRemarks?: string,
        public date?: any,
        public buyerCode?: IRnsBuyerMaster,
        public rnsPchMaster?: IRnsPchMaster
    ) {}
}
