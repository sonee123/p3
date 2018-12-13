import { BaseEntity, User } from 'app/core';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public fromMail?: string,
        public toMail?: string,
        public subject?: string,
        public messageBody?: string,
        public quotationId?: number,
        public createdBy?: string,
        public createdDate?: any,
        public fromUser?: User,
        public toUser?: User,
        public company?: string,
        public readflag?: string
    ) {}
}
