import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsEmpLinkMaster } from './rns-emp-link-master.model';
import { createRequestOption } from 'app/shared';
import { EmailTemplateBody } from 'app/entities/email-template-body';

type EntityResponseType = HttpResponse<RnsEmpLinkMaster>;

@Injectable()
export class RnsEmpLinkMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-emp-link-masters';

    private resourceUrlForward = SERVER_API_URL + 'api/rns-emp-forward-fetch';

    private resourceUrlForwardList = SERVER_API_URL + 'api/rns-emp-link-masters-fetch';

    constructor(private http: HttpClient) {}

    create(rnsEmpLinkMaster: RnsEmpLinkMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsEmpLinkMaster);
        return this.http
            .post<RnsEmpLinkMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsEmpLinkMaster: RnsEmpLinkMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsEmpLinkMaster);
        return this.http
            .put<RnsEmpLinkMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsEmpLinkMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsEmpLinkMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsEmpLinkMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsEmpLinkMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    forward(): Observable<HttpResponse<RnsEmpLinkMaster[]>> {
        return this.http
            .get<EmailTemplateBody[]>(this.resourceUrlForward, { observe: 'response' })
            .map((res: HttpResponse<RnsEmpLinkMaster[]>) => this.convertArrayResponse(res));
    }

    emplist(type: string): Observable<HttpResponse<RnsEmpLinkMaster[]>> {
        return this.http
            .get<EmailTemplateBody[]>(`${this.resourceUrlForwardList}/${type}`, { observe: 'response' })
            .map((res: HttpResponse<RnsEmpLinkMaster[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsEmpLinkMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsEmpLinkMaster[]>): HttpResponse<RnsEmpLinkMaster[]> {
        const jsonResponse: RnsEmpLinkMaster[] = res.body;
        const body: RnsEmpLinkMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsEmpLinkMaster.
     */
    private convertItemFromServer(rnsEmpLinkMaster: RnsEmpLinkMaster): RnsEmpLinkMaster {
        const copy: RnsEmpLinkMaster = Object.assign({}, rnsEmpLinkMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsEmpLinkMaster: RnsEmpLinkMaster): RnsEmpLinkMaster {
        const copy: RnsEmpLinkMaster = Object.assign({}, rnsEmpLinkMaster);
        return copy;
    }
}
