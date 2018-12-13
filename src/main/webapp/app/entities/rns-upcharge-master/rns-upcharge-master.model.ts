import { BaseEntity, User } from 'app/core';

export class RnsUpchargeMaster implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public flag?: string,
        public user?: User,
        public createdDate?: any,
        public updateUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
