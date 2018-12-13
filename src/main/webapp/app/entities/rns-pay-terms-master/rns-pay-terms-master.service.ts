import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsPayTermsMaster } from './rns-pay-terms-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsPayTermsMaster>;

@Injectable()
export class RnsPayTermsMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-pay-terms-masters';

    constructor(private http: HttpClient) {}

    create(rnsPayTermsMaster: RnsPayTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsPayTermsMaster);
        return this.http
            .post<RnsPayTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsPayTermsMaster: RnsPayTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsPayTermsMaster);
        return this.http
            .put<RnsPayTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsPayTermsMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsPayTermsMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsPayTermsMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsPayTermsMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsPayTermsMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsPayTermsMaster[]>): HttpResponse<RnsPayTermsMaster[]> {
        const jsonResponse: RnsPayTermsMaster[] = res.body;
        const body: RnsPayTermsMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsPayTermsMaster.
     */
    private convertItemFromServer(rnsPayTermsMaster: RnsPayTermsMaster): RnsPayTermsMaster {
        const copy: RnsPayTermsMaster = Object.assign({}, rnsPayTermsMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsPayTermsMaster: RnsPayTermsMaster): RnsPayTermsMaster {
        const copy: RnsPayTermsMaster = Object.assign({}, rnsPayTermsMaster);
        return copy;
    }
}
