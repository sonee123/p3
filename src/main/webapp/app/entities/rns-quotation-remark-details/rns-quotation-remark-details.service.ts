import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsQuotationRemarkDetails } from './rns-quotation-remark-details.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsQuotationRemarkDetails>;

@Injectable()
export class RnsQuotationRemarkDetailsService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-remark-details';

    private resourceUrlPopup = SERVER_API_URL + 'api/rns-quotation-remark-details-call-quote';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsQuotationRemarkDetails: RnsQuotationRemarkDetails): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationRemarkDetails);
        return this.http
            .post<RnsQuotationRemarkDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationRemarkDetails: RnsQuotationRemarkDetails): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationRemarkDetails);
        console.log('line 29', copy);
        return this.http
            .put<RnsQuotationRemarkDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    reopen(rnsQuotationRemarkDetails: RnsQuotationRemarkDetails): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationRemarkDetails);
        return this.http
            .put<RnsQuotationRemarkDetails>(this.resourceUrl + '-reopen', copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationRemarkDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationRemarkDetails[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationRemarkDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationRemarkDetails[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    popup(id: number, type: string): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationRemarkDetails>(`${this.resourceUrlPopup}/${id}/${type}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationRemarkDetails = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationRemarkDetails[]>): HttpResponse<RnsQuotationRemarkDetails[]> {
        const jsonResponse: RnsQuotationRemarkDetails[] = res.body;
        const body: RnsQuotationRemarkDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationRemarkDetails.
     */
    private convertItemFromServer(rnsQuotationRemarkDetails: RnsQuotationRemarkDetails): RnsQuotationRemarkDetails {
        const copy: RnsQuotationRemarkDetails = Object.assign({}, rnsQuotationRemarkDetails);

        copy.authDate = this.dateUtils.convertLocalDateFromServer(rnsQuotationRemarkDetails.authDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationRemarkDetails: RnsQuotationRemarkDetails): RnsQuotationRemarkDetails {
        const copy: RnsQuotationRemarkDetails = Object.assign({}, rnsQuotationRemarkDetails);

        copy.authDate = this.dateUtils.convertLocalDateToServer(rnsQuotationRemarkDetails.authDate);
        return copy;
    }
}
