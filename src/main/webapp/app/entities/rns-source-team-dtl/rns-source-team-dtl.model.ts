import { BaseEntity, IUser } from 'app/core';
import { RnsSourceTeamMaster } from '../rns-source-team-master';
import { User } from 'app/core';

export class RnsSourceTeamDtl implements BaseEntity {
    constructor(
        public id?: number,
        public teamUser?: IUser,
        public masterId?: number,
        public user?: User,
        public createdDate?: any,
        public updatedUser?: User,
        public lastUpdatedDate?: any,
        public master?: RnsSourceTeamMaster
    ) {}
}
