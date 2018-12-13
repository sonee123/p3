import { BaseEntity } from 'app/core';

export class RnsQuotationSearch implements BaseEntity {
    public id: string;
    public title: string;
    public projectType: string;
    public catgCode: BaseEntity;
    public dateFrom: any;
    public dateTo: any;
    public sourceTeam: string;
    public eventType: string;
    public pchCode: string;
    public type: string;
    public rfqStatus: string;
    public rfbStatus: string;
    public workflowStatus: string;
    constructor() {}
}
