import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { Auction } from './auction.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<Auction>;

@Injectable()
export class AuctionService {
    private resourceUrl = SERVER_API_URL + 'api/auctions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(auction: Auction): Observable<EntityResponseType> {
        const copy = this.convert(auction);
        return this.http
            .post<Auction>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(auction: Auction): Observable<EntityResponseType> {
        const copy = this.convert(auction);
        return this.http
            .put<Auction>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<Auction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Auction[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<Auction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Auction[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Auction = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<Auction[]>): HttpResponse<Auction[]> {
        const jsonResponse: Auction[] = res.body;
        const body: Auction[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Auction.
     */
    private convertItemFromServer(auction: Auction): Auction {
        const copy: Auction = Object.assign({}, auction);

        copy.publishTime = this.dateUtils.convertDateTimeFromServer(auction.publishTime);
        copy.biddingStartTime = this.dateUtils.convertDateTimeFromServer(auction.biddingStartTime);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(auction: Auction): Auction {
        const copy: Auction = Object.assign({}, auction);

        // copy.publishTime = this.dateUtils.toDate(auction.publishTime);
        // copy.biddingStartTime = this.dateUtils.toDate(auction.biddingStartTime);
        copy.publishTime = auction.publishTime != null ? moment(auction.publishTime) : null;
        copy.biddingStartTime = auction.biddingStartTime != null ? moment(auction.biddingStartTime) : null;
        return copy;
    }
}
