import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { RnsVendorMaster } from './rns-vendor-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsVendorMaster>;

@Injectable()
export class RnsVendorMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-vendor-masters';

    constructor(private http: HttpClient) {}

    create(rnsVendorMaster: RnsVendorMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorMaster);
        return this.http
            .post<RnsVendorMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsVendorMaster: RnsVendorMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorMaster);
        return this.http
            .put<RnsVendorMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsVendorMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsVendorMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsVendorMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    queryvendor(search?: string, req?: any): Observable<HttpResponse<RnsVendorMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorMaster[]>(this.resourceUrl + '-vendor/' + search, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsVendorMaster[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsVendorMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsVendorMaster[]>): HttpResponse<RnsVendorMaster[]> {
        const jsonResponse: RnsVendorMaster[] = res.body;
        const body: RnsVendorMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsVendorMaster.
     */
    private convertItemFromServer(rnsVendorMaster: RnsVendorMaster): RnsVendorMaster {
        const copy: RnsVendorMaster = Object.assign({}, rnsVendorMaster);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsVendorMaster: RnsVendorMaster): RnsVendorMaster {
        const copy: RnsVendorMaster = Object.assign({}, rnsVendorMaster);
        return copy;
    }
}
