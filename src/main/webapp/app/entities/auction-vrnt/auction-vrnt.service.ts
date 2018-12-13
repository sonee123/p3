import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from 'app/app.constants';

import { AuctionVrnt } from './auction-vrnt.model';

import { createRequestOption } from 'app/shared';

type EntityResponseType = HttpResponse<AuctionVrnt>;

@Injectable()
export class AuctionVrntService {
    private resourceUrl = SERVER_API_URL + 'api/auction-vrnts';

    constructor(private http: HttpClient) {}

    create(auctionVrnt: AuctionVrnt): Observable<EntityResponseType> {
        const copy = this.convert(auctionVrnt);
        return this.http
            .post<AuctionVrnt>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(auctionVrnt: AuctionVrnt): Observable<EntityResponseType> {
        const copy = this.convert(auctionVrnt);
        return this.http
            .put<AuctionVrnt>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionVrnt>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AuctionVrnt[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<AuctionVrnt[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AuctionVrnt[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findByVariantId(id: number): Observable<EntityResponseType> {
        return this.http
            .get<AuctionVrnt>(`${this.resourceUrl}/get-auction-by-variant/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AuctionVrnt = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<AuctionVrnt[]>): HttpResponse<AuctionVrnt[]> {
        const jsonResponse: AuctionVrnt[] = res.body;
        const body: AuctionVrnt[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to AuctionVrnt.
     */
    private convertItemFromServer(auctionVrnt: AuctionVrnt): AuctionVrnt {
        const copy: AuctionVrnt = Object.assign({}, auctionVrnt);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(auctionVrnt: AuctionVrnt): AuctionVrnt {
        const copy: AuctionVrnt = Object.assign({}, auctionVrnt);
        return copy;
    }
}
