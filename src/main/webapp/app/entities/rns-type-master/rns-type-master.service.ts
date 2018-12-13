import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsTypeMaster } from './rns-type-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsTypeMaster>;

@Injectable()
export class RnsTypeMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-type-masters';

    constructor(private http: HttpClient) {}

    create(rnsTypeMaster: RnsTypeMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsTypeMaster);
        return this.http
            .post<RnsTypeMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsTypeMaster: RnsTypeMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsTypeMaster);
        return this.http
            .put<RnsTypeMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsTypeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsTypeMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsTypeMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsTypeMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsTypeMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    queryByCatg(id: number): Observable<HttpResponse<RnsTypeMaster[]>> {
        return this.http
            .get<RnsTypeMaster[]>(`${this.resourceUrl}-by-catg/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsTypeMaster[]>) => this.convertArrayResponse(res));
    }

    private convertArrayResponse(res: HttpResponse<RnsTypeMaster[]>): HttpResponse<RnsTypeMaster[]> {
        const jsonResponse: RnsTypeMaster[] = res.body;
        const body: RnsTypeMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsTypeMaster.
     */
    private convertItemFromServer(rnsTypeMaster: RnsTypeMaster): RnsTypeMaster {
        const copy: RnsTypeMaster = Object.assign({}, rnsTypeMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsTypeMaster: RnsTypeMaster): RnsTypeMaster {
        const copy: RnsTypeMaster = Object.assign({}, rnsTypeMaster);
        return copy;
    }
}
