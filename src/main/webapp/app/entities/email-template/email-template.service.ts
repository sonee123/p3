import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { EmailTemplate } from './email-template.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<EmailTemplate>;

@Injectable()
export class EmailTemplateService {
    private resourceUrl = SERVER_API_URL + 'api/email-templates';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(emailTemplate: EmailTemplate): Observable<EntityResponseType> {
        const copy = this.convert(emailTemplate);
        return this.http
            .post<EmailTemplate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(emailTemplate: EmailTemplate): Observable<EntityResponseType> {
        const copy = this.convert(emailTemplate);
        return this.http
            .put<EmailTemplate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<EmailTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmailTemplate[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<EmailTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmailTemplate[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmailTemplate = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<EmailTemplate[]>): HttpResponse<EmailTemplate[]> {
        const jsonResponse: EmailTemplate[] = res.body;
        const body: EmailTemplate[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to EmailTemplate.
     */
    private convertItemFromServer(emailTemplate: EmailTemplate): EmailTemplate {
        const copy: EmailTemplate = Object.assign({}, emailTemplate);

        copy.createdDate = this.dateUtils.convertLocalDateFromServer(emailTemplate.createdDate);
        copy.lastUpdatedDate = this.dateUtils.convertLocalDateFromServer(emailTemplate.lastUpdatedDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(emailTemplate: EmailTemplate): EmailTemplate {
        const copy: EmailTemplate = Object.assign({}, emailTemplate);

        copy.createdDate = this.dateUtils.convertLocalDateToServer(emailTemplate.createdDate);
        copy.lastUpdatedDate = this.dateUtils.convertLocalDateToServer(emailTemplate.lastUpdatedDate);
        return copy;
    }
}
