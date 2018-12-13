import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { AuctionVarDetails } from './auction-var-details.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<AuctionVarDetails>;

@Injectable()
export class AuctionVarDetailsService {
    private resourceUrl = SERVER_API_URL + 'api/auction-var-details';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(auctionVarDetails: AuctionVarDetails): Observable<EntityResponseType> {
        const copy = this.convert(auctionVarDetails);
        return this.http
            .post<AuctionVarDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(auctionVarDetails: AuctionVarDetails): Observable<EntityResponseType> {
        const copy = this.convert(auctionVarDetails);
        return this.http
            .put<AuctionVarDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<HttpResponse<AuctionVarDetails[]>> {
        return this.http
            .get<AuctionVarDetails[]>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<AuctionVarDetails[]>) => this.convertArrayResponse(res));
    }

    findByVariantId(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionVarDetails>(`${this.resourceUrl}-by-variant/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AuctionVarDetails[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<AuctionVarDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AuctionVarDetails[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    post(auctionVarDetails: AuctionVarDetails, type?: string): Observable<EntityResponseType> {
        const copy = this.convert(auctionVarDetails, type);
        return this.http
            .post<AuctionVarDetails>(`${this.resourceUrl}-quotation`, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AuctionVarDetails = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<AuctionVarDetails[]>): HttpResponse<AuctionVarDetails[]> {
        const jsonResponse: AuctionVarDetails[] = res.body;
        const body: AuctionVarDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to AuctionVarDetails.
     */
    private convertItemFromServer(auctionVarDetails: AuctionVarDetails): AuctionVarDetails {
        const copy: AuctionVarDetails = Object.assign({}, auctionVarDetails);

        copy.lotStartTime = this.dateUtils.convertDateTimeFromServer(auctionVarDetails.lotStartTime);
        copy.lotEndTime = this.dateUtils.convertDateTimeFromServer(auctionVarDetails.lotEndTime);
        copy.createdDate = this.dateUtils.convertDateTimeFromServer(auctionVarDetails.createdDate);
        copy.updatedDate = this.dateUtils.convertDateTimeFromServer(auctionVarDetails.updatedDate);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(auctionVarDetails: AuctionVarDetails, type?: string): AuctionVarDetails {
        const copy: AuctionVarDetails = Object.assign({}, auctionVarDetails);

        // copy.lotStartTime = this.dateUtils.toDate(auctionVarDetails.lotStartTime);
        // copy.lotEndTime = this.dateUtils.toDate(auctionVarDetails.lotEndTime);
        // copy.createdDate = this.dateUtils.toDate(auctionVarDetails.createdDate);
        // copy.updatedDate = this.dateUtils.toDate(auctionVarDetails.updatedDate);

        copy.lotStartTime = auctionVarDetails.lotStartTime != null ? moment(auctionVarDetails.lotStartTime) : null;
        if (type !== null && type !== undefined && auctionVarDetails.lotMinutes !== null && auctionVarDetails.lotMinutes !== undefined) {
            copy.type = type;
            if (type === 'E') {
                copy.lotEndTime =
                    auctionVarDetails.lotEndTime != null
                        ? moment(auctionVarDetails.lotEndTime).add(auctionVarDetails.lotMinutes, 'minutes')
                        : null;
            } else if (type === 'R') {
                copy.lotEndTime =
                    auctionVarDetails.lotEndTime != null
                        ? moment(auctionVarDetails.lotEndTime).add(-auctionVarDetails.lotMinutes, 'minutes')
                        : null;
            }
        } else {
            copy.lotEndTime = auctionVarDetails.lotEndTime != null ? moment(auctionVarDetails.lotEndTime) : null;
        }
        copy.createdDate = auctionVarDetails.createdDate != null ? moment(auctionVarDetails.createdDate) : null;
        copy.updatedDate = auctionVarDetails.updatedDate != null ? moment(auctionVarDetails.updatedDate) : null;
        return copy;
    }
}
