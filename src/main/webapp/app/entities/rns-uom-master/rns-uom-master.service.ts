import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsUomMaster } from './rns-uom-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsUomMaster>;

@Injectable()
export class RnsUomMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-uom-masters';

    constructor(private http: HttpClient) {}

    create(rnsUomMaster: RnsUomMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsUomMaster);
        return this.http
            .post<RnsUomMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsUomMaster: RnsUomMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsUomMaster);
        return this.http
            .put<RnsUomMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsUomMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsUomMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsUomMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsUomMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsUomMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsUomMaster[]>): HttpResponse<RnsUomMaster[]> {
        const jsonResponse: RnsUomMaster[] = res.body;
        const body: RnsUomMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsUomMaster.
     */
    private convertItemFromServer(rnsUomMaster: RnsUomMaster): RnsUomMaster {
        const copy: RnsUomMaster = Object.assign({}, rnsUomMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsUomMaster: RnsUomMaster): RnsUomMaster {
        const copy: RnsUomMaster = Object.assign({}, rnsUomMaster);
        return copy;
    }
}
