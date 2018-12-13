import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsSourceTeamMaster } from './rns-source-team-master.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsSourceTeamMaster>;

@Injectable()
export class RnsSourceTeamMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-source-team-masters';

    constructor(private http: HttpClient) {}

    create(rnsSourceTeamMaster: RnsSourceTeamMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsSourceTeamMaster);
        return this.http
            .post<RnsSourceTeamMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsSourceTeamMaster: RnsSourceTeamMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsSourceTeamMaster);
        return this.http
            .put<RnsSourceTeamMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsSourceTeamMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsSourceTeamMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsSourceTeamMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsSourceTeamMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    querylogin(req?: any): Observable<HttpResponse<RnsSourceTeamMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsSourceTeamMaster[]>(this.resourceUrl + '-login', { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsSourceTeamMaster[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsSourceTeamMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsSourceTeamMaster[]>): HttpResponse<RnsSourceTeamMaster[]> {
        const jsonResponse: RnsSourceTeamMaster[] = res.body;
        const body: RnsSourceTeamMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsSourceTeamMaster.
     */
    private convertItemFromServer(rnsSourceTeamMaster: RnsSourceTeamMaster): RnsSourceTeamMaster {
        const copy: RnsSourceTeamMaster = Object.assign({}, rnsSourceTeamMaster);

        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsSourceTeamMaster: RnsSourceTeamMaster): RnsSourceTeamMaster {
        const copy: RnsSourceTeamMaster = Object.assign({}, rnsSourceTeamMaster);
        copy.createdDate = rnsSourceTeamMaster.createdDate != null ? moment(rnsSourceTeamMaster.createdDate) : null;
        return copy;
    }
}
