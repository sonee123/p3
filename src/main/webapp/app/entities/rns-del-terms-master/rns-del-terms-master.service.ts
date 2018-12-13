import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsDelTermsMaster } from './rns-del-terms-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsDelTermsMaster>;

@Injectable()
export class RnsDelTermsMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-del-terms-masters';

    constructor(private http: HttpClient) {}

    create(rnsDelTermsMaster: RnsDelTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsDelTermsMaster);
        return this.http
            .post<RnsDelTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsDelTermsMaster: RnsDelTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsDelTermsMaster);
        return this.http
            .put<RnsDelTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsDelTermsMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsDelTermsMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsDelTermsMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsDelTermsMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsDelTermsMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsDelTermsMaster[]>): HttpResponse<RnsDelTermsMaster[]> {
        const jsonResponse: RnsDelTermsMaster[] = res.body;
        const body: RnsDelTermsMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsDelTermsMaster.
     */
    private convertItemFromServer(rnsDelTermsMaster: RnsDelTermsMaster): RnsDelTermsMaster {
        const copy: RnsDelTermsMaster = Object.assign({}, rnsDelTermsMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsDelTermsMaster: RnsDelTermsMaster): RnsDelTermsMaster {
        const copy: RnsDelTermsMaster = Object.assign({}, rnsDelTermsMaster);
        return copy;
    }
}
