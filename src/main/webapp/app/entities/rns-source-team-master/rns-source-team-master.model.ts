import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export interface IRnsSourceTeamMaster {
    id?: number;
    description?: string;
    flag?: string;
    user?: User;
    createdDate?: any;
    updatedUser?: User;
    lastUpdatedDate?: any;
}

export class RnsSourceTeamMaster implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public flag?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
