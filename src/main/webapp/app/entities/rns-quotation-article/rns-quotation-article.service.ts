import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsQuotationArticle } from './rns-quotation-article.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsQuotationArticle>;

@Injectable()
export class RnsQuotationArticleService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotation-articles';

    constructor(private http: HttpClient) {}

    create(rnsQuotationArticle: RnsQuotationArticle): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationArticle);
        return this.http
            .post<RnsQuotationArticle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotationArticle: RnsQuotationArticle): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotationArticle);
        return this.http
            .put<RnsQuotationArticle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotationArticle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotationArticle[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotationArticle[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotationArticle[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotationArticle = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotationArticle[]>): HttpResponse<RnsQuotationArticle[]> {
        const jsonResponse: RnsQuotationArticle[] = res.body;
        const body: RnsQuotationArticle[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotationArticle.
     */
    private convertItemFromServer(rnsQuotationArticle: RnsQuotationArticle): RnsQuotationArticle {
        const copy: RnsQuotationArticle = Object.assign({}, rnsQuotationArticle);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotationArticle: RnsQuotationArticle): RnsQuotationArticle {
        const copy: RnsQuotationArticle = Object.assign({}, rnsQuotationArticle);
        return copy;
    }
}
