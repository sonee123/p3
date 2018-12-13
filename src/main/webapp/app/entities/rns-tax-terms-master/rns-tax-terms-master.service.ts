import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsTaxTermsMaster } from './rns-tax-terms-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsTaxTermsMaster>;

@Injectable()
export class RnsTaxTermsMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-tax-terms-masters';

    constructor(private http: HttpClient) {}

    create(rnsTaxTermsMaster: RnsTaxTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsTaxTermsMaster);
        return this.http
            .post<RnsTaxTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsTaxTermsMaster: RnsTaxTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsTaxTermsMaster);
        return this.http
            .put<RnsTaxTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsTaxTermsMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsTaxTermsMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsTaxTermsMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsTaxTermsMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsTaxTermsMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsTaxTermsMaster[]>): HttpResponse<RnsTaxTermsMaster[]> {
        const jsonResponse: RnsTaxTermsMaster[] = res.body;
        const body: RnsTaxTermsMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsTaxTermsMaster.
     */
    private convertItemFromServer(rnsTaxTermsMaster: RnsTaxTermsMaster): RnsTaxTermsMaster {
        const copy: RnsTaxTermsMaster = Object.assign({}, rnsTaxTermsMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsTaxTermsMaster: RnsTaxTermsMaster): RnsTaxTermsMaster {
        const copy: RnsTaxTermsMaster = Object.assign({}, rnsTaxTermsMaster);
        return copy;
    }
}
