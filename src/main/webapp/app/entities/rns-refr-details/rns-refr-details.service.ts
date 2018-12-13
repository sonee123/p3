import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import { RnsRefrDetails } from './rns-refr-details.model';
import { createRequestOption } from 'app/shared';
import * as moment from 'moment';

type EntityResponseType = HttpResponse<RnsRefrDetails>;

@Injectable()
export class RnsRefrDetailsService {
    private resourceUrl = SERVER_API_URL + 'api/rns-refr-details';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) {}

    create(rnsRefrDetails: RnsRefrDetails): Observable<EntityResponseType> {
        const copy = this.convert(rnsRefrDetails);
        return this.http
            .post<RnsRefrDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsRefrDetails: RnsRefrDetails): Observable<EntityResponseType> {
        const copy = this.convert(rnsRefrDetails);
        return this.http
            .put<RnsRefrDetails>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsRefrDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsRefrDetails[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsRefrDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsRefrDetails[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsRefrDetails = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsRefrDetails[]>): HttpResponse<RnsRefrDetails[]> {
        const jsonResponse: RnsRefrDetails[] = res.body;
        const body: RnsRefrDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsRefrDetails.
     */
    private convertItemFromServer(rnsRefrDetails: RnsRefrDetails): RnsRefrDetails {
        const copy: RnsRefrDetails = Object.assign({}, rnsRefrDetails);

        copy.createdDate = this.dateUtils.convertDateTimeFromServer(rnsRefrDetails.createdDate);
        copy.lastModifiedBy = this.dateUtils.convertDateTimeFromServer(rnsRefrDetails.lastModifiedBy);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsRefrDetails: RnsRefrDetails): RnsRefrDetails {
        const copy: RnsRefrDetails = Object.assign({}, rnsRefrDetails);

        copy.createdDate = rnsRefrDetails.createdDate != null ? moment(rnsRefrDetails.createdDate) : null;
        copy.lastModifiedBy = rnsRefrDetails.lastModifiedBy != null ? moment(rnsRefrDetails.lastModifiedBy) : null;
        return copy;
    }
}
