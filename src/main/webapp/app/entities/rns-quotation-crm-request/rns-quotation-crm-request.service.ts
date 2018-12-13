import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsQuotationCrmRequest } from './rns-quotation-crm-request.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsQuotationCrmRequest>;

@Injectable()
export class RnsQuotationCrmRequestService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-crm-requests';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsQuotationCrmRequest: RnsQuotationCrmRequest): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationCrmRequest);
        return this.http
            .post<RnsQuotationCrmRequest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationCrmRequest: RnsQuotationCrmRequest): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationCrmRequest);
        return this.http
            .put<RnsQuotationCrmRequest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationCrmRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationCrmRequest[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationCrmRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationCrmRequest[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationCrmRequest = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationCrmRequest[]>): HttpResponse<RnsQuotationCrmRequest[]> {
        const jsonResponse: RnsQuotationCrmRequest[] = res.body;
        const body: RnsQuotationCrmRequest[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationCrmRequest.
     */
    private convertItemFromServer(rnsQuotationCrmRequest: RnsQuotationCrmRequest): RnsQuotationCrmRequest {
        const copy: RnsQuotationCrmRequest = Object.assign({}, rnsQuotationCrmRequest);

        copy.targetPcd = this.dateUtils.convertLocalDateFromServer(rnsQuotationCrmRequest.targetPcd);
        copy.date = this.dateUtils.convertDateTimeFromServer(rnsQuotationCrmRequest.date);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationCrmRequest: RnsQuotationCrmRequest): RnsQuotationCrmRequest {
        const copy: RnsQuotationCrmRequest = Object.assign({}, rnsQuotationCrmRequest);

        copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotationCrmRequest.targetPcd);
        copy.date = rnsQuotationCrmRequest.date != null ? moment(rnsQuotationCrmRequest.date) : null;
        return copy;
    }
}
