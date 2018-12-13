import { BaseEntity } from 'app/core';
import { User } from 'app/core';
import { IRnsCatgMaster } from 'app/entities/rns-catg-master';
export interface IRnsTypeMaster {
    id?: number;
    typeCode?: string;
    typeCodeDesc?: string;
    user?: User;
    createdDate?: any;
    updatedUser?: User;
    lastUpdatedDate?: any;
    rnsCatgCode?: IRnsCatgMaster;
}
export class RnsTypeMaster implements BaseEntity {
    constructor(
        public id?: number,
        public typeCode?: string,
        public typeCodeDesc?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public rnsCatgCode?: IRnsCatgMaster
    ) {}
}
