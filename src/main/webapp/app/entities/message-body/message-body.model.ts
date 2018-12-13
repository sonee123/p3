import { BaseEntity } from 'app/core';

export class MessageBody implements BaseEntity {
    constructor(
        public id?: number,
        public messageId?: number,
        public messageBody?: string,
        public createdBy?: string,
        public createdDate?: any
    ) {}
}
