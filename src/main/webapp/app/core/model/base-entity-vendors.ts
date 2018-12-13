export interface BaseEntityVendors {
    // using type any to avoid methods complaining of invalid type
    id?: any;
    email?: any;
    user?: any;
    vendorCode?: any;
    regularRate?: any;
    login?: any;
    confDelDatedate?: any;
    confDelDate?: any;
    rfqPrice?: any;
    allEditable?: Boolean;
    quoteQty?: string;
    expDelDate?: string;
    minExpDelDate?: any;
    expDelDatedate?: any;
    minConfDelDate?: any;
    auctionApplicable?: boolean;
}
