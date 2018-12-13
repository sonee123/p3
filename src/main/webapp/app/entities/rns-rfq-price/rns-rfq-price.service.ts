import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsRfqPrice } from './rns-rfq-price.model';
import { createRequestOption } from 'app/shared';
import { RnsQuotationVendors } from 'app/entities/rns-quotation-vendors';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsRfqPrice>;

@Injectable()
export class RnsRfqPriceService {
    private resourceUrl = SERVER_API_URL + 'api/rns-rfq-prices';
    private resourceUrlVendors = SERVER_API_URL + 'api/rns-quotation-vendors';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsRfqPrice: RnsRfqPrice): Observable<EntityResponseType> {
        const copy = this.convert(rnsRfqPrice);
        return this.http
            .post<RnsRfqPrice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsRfqPrice: RnsRfqPrice): Observable<EntityResponseType> {
        const copy = this.convert(rnsRfqPrice);
        return this.http
            .put<RnsRfqPrice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsRfqPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsRfqPrice[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsRfqPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsRfqPrice[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    createrfqvendors(rnsRfqPrice: RnsRfqPrice): Observable<EntityResponseType> {
        const copy = this.convert(rnsRfqPrice);

        return this.http
            .post<RnsRfqPrice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponseVendors(res));
    }

    findByVendor(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsRfqPrice>(`${this.resourceUrl}-vendor/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findVendors(id: number): Observable<HttpResponse<RnsQuotationVendors>> {
        return this.http
            .get<RnsQuotationVendors>(`${this.resourceUrlVendors}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVendors>) => this.convertResponse(res));
    }

    popup(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsRfqPrice>(`${this.resourceUrl}-popup/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsRfqPrice = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertResponseVendors(res: HttpResponse<RnsQuotationVendors>): EntityResponseType {
        const body: RnsQuotationVendors = this.convertItemFromServerVendors(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsRfqPrice[]>): HttpResponse<RnsRfqPrice[]> {
        const jsonResponse: RnsRfqPrice[] = res.body;
        const body: RnsRfqPrice[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsRfqPrice.
     */
    private convertItemFromServer(rnsRfqPrice: RnsRfqPrice): RnsRfqPrice {
        const copy: RnsRfqPrice = Object.assign({}, rnsRfqPrice);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsRfqPrice.createdDate);
        return copy;
    }

    /**
     * Convert a returned JSON object to RnsQuotationVendors.
     */
    private convertItemFromServerVendors(rnsQuotationVendors: RnsQuotationVendors): RnsQuotationVendors {
        const copy: RnsQuotationVendors = Object.assign({}, rnsQuotationVendors);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsRfqPrice: RnsRfqPrice): RnsRfqPrice {
        const copy: RnsRfqPrice = Object.assign({}, rnsRfqPrice);

        copy.createdDate = rnsRfqPrice.createdDate != null ? moment(rnsRfqPrice.createdDate) : null;
        return copy;
    }
}
