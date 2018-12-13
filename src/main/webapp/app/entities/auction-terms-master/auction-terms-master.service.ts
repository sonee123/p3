import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { AuctionTermsMaster } from './auction-terms-master.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<AuctionTermsMaster>;

@Injectable()
export class AuctionTermsMasterService {
    private resourceUrl = SERVER_API_URL + 'api/auction-terms-masters';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(auctionTermsMaster: AuctionTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(auctionTermsMaster);
        return this.http
            .post<AuctionTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(auctionTermsMaster: AuctionTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(auctionTermsMaster);
        return this.http
            .put<AuctionTermsMaster>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionTermsMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AuctionTermsMaster[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<AuctionTermsMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AuctionTermsMaster[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    get(auctionTermsMaster: AuctionTermsMaster): Observable<EntityResponseType> {
        const copy = this.convert(auctionTermsMaster);
        return this.http
            .post<AuctionTermsMaster>(this.resourceUrl + '-agree', copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AuctionTermsMaster = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<AuctionTermsMaster[]>): HttpResponse<AuctionTermsMaster[]> {
        const jsonResponse: AuctionTermsMaster[] = res.body;
        const body: AuctionTermsMaster[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to AuctionTermsMaster.
     */
    private convertItemFromServer(auctionTermsMaster: AuctionTermsMaster): AuctionTermsMaster {
        const copy: AuctionTermsMaster = Object.assign({}, auctionTermsMaster);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(auctionTermsMaster.createdDate);
        copy.lastUpdatedDate = this.dateUtils.convertDateTimeFromServer(auctionTermsMaster.lastUpdatedDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(auctionTermsMaster: AuctionTermsMaster): AuctionTermsMaster {
        const copy: AuctionTermsMaster = Object.assign({}, auctionTermsMaster);

        // copy.createdDate = this.dateUtils.toDate(auctionTermsMaster.createdDate);
        // copy.lastUpdatedDate = this.dateUtils.toDate(auctionTermsMaster.lastUpdatedDate);
        copy.createdDate = auctionTermsMaster.createdDate != null ? moment(auctionTermsMaster.createdDate) : null;
        copy.lastUpdatedDate = auctionTermsMaster.lastUpdatedDate != null ? moment(auctionTermsMaster.lastUpdatedDate) : null;
        return copy;
    }
}
