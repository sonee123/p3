import { BaseEntity } from 'app/core';

export class RnsForwardTypeMaster implements BaseEntity {
    constructor(public id?: number, public code?: string, public description?: string) {}
}
