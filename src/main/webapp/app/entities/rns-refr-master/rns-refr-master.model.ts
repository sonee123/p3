import { IRnsRefrDetails } from 'app/entities/rns-refr-details';
import { BaseEntity } from 'app/core';

export interface IRnsRefrMaster {
    id?: number;
    subcode?: string;
    subCodeDesc?: string;
    status?: string;
    createdBy?: string;
    createdDate?: any;
    lastModifiedBy?: string;
    rnsRefrDetails?: IRnsRefrDetails;
}

export class RnsRefrMaster implements BaseEntity {
    constructor(
        public id?: number,
        public subcode?: string,
        public subCodeDesc?: string,
        public status?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public rnsRefrDetails?: IRnsRefrDetails
    ) {}
}
