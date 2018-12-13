import { BaseEntity } from 'app/core';

export class EmailTemplate implements BaseEntity {
    constructor(
        public id?: number,
        public templateCode?: string,
        public mailSubject?: string,
        public mailBody?: string,
        public notification?: boolean,
        public email?: boolean,
        public createdBy?: string,
        public createdDate?: any,
        public lastUpdatedBy?: string,
        public lastUpdatedDate?: any
    ) {
        this.notification = false;
        this.email = false;
    }
}
