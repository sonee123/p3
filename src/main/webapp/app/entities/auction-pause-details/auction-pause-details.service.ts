import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from 'app/app.constants';

import { JhiDateUtils } from 'ng-jhipster';
import { AuctionPauseDetails } from './auction-pause-details.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<AuctionPauseDetails>;

@Injectable()
export class AuctionPauseDetailsService {
    private resourceUrl = SERVER_API_URL + 'api/auction-pause-details';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(auctionPauseDetails: AuctionPauseDetails): Observable<EntityResponseType> {
        const copy = this.convert(auctionPauseDetails);
        return this.http
            .post<AuctionPauseDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(auctionPauseDetails: AuctionPauseDetails): Observable<EntityResponseType> {
        const copy = this.convert(auctionPauseDetails);
        return this.http
            .put<AuctionPauseDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionPauseDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AuctionPauseDetails[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<AuctionPauseDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AuctionPauseDetails[]>) => this.convertArrayResponse(res));
    }

    queryQuote(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionPauseDetails>(`${this.resourceUrl}-quotation/${id}`, { observe: 'response' })
            .map((res: HttpResponse<AuctionPauseDetails>) => this.convertResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AuctionPauseDetails = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<AuctionPauseDetails[]>): HttpResponse<AuctionPauseDetails[]> {
        const jsonResponse: AuctionPauseDetails[] = res.body;
        const body: AuctionPauseDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to AuctionPauseDetails.
     */
    private convertItemFromServer(auctionPauseDetails: AuctionPauseDetails): AuctionPauseDetails {
        const copy: AuctionPauseDetails = Object.assign({}, auctionPauseDetails);

        copy.pauseStartDate = this.dateUtils.convertDateTimeFromServer(auctionPauseDetails.pauseStartDate);
        copy.pauseEndDate = this.dateUtils.convertDateTimeFromServer(auctionPauseDetails.pauseEndDate);
        copy.createdDate = this.dateUtils.convertDateTimeFromServer(auctionPauseDetails.createdDate);
        copy.updatedDate = this.dateUtils.convertDateTimeFromServer(auctionPauseDetails.updatedDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(auctionPauseDetails: AuctionPauseDetails): AuctionPauseDetails {
        const copy: AuctionPauseDetails = Object.assign({}, auctionPauseDetails);

        copy.pauseStartDate = auctionPauseDetails.pauseStartDate != null ? moment(auctionPauseDetails.pauseStartDate) : null;
        copy.pauseEndDate = auctionPauseDetails.pauseEndDate != null ? moment(auctionPauseDetails.pauseEndDate) : null;
        copy.createdDate = auctionPauseDetails.createdDate != null ? moment(auctionPauseDetails.createdDate) : null;
        copy.updatedDate = auctionPauseDetails.updatedDate != null ? moment(auctionPauseDetails.updatedDate) : null;
        // copy.pauseStartDate = this.dateUtils.toDate(auctionPauseDetails.pauseStartDate);
        // copy.pauseEndDate = this.dateUtils.toDate(auctionPauseDetails.pauseEndDate);
        // copy.createdDate = this.dateUtils.toDate(auctionPauseDetails.createdDate);
        // copy.updatedDate = this.dateUtils.toDate(auctionPauseDetails.updatedDate);
        return copy;
    }
}
