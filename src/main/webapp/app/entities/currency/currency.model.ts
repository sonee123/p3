import { BaseEntity } from 'app/core';

export interface ICurrency {
    id?: number;
    currency?: string;
    symbol?: string;
    exchange_rate?: number;
}

export class Currency implements BaseEntity {
    constructor(public id?: number, public currency?: string, public symbol?: string, public exchange_rate?: number) {}
}
