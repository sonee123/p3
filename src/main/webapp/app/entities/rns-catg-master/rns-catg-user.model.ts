import { User, BaseEntity } from 'app/core';
import { RnsCatgMaster } from './rns-catg-master.model';

export interface IRnsCatgUser {
    login?: string;
    userId?: number;
    rnsCatgMasterList?: RnsCatgMaster[];
    rnsCatgMasterSelectedList?: RnsCatgMaster[];
}

export class RnsCatgUser implements IRnsCatgUser {
    constructor(
        public login?: string,
        public userId?: number,
        public rnsCatgMasterList?: RnsCatgMaster[],
        public rnsCatgMasterSelectedList?: RnsCatgMaster[]
    ) {}
}
