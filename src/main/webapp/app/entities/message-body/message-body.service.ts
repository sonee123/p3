import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { MessageBody } from './message-body.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<MessageBody>;

@Injectable()
export class MessageBodyService {
    private resourceUrl = SERVER_API_URL + 'api/message-bodies';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(messageBody: MessageBody): Observable<EntityResponseType> {
        const copy = this.convert(messageBody);
        return this.http
            .post<MessageBody>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(messageBody: MessageBody): Observable<EntityResponseType> {
        const copy = this.convert(messageBody);
        return this.http
            .put<MessageBody>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<MessageBody>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MessageBody[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<MessageBody[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MessageBody[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MessageBody = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<MessageBody[]>): HttpResponse<MessageBody[]> {
        const jsonResponse: MessageBody[] = res.body;
        const body: MessageBody[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to MessageBody.
     */
    private convertItemFromServer(messageBody: MessageBody): MessageBody {
        const copy: MessageBody = Object.assign({}, messageBody);

        copy.createdDate = this.dateUtils.convertLocalDateFromServer(messageBody.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(messageBody: MessageBody): MessageBody {
        const copy: MessageBody = Object.assign({}, messageBody);

        copy.createdDate = this.dateUtils.convertLocalDateToServer(messageBody.createdDate);
        return copy;
    }
}
