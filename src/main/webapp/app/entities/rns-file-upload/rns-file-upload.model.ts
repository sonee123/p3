import { Moment } from 'moment';

export interface IRnsFileUpload {
    id?: number;
    variantId?: number;
    fileName?: string;
    displayName?: string;
    createdDate?: Moment;
    createdBy?: string;
    uploadType?: string;
}
export class RnsFileUpload implements IRnsFileUpload {
    constructor(
        public id?: number,
        public variantId?: number,
        public fileName?: string,
        public displayName?: string,
        public uploadType?: string,
        public createdBy?: string,
        public createdDate?: any
    ) {}
}
