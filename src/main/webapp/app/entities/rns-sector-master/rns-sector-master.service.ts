import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsSectorMaster } from './rns-sector-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsSectorMaster>;

@Injectable()
export class RnsSectorMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-sector-masters';

    constructor(private http: HttpClient) {}

    create(rnsSectorMaster: RnsSectorMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsSectorMaster);
        return this.http
            .post<RnsSectorMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsSectorMaster: RnsSectorMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsSectorMaster);
        return this.http
            .put<RnsSectorMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsSectorMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsSectorMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsSectorMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsSectorMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsSectorMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsSectorMaster[]>): HttpResponse<RnsSectorMaster[]> {
        const jsonResponse: RnsSectorMaster[] = res.body;
        const body: RnsSectorMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsSectorMaster.
     */
    private convertItemFromServer(rnsSectorMaster: RnsSectorMaster): RnsSectorMaster {
        const copy: RnsSectorMaster = Object.assign({}, rnsSectorMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsSectorMaster: RnsSectorMaster): RnsSectorMaster {
        const copy: RnsSectorMaster = Object.assign({}, rnsSectorMaster);
        return copy;
    }
}
