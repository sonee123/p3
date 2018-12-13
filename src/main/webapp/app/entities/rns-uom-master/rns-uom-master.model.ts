import { BaseEntity, User } from 'app/core';

export class RnsUomMaster implements BaseEntity {
    constructor(
        public id?: number,
        public uomCode?: string,
        public uomName?: string,
        public user?: User,
        public createdDate?: any,
        public updateUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
