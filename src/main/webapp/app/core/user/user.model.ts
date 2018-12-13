export interface IUser {
    id?: any;
    login?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    activated?: boolean;
    langKey?: string;
    authorities?: any[];
    createdBy?: string;
    createdDate?: Date;
    lastModifiedBy?: string;
    lastModifiedDate?: Date;
    password?: string;
    favourite?: boolean;
    vendorCode?: string;
    vendorName?: string;
    registeredUser?: boolean;
    secondaryEmail?: string;
    contactNumber1?: string;
    contactNumber2?: string;
    type?: string;
    currency?: string;
}

export class User implements IUser {
    constructor(
        public id?: any,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public activated?: boolean,
        public langKey?: string,
        public authorities?: any[],
        public createdBy?: string,
        public createdDate?: Date,
        public lastModifiedBy?: string,
        public lastModifiedDate?: Date,
        public password?: string,
        public favourite?: boolean,
        public vendorCode?: string,
        public vendorName?: string,
        public registeredUser?: boolean,
        public secondaryEmail?: string,
        public contactNumber1?: string,
        public contactNumber2?: string,
        public type?: string,
        public added?: boolean,
        public currency?: string
    ) {
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.activated = activated ? activated : false;
        this.langKey = langKey ? langKey : null;
        this.authorities = authorities ? authorities : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.password = password ? password : null;
        this.favourite = favourite ? favourite : false;
        this.vendorCode = vendorCode ? vendorCode : null;
        this.vendorName = vendorName ? vendorName : null;
        this.registeredUser = registeredUser ? registeredUser : false;
        this.secondaryEmail = secondaryEmail ? secondaryEmail : null;
        this.contactNumber1 = contactNumber1 ? contactNumber1 : null;
        this.contactNumber2 = contactNumber2 ? contactNumber2 : null;
        this.type = type ? type : null;
        this.added = added ? added : null;
        this.currency = currency ? currency : null;
    }
}
