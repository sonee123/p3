import { BaseEntity } from 'app/core';
import { RnsEmpMaster } from '../rns-emp-master';
import { User } from 'app/core';
import { RnsForwardTypeMaster } from 'app/entities/rns-forward-type-master';

export class RnsEmpLinkMaster implements BaseEntity {
    constructor(
        public id?: number,
        public empCode?: User,
        public forwardEmpCode?: User,
        public forwardEmpType?: string,
        public rnsEmpMaster?: RnsEmpMaster,
        public rnsForwardTypeMaster?: RnsForwardTypeMaster,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public mailApplicable?: boolean
    ) {}
}
