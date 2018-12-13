import { BaseEntity } from 'app/core';

export class Feedback implements BaseEntity {
    constructor(
        public id?: number,
        public yourEmailId?: string,
        public remarks?: string,
        public attachFile?: string,
        public displayAttachFile?: string,
        public createdBy?: string,
        public createdDate?: any,
        public ccdResponseDate?: any,
        public ccdResponse?: string
    ) {}
}
