import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsPchMaster } from './rns-pch-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsPchMaster>;

@Injectable()
export class RnsPchMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-pch-masters';

    constructor(private http: HttpClient) {}

    create(rnsPchMaster: RnsPchMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsPchMaster);
        return this.http
            .post<RnsPchMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsPchMaster: RnsPchMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsPchMaster);
        return this.http
            .put<RnsPchMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsPchMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsPchMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsPchMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsPchMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsPchMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsPchMaster[]>): HttpResponse<RnsPchMaster[]> {
        const jsonResponse: RnsPchMaster[] = res.body;
        const body: RnsPchMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsPchMaster.
     */
    private convertItemFromServer(rnsPchMaster: RnsPchMaster): RnsPchMaster {
        const copy: RnsPchMaster = Object.assign({}, rnsPchMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsPchMaster: RnsPchMaster): RnsPchMaster {
        const copy: RnsPchMaster = Object.assign({}, rnsPchMaster);
        return copy;
    }
}
