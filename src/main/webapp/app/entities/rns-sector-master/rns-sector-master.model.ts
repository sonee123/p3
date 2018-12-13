import { BaseEntity } from 'app/core';
import { IRnsCatgMaster } from 'app/entities/rns-catg-master';

export interface IRnsSectorMaster {
    id?: number;
    sectorCode?: string;
    sectorCodeDesc?: string;
    rnsCatgCode?: IRnsCatgMaster;
}

export class RnsSectorMaster implements BaseEntity {
    constructor(public id?: number, public sectorCode?: string, public sectorCodeDesc?: string, public rnsCatgCode?: IRnsCatgMaster) {}
}
