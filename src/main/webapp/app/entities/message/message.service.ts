import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { Message } from './message.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<Message>;

@Injectable()
export class MessageService {
    private resourceUrl = SERVER_API_URL + 'api/messages';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(message: Message): Observable<EntityResponseType> {
        const copy = this.convert(message);
        return this.http
            .post<Message>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(message: Message): Observable<EntityResponseType> {
        const copy = this.convert(message);
        return this.http
            .put<Message>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<Message>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Message[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Message[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Message[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    queryInbox(req?: any): Observable<HttpResponse<Message[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Message[]>(this.resourceUrl + '-inbox', { params: options, observe: 'response' })
            .map((res: HttpResponse<Message[]>) => this.convertArrayResponse(res));
    }

    querySent(req?: any): Observable<HttpResponse<Message[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Message[]>(this.resourceUrl + '-sent', { params: options, observe: 'response' })
            .map((res: HttpResponse<Message[]>) => this.convertArrayResponse(res));
    }

    queryByQuotation(id: number): Observable<HttpResponse<Message[]>> {
        return this.http
            .get<Message[]>(`${this.resourceUrl}-quotation/${id}`, { observe: 'response' })
            .map((res: HttpResponse<Message[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Message = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<Message[]>): HttpResponse<Message[]> {
        const jsonResponse: Message[] = res.body;
        const body: Message[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Message.
     */
    private convertItemFromServer(message: Message): Message {
        const copy: Message = Object.assign({}, message);

        // copy.createdDate = this.dateUtils.convertLocalDateFromServer(message.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(message: Message): Message {
        const copy: Message = Object.assign({}, message);

        // copy.createdDate = this.dateUtils.convertLocalDateToServer(message.createdDate);
        return copy;
    }
}
