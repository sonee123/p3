import { BaseEntity, BaseEntityVendors, BaseEntityVndrPrice } from 'app/core';
import { AuctionVrnt } from 'app/entities/auction-vrnt';

export interface IRnsQuotationVariant {
    id?: number;
    title?: string;
    varDescSpec1?: string;
    varDescSpec1Value?: string;
    varDescSpec2?: string;
    varDescSpec2Value?: string;
    varDescSpec3?: string;
    varDescSpec3Value?: string;
    varDescSpec4?: string;
    varDescSpec4Value?: string;
    varDescSpec5?: string;
    varDescSpec5Value?: string;
    varDescSpec6?: string;
    varDescSpec6Value?: string;
    varDescSpec7?: string;
    varDescSpec7Value?: string;
    varDescSpec8?: string;
    varDescSpec8Value?: string;
    varDescSpec9?: string;
    varDescSpec9Value?: string;
    varDescSpec10?: string;
    varDescSpec10Value?: string;
    eventDefType?: string;
    eventDefCategory?: string;
    eventDefTechnology?: string;
    eventDefDefectCode?: string;
    eventDefText1?: string;
    dealtermCompletionBy?: any;
    dealtermValidUntil?: any;
    dealtermPaymentTerms?: string;
    dealtermDeliveryTerms?: string;
    taxTerms?: string;
    dealtermPaymentTermsDesc?: string;
    dealtermDeliveryTermsDesc?: string;
    dealtermsTaxTermsDesc?: string;
    dealtermsDelPlaceDesc?: string;
    dealtermDeliverAt?: string;
    dealtermText2?: string;
    orderQuantity?: number;
    orderUom?: string;
    remarks?: string;
    vendors?: BaseEntityVendors[];
    quotation?: BaseEntity;
    auctionConfig?: AuctionVrnt;
    vndrPrice?: BaseEntityVndrPrice;
    startTime?: any;
    endTime?: any;
    timeLeft?: any;
    msg?: any;
    timeMsg?: any;
    isAuctionActive?: Boolean;
    isAuctionPending?: Boolean;
    isAuctionClosed?: Boolean;
    lotTime?: number;
    overTime?: number;
    paused?: Boolean;
    totalPrice?: number;
    vendorReguarRate?: number;
    historicalPrice?: number;
    currency?: string;
    natureOfPrice?: string;
    lotStartTime?: any;
    lotEndTime?: any;
    lotEndTimeNew?: Date;
    vendorActive?: boolean;
    auctionVrnt?: AuctionVrnt;
    openCosting?: string;
    showInRfqOneRequired?: boolean;
    showInRfqTwoRequired?: boolean;
    showInRfqThreeRequired?: boolean;
    showInRfqFourRequired?: boolean;
    showInRfqFiveRequired?: boolean;
    showInRfqSixRequired?: boolean;
    showInRfqSevenRequired?: boolean;
    showInRfqEightRequired?: boolean;
    showInRfqNineRequired?: boolean;
    showInRfqTenRequired?: boolean;
    showInAuctionOneRequired?: boolean;
    showInAuctionTwoRequired?: boolean;
    showInAuctionThreeRequired?: boolean;
    showInAuctionFourRequired?: boolean;
    showInAuctionFiveRequired?: boolean;
    showInAuctionSixRequired?: boolean;
    showInAuctionSevenRequired?: boolean;
    showInAuctionEightRequired?: boolean;
    showInAuctionNineRequired?: boolean;
    showInAuctionTenRequired?: boolean;
    uploadFlag?: string;
    bidStartPrice?: number;
    isCollapse?: boolean;
}

export class RnsQuotationVariant implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public varDescSpec1?: string,
        public varDescSpec1Value?: string,
        public varDescSpec2?: string,
        public varDescSpec2Value?: string,
        public varDescSpec3?: string,
        public varDescSpec3Value?: string,
        public varDescSpec4?: string,
        public varDescSpec4Value?: string,
        public varDescSpec5?: string,
        public varDescSpec5Value?: string,
        public varDescSpec6?: string,
        public varDescSpec6Value?: string,
        public varDescSpec7?: string,
        public varDescSpec7Value?: string,
        public varDescSpec8?: string,
        public varDescSpec8Value?: string,
        public varDescSpec9?: string,
        public varDescSpec9Value?: string,
        public varDescSpec10?: string,
        public varDescSpec10Value?: string,
        public eventDefType?: string,
        public eventDefCategory?: string,
        public eventDefTechnology?: string,
        public eventDefDefectCode?: string,
        public eventDefText1?: string,
        public dealtermCompletionBy?: any,
        public dealtermValidUntil?: any,
        public dealtermPaymentTerms?: string,
        public dealtermDeliveryTerms?: string,
        public taxTerms?: string,
        public dealtermPaymentTermsDesc?: string,
        public dealtermDeliveryTermsDesc?: string,
        public dealtermsTaxTermsDesc?: string,
        public dealtermsDelPlaceDesc?: string,
        public dealtermDeliverAt?: string,
        public dealtermText2?: string,
        public orderQuantity?: number,
        public orderUom?: string,
        public remarks?: string,
        public vendors?: BaseEntityVendors[],
        public quotation?: BaseEntity,
        public auctionConfig?: AuctionVrnt,
        public vndrPrice?: BaseEntityVndrPrice,
        public startTime?: any,
        public endTime?: any,
        public timeLeft?: any,
        public msg?: any,
        public timeMsg?: any,
        public isAuctionActive?: Boolean,
        public isAuctionPending?: Boolean,
        public isAuctionClosed?: Boolean,
        public lotTime?: number,
        public overTime?: number,
        public paused?: Boolean,
        public totalPrice?: number,
        public vendorReguarRate?: number,
        public historicalPrice?: number,
        public currency?: string,
        public natureOfPrice?: string,
        public lotStartTime?: any,
        public lotEndTime?: any,
        public lotEndTimeNew?: Date,
        public vendorActive?: boolean,
        public auctionVrnt?: AuctionVrnt,
        public openCosting?: string,
        public showInRfqOneRequired?: boolean,
        public showInRfqTwoRequired?: boolean,
        public showInRfqThreeRequired?: boolean,
        public showInRfqFourRequired?: boolean,
        public showInRfqFiveRequired?: boolean,
        public showInRfqSixRequired?: boolean,
        public showInRfqSevenRequired?: boolean,
        public showInRfqEightRequired?: boolean,
        public showInRfqNineRequired?: boolean,
        public showInRfqTenRequired?: boolean,
        public showInAuctionOneRequired?: boolean,
        public showInAuctionTwoRequired?: boolean,
        public showInAuctionThreeRequired?: boolean,
        public showInAuctionFourRequired?: boolean,
        public showInAuctionFiveRequired?: boolean,
        public showInAuctionSixRequired?: boolean,
        public showInAuctionSevenRequired?: boolean,
        public showInAuctionEightRequired?: boolean,
        public showInAuctionNineRequired?: boolean,
        public showInAuctionTenRequired?: boolean,
        public uploadFlag?: string,
        public bidStartPrice?: number,
        public isCollapse?: boolean
    ) {}
}
