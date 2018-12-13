import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { Template } from './template.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<Template>;

@Injectable()
export class TemplateService {
    private resourceUrl = SERVER_API_URL + 'api/templates';

    constructor(private http: HttpClient) {}

    create(template: Template): Observable<EntityResponseType> {
        const copy = this.convert(template);
        return this.http
            .post<Template>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(template: Template): Observable<EntityResponseType> {
        const copy = this.convert(template);
        return this.http
            .put<Template>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<Template>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Template[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Template[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Template[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    copy(id: number): Observable<EntityResponseType> {
        return this.http
            .get<Template>(`${this.resourceUrl}-copy/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    queryByCatgCode(id: number): Observable<HttpResponse<Template[]>> {
        return this.http
            .get<Template[]>(this.resourceUrl + '/by-Catg-code/' + id, { observe: 'response' })
            .map((res: HttpResponse<Template[]>) => this.convertArrayResponse(res));
    }

    queryByCatgCodeActivate(id: number): Observable<HttpResponse<Template[]>> {
        return this.http
            .get<Template[]>(this.resourceUrl + '/by-Catg-code-activated/' + id, { observe: 'response' })
            .map((res: HttpResponse<Template[]>) => this.convertArrayResponse(res));
    }

    updateFlag(template: Template): Observable<EntityResponseType> {
        const copy = this.convert(template);
        return this.http
            .put<Template>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    download(type: string): any {
        return this.http
            .get(`${this.resourceUrl}-download/${type}`, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Template = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<Template[]>): HttpResponse<Template[]> {
        const jsonResponse: Template[] = res.body;
        const body: Template[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Template.
     */
    private convertItemFromServer(template: Template): Template {
        const copy: Template = Object.assign({}, template);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(template: Template): Template {
        const copy: Template = Object.assign({}, template);
        return copy;
    }
}
