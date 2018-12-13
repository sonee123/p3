import { BaseEntity } from 'app/core';

export class RnsUpchargeDtl implements BaseEntity {
    constructor(
        public id?: number,
        public vendorId?: number,
        public upchargeId?: number,
        public remarks?: string,
        public upchargeType?: string,
        public rate?: number,
        public value?: number,
        public createdBy?: string,
        public createdDate?: any,
        public defaultValue?: number
    ) {}
}
