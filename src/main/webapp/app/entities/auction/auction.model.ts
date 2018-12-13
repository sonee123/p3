import {BaseEntity} from 'app/core';

export const enum AuctionEvent {
    'AUCTION',
    'REVERSE_AUCTION'
}

export class Auction implements BaseEntity {
    constructor(
        public id?: number,
        public auctionTitle?: string,
        public auctionDescription?: string,
        public publishTime?: any,
        public biddingStartTime?: any,
        public lotRunningTime?: number,
        public bidRankOverTime?: number,
        public bidTimeForOvertimeStart?: number,
        public overtimePeriod?: number,
        public showLeadBidToAll?: boolean,
        public event?: AuctionEvent,
        public timeBetweenLots?: number,
        public minPriceChanges?: number,
        public currency?: string,
        public showRanks?: boolean,
        public quotationId?: number,
        public quotation?: BaseEntity,
         public allowTieBids?: boolean,
    ) {
        this.showLeadBidToAll = false;
        this.showRanks = false;
    }
}
