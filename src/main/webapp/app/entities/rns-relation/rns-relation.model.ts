import { BaseEntity, User } from 'app/core';

export class RnsRelation implements BaseEntity {
    constructor(public id?: number, public user?: User) {}
}
