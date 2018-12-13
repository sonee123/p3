import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsDelPlaceMaster } from './rns-del-place-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsDelPlaceMaster>;

@Injectable()
export class RnsDelPlaceMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-del-place-masters';

    constructor(private http: HttpClient) {}

    create(rnsDelPlaceMaster: RnsDelPlaceMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsDelPlaceMaster);
        return this.http
            .post<RnsDelPlaceMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsDelPlaceMaster: RnsDelPlaceMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsDelPlaceMaster);
        return this.http
            .put<RnsDelPlaceMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsDelPlaceMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsDelPlaceMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsDelPlaceMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsDelPlaceMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsDelPlaceMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsDelPlaceMaster[]>): HttpResponse<RnsDelPlaceMaster[]> {
        const jsonResponse: RnsDelPlaceMaster[] = res.body;
        const body: RnsDelPlaceMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsDelPlaceMaster.
     */
    private convertItemFromServer(rnsDelPlaceMaster: RnsDelPlaceMaster): RnsDelPlaceMaster {
        const copy: RnsDelPlaceMaster = Object.assign({}, rnsDelPlaceMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsDelPlaceMaster: RnsDelPlaceMaster): RnsDelPlaceMaster {
        const copy: RnsDelPlaceMaster = Object.assign({}, rnsDelPlaceMaster);
        return copy;
    }
}
