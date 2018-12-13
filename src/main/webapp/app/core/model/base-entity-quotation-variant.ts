import {AuctionVrnt} from '../../entities/auction-vrnt';
import {RnsQuotation} from '../../entities/rns-quotation';

export interface BaseEntityQuotationVariants {
    // using type any to avoid methods complaining of invalid type
    id?: any;
    title?: any;
    auctionConfig?: any;
    auctionVrnt?: AuctionVrnt;
    VndrPrice?: any;
    startTime?: any;
    endTime?: any;
    timeLeft?: any;
    isAuctionActive?: Boolean;
    lotTime?: number;
    overTime?: number;
    vendorReguarRate?: number;
    lotStartTime?: any;
    lotEndTime?: any;
    lotEndTimeNew?: Date;
    quotation?: RnsQuotation;
    quotationFullDetails?: RnsQuotation;
}
