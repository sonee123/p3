import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsQuotationDealTerms } from './rns-quotation-deal-terms.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsQuotationDealTerms>;

@Injectable()
export class RnsQuotationDealTermsService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-deal-terms';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsQuotationDealTerms: RnsQuotationDealTerms): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationDealTerms);
        return this.http
            .post<RnsQuotationDealTerms>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationDealTerms: RnsQuotationDealTerms): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationDealTerms);
        return this.http
            .put<RnsQuotationDealTerms>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationDealTerms>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationDealTerms[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationDealTerms[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationDealTerms[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationDealTerms = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationDealTerms[]>): HttpResponse<RnsQuotationDealTerms[]> {
        const jsonResponse: RnsQuotationDealTerms[] = res.body;
        const body: RnsQuotationDealTerms[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationDealTerms.
     */
    private convertItemFromServer(rnsQuotationDealTerms: RnsQuotationDealTerms): RnsQuotationDealTerms {
        const copy: RnsQuotationDealTerms = Object.assign({}, rnsQuotationDealTerms);

        copy.completionBy = this.dateUtils.convertLocalDateFromServer(rnsQuotationDealTerms.completionBy);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationDealTerms: RnsQuotationDealTerms): RnsQuotationDealTerms {
        const copy: RnsQuotationDealTerms = Object.assign({}, rnsQuotationDealTerms);

        copy.completionBy = this.dateUtils.convertLocalDateToServer(rnsQuotationDealTerms.completionBy);
        return copy;
    }
}
