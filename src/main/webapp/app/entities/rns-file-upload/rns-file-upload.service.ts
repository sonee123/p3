import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map, tap } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRnsFileUpload, RnsFileUpload } from './rns-file-upload.model';
import { RnsUploadBean } from './rns-upload-bean.model';

type EntityResponseType = HttpResponse<IRnsFileUpload>;
type EntityArrayResponseType = HttpResponse<IRnsFileUpload[]>;

@Injectable()
export class RnsFileUploadService {
    private resourceUrl = SERVER_API_URL + 'api/rns-file-uploads';

    constructor(private http: HttpClient) {}

    create(rnsFileUpload: IRnsFileUpload, file: File): Observable<EntityArrayResponseType> {
        const formdata: FormData = new FormData();
        formdata.append('file', file);
        formdata.append('variantId', rnsFileUpload.variantId + '');
        formdata.append('uploadType', rnsFileUpload.uploadType);
        return this.http
            .post(this.resourceUrl, formdata, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    update(rnsFileUpload: IRnsFileUpload): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rnsFileUpload);
        return this.http
            .put<IRnsFileUpload>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRnsFileUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findbyType(id: number, type: string): Observable<EntityArrayResponseType> {
        const rnsFileUpload = new RnsUploadBean();
        rnsFileUpload.variantId = id;
        rnsFileUpload.uploadType = type;
        return this.http
            .post(`${this.resourceUrl}-list`, rnsFileUpload, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRnsFileUpload[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    download(id: number): Observable<Blob> {
        return this.http
            .get(`${this.resourceUrl}-download/${id}`, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    private convertDateFromClient(rnsFileUpload: IRnsFileUpload): IRnsFileUpload {
        const copy: IRnsFileUpload = Object.assign({}, rnsFileUpload, {
            createdDate:
                rnsFileUpload.createdDate != null && rnsFileUpload.createdDate.isValid() ? rnsFileUpload.createdDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((rnsFileUpload: IRnsFileUpload) => {
            rnsFileUpload.createdDate = rnsFileUpload.createdDate != null ? moment(rnsFileUpload.createdDate) : null;
        });
        return res;
    }
}
