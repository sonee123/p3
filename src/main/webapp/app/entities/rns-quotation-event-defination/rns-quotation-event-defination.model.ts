import { BaseEntity } from 'app/core';

export class RnsQuotationEventDefination implements BaseEntity {
    constructor(
        public id?: number,
        public technology?: string,
        public defectCode?: string,
        public text1?: string,
        public rnsType?: BaseEntity
    ) {}
}
