import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsArticleMaster } from './rns-article-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsArticleMaster>;

@Injectable()
export class RnsArticleMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-article-masters';

    constructor(private http: HttpClient) {}

    create(rnsArticleMaster: RnsArticleMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsArticleMaster);
        return this.http
            .post<RnsArticleMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsArticleMaster: RnsArticleMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsArticleMaster);
        return this.http
            .put<RnsArticleMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsArticleMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsArticleMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsArticleMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsArticleMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findByCatg(id: number): Observable<HttpResponse<RnsArticleMaster[]>> {
        return this.http
            .get<RnsArticleMaster[]>(`${this.resourceUrl}/findbyCatg/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsArticleMaster[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsArticleMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsArticleMaster[]>): HttpResponse<RnsArticleMaster[]> {
        const jsonResponse: RnsArticleMaster[] = res.body;
        const body: RnsArticleMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsArticleMaster.
     */
    private convertItemFromServer(rnsArticleMaster: RnsArticleMaster): RnsArticleMaster {
        const copy: RnsArticleMaster = Object.assign({}, rnsArticleMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsArticleMaster: RnsArticleMaster): RnsArticleMaster {
        const copy: RnsArticleMaster = Object.assign({}, rnsArticleMaster);
        return copy;
    }
}
