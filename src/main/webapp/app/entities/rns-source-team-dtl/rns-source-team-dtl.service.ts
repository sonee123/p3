import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsSourceTeamDtl } from './rns-source-team-dtl.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsSourceTeamDtl>;

@Injectable()
export class RnsSourceTeamDtlService {
    private resourceUrl = SERVER_API_URL + 'api/rns-source-team-dtls';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsSourceTeamDtl: RnsSourceTeamDtl): Observable<EntityResponseType> {
        const copy = this.convert(rnsSourceTeamDtl);
        return this.http
            .post<RnsSourceTeamDtl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsSourceTeamDtl: RnsSourceTeamDtl): Observable<EntityResponseType> {
        const copy = this.convert(rnsSourceTeamDtl);
        return this.http
            .put<RnsSourceTeamDtl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsSourceTeamDtl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsSourceTeamDtl[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsSourceTeamDtl[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsSourceTeamDtl[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsSourceTeamDtl = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsSourceTeamDtl[]>): HttpResponse<RnsSourceTeamDtl[]> {
        const jsonResponse: RnsSourceTeamDtl[] = res.body;
        const body: RnsSourceTeamDtl[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsSourceTeamDtl.
     */
    private convertItemFromServer(rnsSourceTeamDtl: RnsSourceTeamDtl): RnsSourceTeamDtl {
        const copy: RnsSourceTeamDtl = Object.assign({}, rnsSourceTeamDtl);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsSourceTeamDtl.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsSourceTeamDtl: RnsSourceTeamDtl): RnsSourceTeamDtl {
        const copy: RnsSourceTeamDtl = Object.assign({}, rnsSourceTeamDtl);

        // copy.createdDate = this.dateUtils.toDate(rnsSourceTeamDtl.createdDate);
        copy.createdDate = rnsSourceTeamDtl.createdDate != null ? moment(rnsSourceTeamDtl.createdDate) : null;
        return copy;
    }
}
