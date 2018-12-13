import { BaseEntity, User } from 'app/core';
import { IRnsCatgMaster } from 'app/entities/rns-catg-master';
import { IRnsTypeMaster } from 'app/entities/rns-type-master';
import { IRnsSourceTeamMaster } from 'app/entities/rns-source-team-master';

export class AuctionTermsMaster implements BaseEntity {
    constructor(
        public id?: number,
        public categoryId?: number,
        public quoteTypeCode?: string,
        public quoteType?: IRnsTypeMaster,
        public sourceTeam?: number,
        public sourceTeamId?: IRnsSourceTeamMaster,
        public termsBody?: string,
        public createdBy?: string,
        public createdDate?: any,
        public updatedBy?: string,
        public lastUpdatedDate?: any,
        public rnsCatgMaster?: IRnsCatgMaster,
        public user?: User,
        public updatedUser?: User
    ) {}
}
