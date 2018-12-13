import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsEmpMaster } from './rns-emp-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsEmpMaster>;

@Injectable()
export class RnsEmpMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-emp-masters';

    constructor(private http: HttpClient) {}

    create(rnsEmpMaster: RnsEmpMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsEmpMaster);
        return this.http
            .post<RnsEmpMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsEmpMaster: RnsEmpMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsEmpMaster);
        return this.http
            .put<RnsEmpMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsEmpMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsEmpMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsEmpMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsEmpMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsEmpMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsEmpMaster[]>): HttpResponse<RnsEmpMaster[]> {
        const jsonResponse: RnsEmpMaster[] = res.body;
        const body: RnsEmpMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsEmpMaster.
     */
    private convertItemFromServer(rnsEmpMaster: RnsEmpMaster): RnsEmpMaster {
        const copy: RnsEmpMaster = Object.assign({}, rnsEmpMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsEmpMaster: RnsEmpMaster): RnsEmpMaster {
        const copy: RnsEmpMaster = Object.assign({}, rnsEmpMaster);
        return copy;
    }
}
