import { User, BaseEntity } from 'app/core';

export interface IRnsCatgMaster {
    id?: number;
    catgCode?: string;
    catgCodeDesc?: string;
    showCrm?: boolean;
    user?: User;
    createdDate?: any;
    updatedUser?: User;
    lastUpdatedDate?: any;
    users?: BaseEntity[];
    name?: string;
    url?: string;
}

export class RnsCatgMaster implements BaseEntity, IRnsCatgMaster {
    constructor(
        public id?: number,
        public catgCode?: string,
        public catgCodeDesc?: string,
        public showCrm?: boolean,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public users?: BaseEntity[],
        public name?: string,
        public url?: string
    ) {
        this.showCrm = false;
    }
}
