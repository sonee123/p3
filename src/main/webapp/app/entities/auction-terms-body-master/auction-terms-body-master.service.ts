import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from 'app/app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AuctionTermsBodyMaster } from './auction-terms-body-master.model';

import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<AuctionTermsBodyMaster>;

@Injectable()
export class AuctionTermsBodyMasterService {
    private resourceUrl = SERVER_API_URL + 'api/auction-terms-body-masters';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(auctionTermsBodyMaster: AuctionTermsBodyMaster): Observable<EntityResponseType> {
        const copy = this.convert(auctionTermsBodyMaster);
        return this.http
            .post<AuctionTermsBodyMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(auctionTermsBodyMaster: AuctionTermsBodyMaster): Observable<EntityResponseType> {
        const copy = this.convert(auctionTermsBodyMaster);
        return this.http
            .put<AuctionTermsBodyMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionTermsBodyMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AuctionTermsBodyMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<AuctionTermsBodyMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AuctionTermsBodyMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AuctionTermsBodyMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<AuctionTermsBodyMaster[]>): HttpResponse<AuctionTermsBodyMaster[]> {
        const jsonResponse: AuctionTermsBodyMaster[] = res.body;
        const body: AuctionTermsBodyMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to AuctionTermsBodyMaster.
     */
    private convertItemFromServer(auctionTermsBodyMaster: AuctionTermsBodyMaster): AuctionTermsBodyMaster {
        const copy: AuctionTermsBodyMaster = Object.assign({}, auctionTermsBodyMaster);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(auctionTermsBodyMaster.createdDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(auctionTermsBodyMaster: AuctionTermsBodyMaster): AuctionTermsBodyMaster {
        const copy: AuctionTermsBodyMaster = Object.assign({}, auctionTermsBodyMaster);

        // copy.createdDate = this.dateUtils.toDate(auctionTermsBodyMaster.createdDate);
        copy.createdDate = auctionTermsBodyMaster.createdDate != null ? moment(auctionTermsBodyMaster.createdDate) : null;
        return copy;
    }
}
