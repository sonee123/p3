import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from 'app/app.constants';
import { Feedback } from './feedback.model';
import { createRequestOption } from 'app/shared';
import { tap } from 'rxjs/operators';
type EntityResponseType = HttpResponse<Feedback>;

@Injectable()
export class FeedbackService {
    private resourceUrl = SERVER_API_URL + 'api/feedbacks';

    constructor(private http: HttpClient) {}

    create(feedback: Feedback): Observable<EntityResponseType> {
        const copy = this.convert(feedback);
        return this.http
            .post<Feedback>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(feedback: Feedback): Observable<EntityResponseType> {
        const copy = this.convert(feedback);
        return this.http
            .put<Feedback>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<Feedback>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Feedback[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Feedback[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Feedback[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    createupload(file: File, feedback: Feedback): Observable<EntityResponseType> {
        const formdata: FormData = new FormData();
        formdata.append('file', file);
        formdata.append('email', feedback.yourEmailId);
        formdata.append('message', feedback.remarks);
        // const copy = this.convert(feedback);
        return this.http
            .post<Feedback>(this.resourceUrl, formdata, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    download(id: number): any {
        return this.http
            .get(`${this.resourceUrl}-download/${id}`, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Feedback = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<Feedback[]>): HttpResponse<Feedback[]> {
        const jsonResponse: Feedback[] = res.body;
        const body: Feedback[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Feedback.
     */
    private convertItemFromServer(feedback: Feedback): Feedback {
        const copy: Feedback = Object.assign({}, feedback);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(feedback: Feedback): Feedback {
        const copy: Feedback = Object.assign({}, feedback);
        return copy;
    }
}
