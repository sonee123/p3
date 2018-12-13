import { BaseEntity, User } from 'app/core';
import { RnsEmpMaster } from '../rns-emp-master';
import { RnsForwardTypeMaster } from '../rns-forward-type-master';

export class RnsQuotationRemarkDetails implements BaseEntity {
    constructor(
        public id?: number,
        public quoteId?: number,
        public empCode?: User,
        public forwardCode?: User,
        public remarks?: string,
        public authType?: string,
        public authDate?: any,
        public allowEntry?: boolean,
        public allowWorkFlow?: string,
        public flowType?: string,
        public errorMessage?: string,
        public rnsForwardTypeMaster?: RnsForwardTypeMaster,
        public approvalType?: string,
        public rnsQuotationRemarkDetailsList?: RnsQuotationRemarkDetails[]
    ) {}
}
