import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { VndrPrice } from './vndr-price.model';
import { createRequestOption } from 'app/shared';
import { VndrPriceBean } from './vndr-price-bean.model';

type EntityResponseType = HttpResponse<VndrPrice>;

@Injectable()
export class VndrPriceService {
    private resourceUrl = SERVER_API_URL + 'api/vndr-prices';

    constructor(private http: HttpClient) {}

    create(vndrPrice: VndrPrice): Observable<EntityResponseType> {
        const copy = this.convert(vndrPrice);
        return this.http
            .post<VndrPrice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vndrPrice: VndrPrice): Observable<EntityResponseType> {
        const copy = this.convert(vndrPrice);
        return this.http
            .put<VndrPrice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<VndrPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VndrPrice[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<VndrPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VndrPrice[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findByVariantId(id: number, code: string): Observable<EntityResponseType> {
        const vndrPriceBean = new VndrPriceBean();
        vndrPriceBean.variantId = id;
        vndrPriceBean.vrntcode = code;

        return this.http
            .post<VndrPrice>(this.resourceUrl + '/get-by-variant-post', vndrPriceBean, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    getHighestPrice(id: number): Observable<EntityResponseType> {
        return this.http
            .get<VndrPrice>(`${this.resourceUrl}-highest/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    getHighestPricePost(id: number, vndrCode: string): Observable<EntityResponseType> {
        const vndrPrice = new VndrPriceBean();
        vndrPrice.variantId = id;
        vndrPrice.vrntcode = vndrCode;
        return this.http
            .post<VndrPrice>(`${this.resourceUrl}-highest`, vndrPrice, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    getLowestPrice(id: number): Observable<EntityResponseType> {
        return this.http
            .get<VndrPrice>(`${this.resourceUrl}-lowest/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    getPosition(id: number): Observable<EntityResponseType> {
        return this.http
            .get<VndrPrice>(`${this.resourceUrl}-rank/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VndrPrice = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<VndrPrice[]>): HttpResponse<VndrPrice[]> {
        const jsonResponse: VndrPrice[] = res.body;
        const body: VndrPrice[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to VndrPrice.
     */
    private convertItemFromServer(vndrPrice: VndrPrice): VndrPrice {
        const copy: VndrPrice = Object.assign({}, vndrPrice);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(vndrPrice: VndrPrice): VndrPrice {
        const copy: VndrPrice = Object.assign({}, vndrPrice);
        return copy;
    }
}
