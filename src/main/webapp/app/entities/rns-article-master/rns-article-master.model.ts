import { BaseEntity, User } from 'app/core';
import { IRnsCatgMaster } from 'app/entities/rns-catg-master';

export interface IRnsArticleMaster {
    id?: number;
    articleCode?: string;
    articleDesc?: string;
    catgCode?: IRnsCatgMaster;
    user?: User;
    createdDate?: any;
    updatedUser?: User;
    lastUpdatedDate?: any;
}

export class RnsArticleMaster implements BaseEntity {
    constructor(
        public id?: number,
        public articleCode?: string,
        public articleDesc?: string,
        public catgCode?: IRnsCatgMaster,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
