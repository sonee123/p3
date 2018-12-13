import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsQuotationEventDefination } from './rns-quotation-event-defination.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsQuotationEventDefination>;

@Injectable()
export class RnsQuotationEventDefinationService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-event-definations';

    constructor(private http: HttpClient) {}

    create(rnsQuotationEventDefination: RnsQuotationEventDefination): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationEventDefination);
        return this.http
            .post<RnsQuotationEventDefination>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationEventDefination: RnsQuotationEventDefination): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationEventDefination);
        return this.http
            .put<RnsQuotationEventDefination>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationEventDefination>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationEventDefination[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationEventDefination[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationEventDefination[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationEventDefination = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationEventDefination[]>): HttpResponse<RnsQuotationEventDefination[]> {
        const jsonResponse: RnsQuotationEventDefination[] = res.body;
        const body: RnsQuotationEventDefination[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationEventDefination.
     */
    private convertItemFromServer(rnsQuotationEventDefination: RnsQuotationEventDefination): RnsQuotationEventDefination {
        const copy: RnsQuotationEventDefination = Object.assign({}, rnsQuotationEventDefination);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationEventDefination: RnsQuotationEventDefination): RnsQuotationEventDefination {
        const copy: RnsQuotationEventDefination = Object.assign({}, rnsQuotationEventDefination);
        return copy;
    }
}
