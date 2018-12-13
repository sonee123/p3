import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export class RnsPayTermsMaster implements BaseEntity {
    constructor(
        public id?: number,
        public payTermsCode?: string,
        public payTermsCodeDesc?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public catgCode?: BaseEntity
    ) {}
}
