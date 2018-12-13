import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { RnsQuotationVendors } from './rns-quotation-vendors.model';
import { JhiDateUtils } from 'ng-jhipster';
import { BaseEntityVndrPrice } from 'app/core';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { RnsRfqPrice } from 'app/entities/rns-rfq-price';

type EntityResponseType = HttpResponse<RnsQuotationVendors>;

@Injectable()
export class RnsQuotationVendorsService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-vendors';
    private resourceUrlRFQ = SERVER_API_URL + 'api/rns-rfq-prices';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsQuotationVendors: RnsQuotationVendors): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationVendors);
        return this.http
            .post<RnsQuotationVendors>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationVendors: RnsQuotationVendors): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationVendors);
        return this.http
            .put<RnsQuotationVendors>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationVendors>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationVendors[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationVendors[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVendors[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getByQuotationId(id: number): Observable<HttpResponse<RnsQuotationVendors[]>> {
        return this.http
            .get<RnsQuotationVendors[]>(`${this.resourceUrl}/getByQuotationId/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVendors[]>) => this.convertArrayResponse(res));
    }

    getByQuotationpriceId(id: number): Observable<HttpResponse<RnsQuotationVendors[]>> {
        return this.http
            .get<RnsQuotationVendors[]>(`${this.resourceUrl}/getPriceByQuotationId/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVendors[]>) => this.convertArrayResponse(res));
    }

    getRevisionByQuotationId(id: number): Observable<HttpResponse<BaseEntityVndrPrice[]>> {
        return this.http
            .get<RnsQuotationVendors[]>(`${this.resourceUrl}/getRevisionByQuotationId/${id}`, { observe: 'response' })
            .map((res: HttpResponse<BaseEntityVndrPrice[]>) => this.convertArrayResponseVndrPrice(res));
    }

    getRevisionByVendorId(id: number): Observable<HttpResponse<BaseEntityVndrPrice[]>> {
        return this.http
            .get<BaseEntityVndrPrice[]>(`${this.resourceUrl}/getRevisionByVendorId/${id}`, { observe: 'response' })
            .map((res: HttpResponse<BaseEntityVndrPrice[]>) => this.convertArrayResponseVndrPrice(res));
    }

    getLoginVendorByQuotationId(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationVendors>(`${this.resourceUrl}/getLoginVendorByQuotationId/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    surrogate(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationVendors>(`${this.resourceUrl}-surrogate/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    updaterfq(rnsQuotationVendors: RnsQuotationVendors): Observable<HttpResponse<RnsRfqPrice>> {
        const rnsRfqPrice: RnsRfqPrice = rnsQuotationVendors.rfqPrice;
        rnsRfqPrice.vendorId = rnsQuotationVendors.id;
        rnsRfqPrice.paymentTerms = rnsQuotationVendors.paymentTerms;
        rnsRfqPrice.deliveryTerms = rnsQuotationVendors.deliveryTerms;
        rnsRfqPrice.confDelDate = rnsQuotationVendors.confDelDate;
        rnsRfqPrice.currency = rnsQuotationVendors.currency;
        rnsRfqPrice.regularRate = rnsQuotationVendors.regularRate;

        const copy = this.convertRFQ(rnsRfqPrice);
        return this.http
            .put<RnsRfqPrice>(this.resourceUrlRFQ, copy, { observe: 'response' })
            .map((res: HttpResponse<RnsRfqPrice>) => this.convertResponseRfq(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationVendors = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertResponseRfq(res: HttpResponse<RnsRfqPrice>): HttpResponse<RnsRfqPrice> {
        const body: RnsRfqPrice = this.convertItemFromServerRfq(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationVendors[]>): HttpResponse<RnsQuotationVendors[]> {
        const jsonResponse: RnsQuotationVendors[] = res.body;
        const body: RnsQuotationVendors[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    private convertArrayResponseVndrPrice(res: HttpResponse<BaseEntityVndrPrice[]>): HttpResponse<BaseEntityVndrPrice[]> {
        const jsonResponse: BaseEntityVndrPrice[] = res.body;
        const body: BaseEntityVndrPrice[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServerVndrPrice(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationVendors.
     */
    private convertItemFromServer(rnsQuotationVendors: RnsQuotationVendors): RnsQuotationVendors {
        const copy: RnsQuotationVendors = Object.assign({}, rnsQuotationVendors);
        return copy;
    }

    /**
     * Convert a returned JSON object to RnsQuotationVendors.
     */
    private convertItemFromServerVndrPrice(baseEntityVndrPrice: BaseEntityVndrPrice): BaseEntityVndrPrice {
        const copy: RnsQuotationVendors = Object.assign({}, baseEntityVndrPrice);
        return copy;
    }

    /**
     * Convert a returned JSON object to RnsRfqPrice.
     */
    private convertItemFromServerRfq(rnsRfqPrice: RnsRfqPrice): RnsRfqPrice {
        const copy: RnsRfqPrice = Object.assign({}, rnsRfqPrice);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsRfqPrice.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationVendors: RnsQuotationVendors): RnsQuotationVendors {
        const copy: RnsQuotationVendors = Object.assign({}, rnsQuotationVendors);
        return copy;
    }

    /**
     * Convert a RnsRfqPrice to a JSON which can be sent to the server.
     */
    private convertRFQ(rnsRfqPrice: RnsRfqPrice): RnsRfqPrice {
        const copy: RnsRfqPrice = Object.assign({}, rnsRfqPrice);
        return copy;
    }
}
