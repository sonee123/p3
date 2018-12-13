import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsRelation } from './rns-relation.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsRelation>;

@Injectable()
export class RnsRelationService {
    private resourceUrl = SERVER_API_URL + 'api/rns-relations';

    constructor(private http: HttpClient) {}

    create(rnsRelation: RnsRelation): Observable<EntityResponseType> {
        const copy = this.convert(rnsRelation);
        return this.http
            .post<RnsRelation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsRelation: RnsRelation): Observable<EntityResponseType> {
        const copy = this.convert(rnsRelation);
        return this.http
            .put<RnsRelation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsRelation[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsRelation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsRelation = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsRelation[]>): HttpResponse<RnsRelation[]> {
        const jsonResponse: RnsRelation[] = res.body;
        const body: RnsRelation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsRelation.
     */
    private convertItemFromServer(rnsRelation: RnsRelation): RnsRelation {
        const copy: RnsRelation = Object.assign({}, rnsRelation);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsRelation: RnsRelation): RnsRelation {
        const copy: RnsRelation = Object.assign({}, rnsRelation);
        return copy;
    }
}
