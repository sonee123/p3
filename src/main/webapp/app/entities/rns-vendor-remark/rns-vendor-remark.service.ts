import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsVendorRemark } from './rns-vendor-remark.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsVendorRemark>;

@Injectable()
export class RnsVendorRemarkService {
    private resourceUrl = SERVER_API_URL + 'api/rns-vendor-remarks';

    constructor(private http: HttpClient) {}

    create(rnsVendorRemark: RnsVendorRemark): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorRemark);
        return this.http
            .post<RnsVendorRemark>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsVendorRemark: RnsVendorRemark): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorRemark);
        return this.http
            .put<RnsVendorRemark>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsVendorRemark>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsVendorRemark[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorRemark[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsVendorRemark[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    queryByEmail(email: string, id: number, req?: any): Observable<HttpResponse<RnsVendorRemark[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorRemark[]>(this.resourceUrl + '?vendorEmail.equals=' + email + '&=quotation.equals=' + id + '&sort=id,desc', {
                params: options,
                observe: 'response'
            })
            .map((res: HttpResponse<RnsVendorRemark[]>) => this.convertArrayResponse(res));
    }

    queryByEmailQuoteId(email: string, id: number, req?: any): Observable<HttpResponse<RnsVendorRemark[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorRemark[]>(this.resourceUrl + '-quotationid/' + id, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsVendorRemark[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsVendorRemark = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsVendorRemark[]>): HttpResponse<RnsVendorRemark[]> {
        const jsonResponse: RnsVendorRemark[] = res.body;
        const body: RnsVendorRemark[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsVendorRemark.
     */
    private convertItemFromServer(rnsVendorRemark: RnsVendorRemark): RnsVendorRemark {
        const copy: RnsVendorRemark = Object.assign({}, rnsVendorRemark);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsVendorRemark: RnsVendorRemark): RnsVendorRemark {
        const copy: RnsVendorRemark = Object.assign({}, rnsVendorRemark);
        return copy;
    }
}
