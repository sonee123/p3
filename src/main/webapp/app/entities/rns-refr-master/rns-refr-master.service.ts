import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsRefrMaster } from './rns-refr-master.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsRefrMaster>;

@Injectable()
export class RnsRefrMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-refr-masters';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsRefrMaster: RnsRefrMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsRefrMaster);
        return this.http
            .post<RnsRefrMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsRefrMaster: RnsRefrMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsRefrMaster);
        return this.http
            .put<RnsRefrMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsRefrMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsRefrMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsRefrMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsRefrMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsRefrMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsRefrMaster[]>): HttpResponse<RnsRefrMaster[]> {
        const jsonResponse: RnsRefrMaster[] = res.body;
        const body: RnsRefrMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsRefrMaster.
     */
    private convertItemFromServer(rnsRefrMaster: RnsRefrMaster): RnsRefrMaster {
        const copy: RnsRefrMaster = Object.assign({}, rnsRefrMaster);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsRefrMaster.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsRefrMaster: RnsRefrMaster): RnsRefrMaster {
        const copy: RnsRefrMaster = Object.assign({}, rnsRefrMaster);

        copy.createdDate = rnsRefrMaster.createdDate != null ? moment(rnsRefrMaster.createdDate) : null;
        return copy;
    }
}
