import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsCrmRequestMaster } from './rns-crm-request-master.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsCrmRequestMaster>;

@Injectable()
export class RnsCrmRequestMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-crm-request-masters';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsCrmRequestMaster: RnsCrmRequestMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsCrmRequestMaster);
        return this.http
            .post<RnsCrmRequestMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsCrmRequestMaster: RnsCrmRequestMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsCrmRequestMaster);
        return this.http
            .put<RnsCrmRequestMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsCrmRequestMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsCrmRequestMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsCrmRequestMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsCrmRequestMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsCrmRequestMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsCrmRequestMaster[]>): HttpResponse<RnsCrmRequestMaster[]> {
        const jsonResponse: RnsCrmRequestMaster[] = res.body;
        const body: RnsCrmRequestMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsCrmRequestMaster.
     */
    private convertItemFromServer(rnsCrmRequestMaster: RnsCrmRequestMaster): RnsCrmRequestMaster {
        const copy: RnsCrmRequestMaster = Object.assign({}, rnsCrmRequestMaster);

        copy.targetPcd = this.dateUtils.convertDateTimeFromServer(rnsCrmRequestMaster.targetPcd);
        copy.date = this.dateUtils.convertDateTimeFromServer(rnsCrmRequestMaster.date);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsCrmRequestMaster: RnsCrmRequestMaster): RnsCrmRequestMaster {
        const copy: RnsCrmRequestMaster = Object.assign({}, rnsCrmRequestMaster);

        copy.targetPcd = rnsCrmRequestMaster.targetPcd != null ? moment(rnsCrmRequestMaster.targetPcd) : null;
        copy.date = rnsCrmRequestMaster.date != null ? moment(rnsCrmRequestMaster.date) : null;
        return copy;
    }
}
