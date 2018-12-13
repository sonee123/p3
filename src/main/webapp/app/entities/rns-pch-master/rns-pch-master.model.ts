import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export interface IRnsPchMaster {
    id?: number;
    pchCode?: string;
    pchName?: string;
    user?: User;
    createdDate?: any;
    updatedUser?: User;
    lastUpdatedDate?: any;
    catgCode?: BaseEntity;
    users?: BaseEntity[];
}

export class RnsPchMaster implements BaseEntity {
    constructor(
        public id?: number,
        public pchCode?: string,
        public pchName?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public catgCode?: BaseEntity,
        public users?: BaseEntity[]
    ) {}
}
