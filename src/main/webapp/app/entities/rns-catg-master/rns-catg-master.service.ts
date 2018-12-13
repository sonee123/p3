import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsCatgMaster } from './rns-catg-master.model';
import { RnsCatgUser } from './rns-catg-user.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsCatgMaster>;

@Injectable()
export class RnsCatgMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-catg-masters';

    constructor(private http: HttpClient) {}

    create(rnsCatgMaster: RnsCatgMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsCatgMaster);
        return this.http
            .post<RnsCatgMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsCatgMaster: RnsCatgMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsCatgMaster);
        return this.http
            .put<RnsCatgMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsCatgMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsCatgMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsCatgMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsCatgMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    querylogin(req?: any): Observable<HttpResponse<RnsCatgMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsCatgMaster[]>(this.resourceUrl + '-login', { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsCatgMaster[]>) => this.convertArrayResponse(res));
    }

    findPost(login: string): Observable<HttpResponse<RnsCatgUser>> {
        const rnsCatgUser = new RnsCatgUser();
        rnsCatgUser.login = login;
        const copy = this.convertUser(rnsCatgUser);
        return this.http
            .post<RnsCatgUser>(`${this.resourceUrl}-users`, copy, { observe: 'response' })
            .map((res: HttpResponse<RnsCatgUser>) => this.convertResponseUser(res));
    }

    createUser(rnsCatgUser: RnsCatgUser): Observable<HttpResponse<RnsCatgUser>> {
        const copy = this.convertUser(rnsCatgUser);
        return this.http
            .post<RnsCatgUser>(`${this.resourceUrl}-users-save`, copy, { observe: 'response' })
            .map((res: HttpResponse<RnsCatgUser>) => this.convertResponseUser(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsCatgMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertResponseUser(res: HttpResponse<RnsCatgUser>): HttpResponse<RnsCatgUser> {
        const body: RnsCatgUser = this.convertItemFromServerUser(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsCatgMaster[]>): HttpResponse<RnsCatgMaster[]> {
        const jsonResponse: RnsCatgMaster[] = res.body;
        const body: RnsCatgMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsCatgMaster.
     */
    private convertItemFromServer(rnsCatgMaster: RnsCatgMaster): RnsCatgMaster {
        const copy: RnsCatgMaster = Object.assign({}, rnsCatgMaster);
        return copy;
    }

    /**
     * Convert a returned JSON object to RnsCatgMaster.
     */
    private convertItemFromServerUser(rnsCatgUser: RnsCatgUser): RnsCatgUser {
        const copy: RnsCatgUser = Object.assign({}, rnsCatgUser);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsCatgMaster: RnsCatgMaster): RnsCatgMaster {
        const copy: RnsCatgMaster = Object.assign({}, rnsCatgMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convertUser(rnsCatgUser: RnsCatgUser): RnsCatgUser {
        const copy: RnsCatgUser = Object.assign({}, rnsCatgUser);
        return copy;
    }
}
