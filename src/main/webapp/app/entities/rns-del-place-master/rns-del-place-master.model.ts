import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export class RnsDelPlaceMaster implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public codeDesc?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
