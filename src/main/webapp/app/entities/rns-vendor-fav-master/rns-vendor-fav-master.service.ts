import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsVendorFavMaster } from './rns-vendor-fav-master.model';
import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<RnsVendorFavMaster>;

@Injectable()
export class RnsVendorFavMasterService {
    private resourceUrl = SERVER_API_URL + 'api/rns-vendor-fav-masters';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsVendorFavMaster: RnsVendorFavMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorFavMaster);
        return this.http
            .post<RnsVendorFavMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsVendorFavMaster: RnsVendorFavMaster): Observable<EntityResponseType> {
        const copy = this.convert(rnsVendorFavMaster);
        return this.http
            .put<RnsVendorFavMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsVendorFavMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsVendorFavMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsVendorFavMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsVendorFavMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsVendorFavMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsVendorFavMaster[]>): HttpResponse<RnsVendorFavMaster[]> {
        const jsonResponse: RnsVendorFavMaster[] = res.body;
        const body: RnsVendorFavMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsVendorFavMaster.
     */
    private convertItemFromServer(rnsVendorFavMaster: RnsVendorFavMaster): RnsVendorFavMaster {
        const copy: RnsVendorFavMaster = Object.assign({}, rnsVendorFavMaster);

        copy.createdDate = this.dateUtils.convertLocalDateFromServer(rnsVendorFavMaster.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsVendorFavMaster: RnsVendorFavMaster): RnsVendorFavMaster {
        const copy: RnsVendorFavMaster = Object.assign({}, rnsVendorFavMaster);
        return copy;
    }
}
