import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export class RnsDelTermsMaster implements BaseEntity {
    constructor(
        public id?: number,
        public delTermsCode?: string,
        public delTermsCodeDesc?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public rnsCatgCode?: BaseEntity
    ) {}
}
