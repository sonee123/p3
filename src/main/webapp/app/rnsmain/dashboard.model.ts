import {BaseEntity, BaseEntityVndrPrice} from '../core';
import {RnsQuotationVariant} from '../entities/rns-quotation-variant';
import {LotBean} from './lot-bean.model';
import {Message} from '../entities/message';

export class Dashboard implements BaseEntity {
    constructor(
        public id?: number,
        public monthYear?: string,
        public catgCode?: number,
        public totalProject?: number,
        public totalRfq?: number,
        public totalRfb?: number,
        public openRfq?: number,
        public closedRfq?: number,
        public openRfb?: number,
        public closedRfb?: number,
        public selectedBid?: number,
        public selectedLot?: number,
        public biddingList?: number[],
        public lotList?: LotBean[],
        public rnsQuotationVariant?: RnsQuotationVariant,
        public pricesList?: BaseEntityVndrPrice[],
        public messageBeans?: Message[],
        public messageBeansTo?: Message[]
    ) {
    }
}
