import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsVendorRemarkComment } from './rns-vendor-remark-comment.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsVendorRemarkComment>;

@Injectable()
export class RnsVendorRemarkCommentService {
    private resourceUrl = SERVER_API_URL + 'api/rns-vendor-remark-comments';

    constructor(private http: HttpClient) {}

    create(rnsVendorRemarkComment: RnsVendorRemarkComment): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorRemarkComment);
        return this.http
            .post<RnsVendorRemarkComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsVendorRemarkComment: RnsVendorRemarkComment): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorRemarkComment);
        return this.http
            .put<RnsVendorRemarkComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsVendorRemarkComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsVendorRemarkComment[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorRemarkComment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsVendorRemarkComment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsVendorRemarkComment = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsVendorRemarkComment[]>): HttpResponse<RnsVendorRemarkComment[]> {
        const jsonResponse: RnsVendorRemarkComment[] = res.body;
        const body: RnsVendorRemarkComment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsVendorRemarkComment.
     */
    private convertItemFromServer(rnsVendorRemarkComment: RnsVendorRemarkComment): RnsVendorRemarkComment {
        const copy: RnsVendorRemarkComment = Object.assign({}, rnsVendorRemarkComment);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsVendorRemarkComment: RnsVendorRemarkComment): RnsVendorRemarkComment {
        const copy: RnsVendorRemarkComment = Object.assign({}, rnsVendorRemarkComment);
        return copy;
    }
}
