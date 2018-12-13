import { BaseEntity } from 'app/core';
import { IRnsRefrMaster } from 'app/entities/rns-refr-master';

export interface IRnsRefrDetails {
    id?: number;
    subCode?: string;
    subCodeDesc?: string;
    status?: string;
    createdBy?: string;
    createdDate?: any;
    lastModifiedBy?: any;
    rnsRefrMasters?: BaseEntity[];
}
export class RnsRefrDetails implements BaseEntity {
    constructor(
        public id?: number,
        public subCode?: string,
        public subCodeDesc?: string,
        public status?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: any,
        public rnsRefrMasters?: IRnsRefrMaster[]
    ) {}
}
