import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export class RnsEmpMaster implements BaseEntity {
    constructor(
        public id?: number,
        public empCode?: string,
        public empName?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any
    ) {}
}
