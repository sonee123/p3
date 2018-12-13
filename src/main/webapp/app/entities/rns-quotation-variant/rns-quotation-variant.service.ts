import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsQuotationVariant } from './rns-quotation-variant.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsQuotationVariant>;

@Injectable()
export class RnsQuotationVariantService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-variants';

    constructor(private http: HttpClient) {}

    create(rnsQuotationVariant: RnsQuotationVariant): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationVariant);
        return this.http
            .post<RnsQuotationVariant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationVariant: RnsQuotationVariant): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationVariant);
        return this.http
            .put<RnsQuotationVariant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationVariant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationVariant[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationVariant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVariant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getByQuotationId(id: number): Observable<HttpResponse<RnsQuotationVariant[]>> {
        return this.http
            .get<RnsQuotationVariant[]>(`${this.resourceUrl}/getByQuotationId/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVariant[]>) => this.convertArrayResponse(res));
    }

    getByVendor(req?: any): Observable<HttpResponse<RnsQuotationVariant[]>> {
        return this.http
            .get<RnsQuotationVariant[]>(this.resourceUrl + 'getByvendors', { observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVariant[]>) => this.convertArrayResponse(res));
    }

    updateMultiple(rnsQuotationVariants: RnsQuotationVariant[]): Observable<HttpResponse<RnsQuotationVariant[]>> {
        const dataTOsend = [];
        rnsQuotationVariants.forEach(variant => {
            const copy = this.convert(variant);
            dataTOsend.push(copy);
        });

        return this.http
            .put<RnsQuotationVariant[]>(this.resourceUrl + '/updatemultiple', dataTOsend, { observe: 'response' })
            .map((res: HttpResponse<RnsQuotationVariant[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationVariant = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationVariant[]>): HttpResponse<RnsQuotationVariant[]> {
        const jsonResponse: RnsQuotationVariant[] = res.body;
        const body: RnsQuotationVariant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationVariant.
     */
    private convertItemFromServer(rnsQuotationVariant: RnsQuotationVariant): RnsQuotationVariant {
        const copy: RnsQuotationVariant = Object.assign({}, rnsQuotationVariant);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationVariant: RnsQuotationVariant): RnsQuotationVariant {
        const copy: RnsQuotationVariant = Object.assign({}, rnsQuotationVariant);
        return copy;
    }
}
