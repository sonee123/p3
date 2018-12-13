import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export interface IRnsBuyerMaster {
    id?: number;
    buyerCode?: string;
    buyerName?: string;
    user?: User;
    createdDate?: any;
    updatedUser?: User;
    lastUpdatedDate?: any;
}

export class RnsBuyerMaster implements BaseEntity {
    constructor(
        public id?: number,
        public buyerCode?: string,
        public buyerName?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
