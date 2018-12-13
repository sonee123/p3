import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { EmailTemplateBody } from './email-template-body.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<EmailTemplateBody>;

@Injectable()
export class EmailTemplateBodyService {
    private resourceUrl = SERVER_API_URL + 'api/email-template-bodies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(emailTemplateBody: EmailTemplateBody): Observable<EntityResponseType> {
        const copy = this.convert(emailTemplateBody);
        return this.http
            .post<EmailTemplateBody>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(emailTemplateBody: EmailTemplateBody): Observable<EntityResponseType> {
        const copy = this.convert(emailTemplateBody);
        return this.http
            .put<EmailTemplateBody>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<EmailTemplateBody>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EmailTemplateBody[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<EmailTemplateBody[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EmailTemplateBody[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EmailTemplateBody = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<EmailTemplateBody[]>): HttpResponse<EmailTemplateBody[]> {
        const jsonResponse: EmailTemplateBody[] = res.body;
        const body: EmailTemplateBody[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to EmailTemplateBody.
     */
    private convertItemFromServer(emailTemplateBody: EmailTemplateBody): EmailTemplateBody {
        const copy: EmailTemplateBody = Object.assign({}, emailTemplateBody);

        copy.createdDate = this.dateUtils.convertLocalDateFromServer(emailTemplateBody.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(emailTemplateBody: EmailTemplateBody): EmailTemplateBody {
        const copy: EmailTemplateBody = Object.assign({}, emailTemplateBody);

        copy.createdDate = this.dateUtils.convertLocalDateToServer(emailTemplateBody.createdDate);
        return copy;
    }
}
