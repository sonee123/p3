import { BaseEntity } from 'app/core';
import { User } from 'app/core';

export class RnsTaxTermsMaster implements BaseEntity {
    constructor(
        public id?: number,
        public taxTermsCode?: string,
        public taxTermsDesc?: string,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public rnsCatgCode?: BaseEntity
    ) {}
}
