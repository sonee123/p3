import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsUpchargeDtl } from './rns-upcharge-dtl.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsUpchargeDtl>;

@Injectable()
export class RnsUpchargeDtlService {
    private resourceUrl = SERVER_API_URL + 'api/rns-upcharge-dtls';
    rnsUpchargeDtls: RnsUpchargeDtl[];

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsUpchargeDtl: RnsUpchargeDtl): Observable<EntityResponseType> {
        const copy = this.convert(rnsUpchargeDtl);
        return this.http
            .post<RnsUpchargeDtl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsUpchargeDtl: RnsUpchargeDtl): Observable<EntityResponseType> {
        const copy = this.convert(rnsUpchargeDtl);
        return this.http
            .put<RnsUpchargeDtl>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsUpchargeDtl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsUpchargeDtl[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsUpchargeDtl[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsUpchargeDtl[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    createMulti(rnsUpchargeDtls: RnsUpchargeDtl[]): Observable<HttpResponse<RnsUpchargeDtl[]>> {
        const copy = this.convertArray(rnsUpchargeDtls);
        return this.http
            .post<RnsUpchargeDtl[]>(this.resourceUrl + '-multi', copy, { observe: 'response' })
            .map((res: HttpResponse<RnsUpchargeDtl[]>) => this.convertArrayResponse(res));
    }

    findVendors(id: number): Observable<HttpResponse<RnsUpchargeDtl[]>> {
        return this.http
            .get<RnsUpchargeDtl[]>(`${this.resourceUrl}-vendors/${id}`, { observe: 'response' })
            .map((res: HttpResponse<RnsUpchargeDtl[]>) => this.convertArrayResponse(res));
    }

    select(id: number): Observable<HttpResponse<RnsUpchargeDtl>> {
        return this.http
            .get<RnsUpchargeDtl>(`${this.resourceUrl}-select/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsUpchargeDtl = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsUpchargeDtl[]>): HttpResponse<RnsUpchargeDtl[]> {
        const jsonResponse: RnsUpchargeDtl[] = res.body;
        const body: RnsUpchargeDtl[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsUpchargeDtl.
     */
    private convertItemFromServer(rnsUpchargeDtl: RnsUpchargeDtl): RnsUpchargeDtl {
        const copy: RnsUpchargeDtl = Object.assign({}, rnsUpchargeDtl);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsUpchargeDtl.createdDate);
        return copy;
    }

    private convertArray(res: RnsUpchargeDtl[]): RnsUpchargeDtl[] {
        const jsonResponse: RnsUpchargeDtl[] = res;
        const body: RnsUpchargeDtl[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convert(jsonResponse[i]));
        }
        return body;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsUpchargeDtl: RnsUpchargeDtl): RnsUpchargeDtl {
        const copy: RnsUpchargeDtl = Object.assign({}, rnsUpchargeDtl);

        // copy.createdDate = this.dateUtils.toDate(rnsUpchargeDtl.createdDate);
        copy.createdDate = rnsUpchargeDtl.createdDate != null ? moment(rnsUpchargeDtl.createdDate) : null;
        return copy;
    }
}
