import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsUpchargeMaster } from './rns-upcharge-master.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsUpchargeMaster>;

@Injectable()
export class RnsUpchargeMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-upcharge-masters';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsUpchargeMaster: RnsUpchargeMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsUpchargeMaster);
        return this.http
            .post<RnsUpchargeMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsUpchargeMaster: RnsUpchargeMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsUpchargeMaster);
        return this.http
            .put<RnsUpchargeMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsUpchargeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsUpchargeMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsUpchargeMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsUpchargeMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsUpchargeMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsUpchargeMaster[]>): HttpResponse<RnsUpchargeMaster[]> {
        const jsonResponse: RnsUpchargeMaster[] = res.body;
        const body: RnsUpchargeMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsUpchargeMaster.
     */
    private convertItemFromServer(rnsUpchargeMaster: RnsUpchargeMaster): RnsUpchargeMaster {
        const copy: RnsUpchargeMaster = Object.assign({}, rnsUpchargeMaster);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsUpchargeMaster.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsUpchargeMaster: RnsUpchargeMaster): RnsUpchargeMaster {
        const copy: RnsUpchargeMaster = Object.assign({}, rnsUpchargeMaster);

        // copy.createdDate = this.dateUtils.toDate(rnsUpchargeMaster.createdDate);
        copy.createdDate = rnsUpchargeMaster.createdDate != null ? moment(rnsUpchargeMaster.createdDate) : null;
        return copy;
    }
}
