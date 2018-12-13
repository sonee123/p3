import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsForwardTypeMaster } from './rns-forward-type-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsForwardTypeMaster>;

@Injectable()
export class RnsForwardTypeMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-forward-type-masters';

    constructor(private http: HttpClient) {}

    create(rnsForwardTypeMaster: RnsForwardTypeMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsForwardTypeMaster);
        return this.http
            .post<RnsForwardTypeMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsForwardTypeMaster: RnsForwardTypeMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsForwardTypeMaster);
        return this.http
            .put<RnsForwardTypeMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsForwardTypeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsForwardTypeMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsForwardTypeMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsForwardTypeMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsForwardTypeMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsForwardTypeMaster[]>): HttpResponse<RnsForwardTypeMaster[]> {
        const jsonResponse: RnsForwardTypeMaster[] = res.body;
        const body: RnsForwardTypeMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsForwardTypeMaster.
     */
    private convertItemFromServer(rnsForwardTypeMaster: RnsForwardTypeMaster): RnsForwardTypeMaster {
        const copy: RnsForwardTypeMaster = Object.assign({}, rnsForwardTypeMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsForwardTypeMaster: RnsForwardTypeMaster): RnsForwardTypeMaster {
        const copy: RnsForwardTypeMaster = Object.assign({}, rnsForwardTypeMaster);
        return copy;
    }
}
