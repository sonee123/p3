import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsBuyerMaster } from './rns-buyer-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsBuyerMaster>;

@Injectable()
export class RnsBuyerMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-buyer-masters';

    constructor(private http: HttpClient) {}

    create(rnsBuyerMaster: RnsBuyerMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsBuyerMaster);
        return this.http
            .post<RnsBuyerMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsBuyerMaster: RnsBuyerMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsBuyerMaster);
        return this.http
            .put<RnsBuyerMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsBuyerMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsBuyerMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsBuyerMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsBuyerMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsBuyerMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsBuyerMaster[]>): HttpResponse<RnsBuyerMaster[]> {
        const jsonResponse: RnsBuyerMaster[] = res.body;
        const body: RnsBuyerMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsBuyerMaster.
     */
    private convertItemFromServer(rnsBuyerMaster: RnsBuyerMaster): RnsBuyerMaster {
        const copy: RnsBuyerMaster = Object.assign({}, rnsBuyerMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsBuyerMaster: RnsBuyerMaster): RnsBuyerMaster {
        const copy: RnsBuyerMaster = Object.assign({}, rnsBuyerMaster);
        return copy;
    }
}
