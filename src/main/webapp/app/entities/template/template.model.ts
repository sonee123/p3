import { BaseEntity } from 'app/core';
import { IUser } from 'app/core';
import { IRnsCatgMaster } from 'app/entities/rns-catg-master';

export const enum ShowAsTemplate {
    'TEXTFIELD',
    'DROPDOWN'
}

export class Template implements BaseEntity {
    constructor(
        public id?: number,
        public templateName?: string,
        public user?: IUser,
        public createdDate?: any,
        public updatedUser?: IUser,
        public lastUpdatedDate?: any,
        public specificationOne?: string,
        public specificationOneShowAs?: ShowAsTemplate,
        public specificationOneValue?: any,
        public specificationOneRequired?: boolean,
        public showInRfqOneRequired?: boolean,
        public showInAuctionOneRequired?: boolean,
        public specificationTwo?: string,
        public specificationTwoShowAs?: ShowAsTemplate,
        public specificationTwoValue?: any,
        public specificationTwoRequired?: boolean,
        public showInRfqTwoRequired?: boolean,
        public showInAuctionTwoRequired?: boolean,
        public specificationThree?: string,
        public specificationThreeShowAs?: ShowAsTemplate,
        public specificationThreeValue?: any,
        public specificationThreeRequired?: boolean,
        public showInRfqThreeRequired?: boolean,
        public showInAuctionThreeRequired?: boolean,
        public specificationFour?: string,
        public specificationFourShowAs?: ShowAsTemplate,
        public specificationFourValue?: any,
        public specificationFourRequired?: boolean,
        public showInRfqFourRequired?: boolean,
        public showInAuctionFourRequired?: boolean,
        public specificationFive?: string,
        public specificationFiveShowAs?: ShowAsTemplate,
        public specificationFiveValue?: any,
        public specificationFiveRequired?: boolean,
        public showInRfqFiveRequired?: boolean,
        public showInAuctionFiveRequired?: boolean,
        public specificationSix?: string,
        public specificationSixShowAs?: ShowAsTemplate,
        public specificationSixValue?: any,
        public specificationSixRequired?: boolean,
        public showInRfqSixRequired?: boolean,
        public showInAuctionSixRequired?: boolean,
        public specificationSeven?: string,
        public specificationSevenShowAs?: ShowAsTemplate,
        public specificationSevenValue?: any,
        public specificationSevenRequired?: boolean,
        public showInRfqSevenRequired?: boolean,
        public showInAuctionSevenRequired?: boolean,
        public specificationEight?: string,
        public specificationEightShowAs?: ShowAsTemplate,
        public specificationEightValue?: any,
        public specificationEightRequired?: boolean,
        public showInRfqEightRequired?: boolean,
        public showInAuctionEightRequired?: boolean,
        public specificationNine?: string,
        public specificationNineShowAs?: ShowAsTemplate,
        public specificationNineValue?: any,
        public specificationNineRequired?: boolean,
        public showInRfqNineRequired?: boolean,
        public showInAuctionNineRequired?: boolean,
        public specificationTen?: string,
        public specificationTenShowAs?: ShowAsTemplate,
        public specificationTenValue?: any,
        public specificationTenRequired?: boolean,
        public showInRfqTenRequired?: boolean,
        public showInAuctionTenRequired?: boolean,
        public rnsCatgCode?: IRnsCatgMaster,
        public flag?: boolean,
        public exist?: boolean
    ) {
        this.specificationOneRequired = false;
        this.showInRfqOneRequired = false;
        this.showInAuctionOneRequired = false;

        this.specificationTwoRequired = false;
        this.showInRfqTwoRequired = false;
        this.showInAuctionTwoRequired = false;

        this.specificationThreeRequired = false;
        this.showInRfqThreeRequired = false;
        this.showInAuctionThreeRequired = false;

        this.specificationFourRequired = false;
        this.showInRfqFourRequired = false;
        this.showInAuctionFourRequired = false;

        this.specificationFiveRequired = false;
        this.showInRfqFiveRequired = false;
        this.showInAuctionFiveRequired = false;

        this.specificationSixRequired = false;
        this.showInRfqSixRequired = false;
        this.showInAuctionSixRequired = false;

        this.specificationSevenRequired = false;
        this.showInRfqSevenRequired = false;
        this.showInAuctionSevenRequired = false;

        this.specificationEightRequired = false;
        this.showInRfqEightRequired = false;
        this.showInAuctionEightRequired = false;

        this.specificationNineRequired = false;
        this.showInRfqNineRequired = false;
        this.showInAuctionNineRequired = false;

        this.specificationTenRequired = false;
        this.showInRfqTenRequired = false;
        this.showInAuctionTenRequired = false;
    }
}
