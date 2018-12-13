import { BaseEntity, User } from 'app/core';
import { IRnsPchMaster } from 'app/entities/rns-pch-master';
import { IRnsBuyerMaster } from 'app/entities/rns-buyer-master';
import { IRnsArticleMaster } from 'app/entities/rns-article-master';

export class RnsCrmRequestMaster implements BaseEntity {
    constructor(
        public id?: number,
        public crmCode?: string,
        public requestedBy?: string,
        public targetPcd?: any,
        public merchantRemarks?: string,
        public date?: any,
        public rnsPchMaster?: IRnsPchMaster,
        public buyerCode?: IRnsBuyerMaster,
        public rnsArticleMaster?: IRnsArticleMaster,
        public user?: User,
        public createdDate?: any,
        public updateUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
