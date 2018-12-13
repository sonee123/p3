import { BaseEntity } from 'app/core';

export class EmailTemplateBody implements BaseEntity {
    constructor(
        public id?: number,
        public templateCode?: string,
        public mailBody?: string,
        public createdBy?: string,
        public createdDate?: any
    ) {}
}
