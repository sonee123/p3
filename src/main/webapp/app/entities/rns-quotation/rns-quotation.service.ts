import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from 'app/app.constants';
import { JhiDateUtils } from 'ng-jhipster';
import 'rxjs/add/operator/toPromise';
import { RnsQuotation, BiddingCompareModel, RnsQuotationSearch } from 'app/entities/rns-quotation';
import { createRequestOption } from 'app/shared';
import { Dashboard } from 'app/rnsmain/dashboard.model';
import { RnsQuotationVariant } from 'app/entities/rns-quotation-variant';
import { AuctionPauseDetails } from 'app/entities/auction-pause-details';
import { DatePipe } from '@angular/common';
import * as moment from 'moment';
import { IRnsFileUpload } from 'app/entities/rns-file-upload';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<RnsQuotation>;

@Injectable()
export class RnsQuotationService {
    private resourceUrl = SERVER_API_URL + 'api/rns-quotations';
    private resourceUrlRFQ = SERVER_API_URL + 'api/rns-quotations-rfq';
    private resourceUrlAuction = SERVER_API_URL + 'api/rns-quotations-auction';
    private resourceUrlAuctionDetails = SERVER_API_URL + 'api/rns-auction-quotation-details';
    private resourceUrlAuctionVariants = SERVER_API_URL + 'api/rns-auction-quotation-variant-details';
    private resourceUrlcurrentDateTime = SERVER_API_URL + 'api/current-time';
    private resourceUrlDashboard = SERVER_API_URL + 'api/dashboard';
    private resourceUrlExport = SERVER_API_URL + 'api/export-rns-quotations-variants';
    private resourceUrlImport = SERVER_API_URL + 'api/import-rns-quotations-variants';

    private resourceUrlExportRfq = SERVER_API_URL + 'api/export-rns-quotations-rfq-price';
    private resourceUrlImportRfq = SERVER_API_URL + 'api/import-rns-quotations-rfq-price';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils, private datepipe: DatePipe) {}

    create(rnsQuotation: RnsQuotation): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotation);
        return this.http
            .post<RnsQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rnsQuotation: RnsQuotation): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotation);
        return this.http
            .put<RnsQuotation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RnsQuotation[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    // Custom methods

    agree(id: number): Observable<HttpResponse<any>> {
        return this.http.get<any>(`${this.resourceUrl}-agree/${id}`, { observe: 'response' });
    }

    auction(rnsQuotation: RnsQuotation): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotation);
        return this.http
            .put<RnsQuotation>(this.resourceUrlAuction, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    compare(id: number): Observable<HttpResponse<BiddingCompareModel>> {
        return this.http.get<BiddingCompareModel>(`${this.resourceUrl}-bidding-compare/${id}`, { observe: 'response' });
    }

    dashboard(dashboard: Dashboard): Observable<HttpResponse<Dashboard>> {
        const copy = dashboard;
        return this.http.post<Dashboard>(this.resourceUrlDashboard, copy, { observe: 'response' });
    }

    dashboardMessage(dashboard: Dashboard): Observable<HttpResponse<Dashboard>> {
        const copy = dashboard;
        return this.http.post<Dashboard>(this.resourceUrlDashboard + '-messages', copy, { observe: 'response' });
    }

    dashboardChart(dashboard: Dashboard): Observable<HttpResponse<Dashboard>> {
        const copy = dashboard;
        return this.http.post<Dashboard>(this.resourceUrlDashboard + '/chart', copy, { observe: 'response' });
    }

    dashboardLot(dashboard: Dashboard): Observable<HttpResponse<Dashboard>> {
        const copy = dashboard;
        return this.http.post<Dashboard>(this.resourceUrlDashboard + '/lot', copy, { observe: 'response' });
    }

    disagree(id: number): Observable<HttpResponse<any>> {
        return this.http.get<any>(`${this.resourceUrl}-disagree/${id}`, { observe: 'response' });
    }

    downloadPdf(id: number): Observable<Blob> {
        return this.http
            .get(`${this.resourceUrl}-details/pdf/${id}`, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    findAuctionDetails(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotation>(`${this.resourceUrlAuctionDetails}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findAuctionDetailVariants(id: number): Observable<HttpResponse<RnsQuotationVariant[]>> {
        return this.http.get<RnsQuotationVariant[]>(`${this.resourceUrlAuctionVariants}/${id}`, { observe: 'response' });
    }

    findFullAuctionDetails(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotation>(`${this.resourceUrlAuctionDetails}/full/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findrfq(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotation>(`${this.resourceUrl}/getRfqDetailsById/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    getCurrentTime(): Observable<HttpResponse<Date>> {
        return this.http.get<Date>(`${this.resourceUrlcurrentDateTime}`, { observe: 'response' });
    }

    getCurrentTimewithPaused(id: number): Observable<HttpResponse<AuctionPauseDetails>> {
        return this.http.get<AuctionPauseDetails>(`${this.resourceUrlcurrentDateTime}/${id}`, { observe: 'response' });
    }

    publish(publishData: any): Observable<EntityResponseType> {
        const copy = publishData;
        return this.http
            .get<RnsQuotation>(this.resourceUrl + '/publish', { params: copy, observe: 'response' })
            .map((res: HttpResponse<RnsQuotation>) => this.convertResponse(res));
    }

    queryAuctionVendor(req?: any): Observable<HttpResponse<RnsQuotation[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotation[]>(`${this.resourceUrl}-auction-vendor-quotations`, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotation[]>) => this.convertArrayResponse(res));
    }

    querysearch(rnsQuotationSearch: RnsQuotationSearch): Observable<HttpResponse<RnsQuotation[]>> {
        if (rnsQuotationSearch.dateFrom != null) {
            rnsQuotationSearch.dateFrom = this.dateUtils.convertLocalDateToServer(rnsQuotationSearch.dateFrom, 'yyyy-MM-dd');
        }
        if (rnsQuotationSearch.dateTo != null) {
            rnsQuotationSearch.dateTo = this.dateUtils.convertLocalDateToServer(rnsQuotationSearch.dateTo, 'yyyy-MM-dd');
        }
        const copy = this.convertSearch(rnsQuotationSearch);

        return this.http
            .post<RnsQuotation[]>(this.resourceUrl + '-custom-query', copy, { observe: 'response' })
            .map((res: HttpResponse<RnsQuotation[]>) => this.convertArrayResponse(res));
    }

    queryVendor(req?: any): Observable<HttpResponse<RnsQuotation[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotation[]>(`${this.resourceUrl}-rns-vendor-quotations`, { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotation[]>) => this.convertArrayResponse(res));
    }

    queryVm(req?: any): Observable<HttpResponse<RnsQuotation[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<RnsQuotation[]>(this.resourceUrl + '-custom', { params: options, observe: 'response' })
            .map((res: HttpResponse<RnsQuotation[]>) => this.convertArrayResponse(res));
    }

    report(id: number): Observable<HttpResponse<any>> {
        return this.http.get<any>(`${this.resourceUrl}-details/pdf/${id}`, { observe: 'response' });
    }

    rfq(rnsQuotation: RnsQuotation): Observable<EntityResponseType> {
        const copy = this.convert(rnsQuotation);
        return this.http
            .put<RnsQuotation>(this.resourceUrlRFQ, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    rfqDetails(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotation>(`${this.resourceUrl}/getRfqDetailsById/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    downloadreport(rnsQuotationSearch: RnsQuotationSearch): any {
        if (rnsQuotationSearch.dateFrom != null) {
            rnsQuotationSearch.dateFrom = this.dateUtils.convertLocalDateToServer(rnsQuotationSearch.dateFrom, 'yyyy-MM-dd');
        }
        if (rnsQuotationSearch.dateTo != null) {
            rnsQuotationSearch.dateTo = this.dateUtils.convertLocalDateToServer(rnsQuotationSearch.dateTo, 'yyyy-MM-dd');
        }
        const copy = this.convertSearch(rnsQuotationSearch);

        return this.http
            .post(`${this.resourceUrl}-detail-reports`, copy, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    export(id: number): any {
        return this.http
            .get(`${this.resourceUrlExport}/${id}`, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    exportRfq(id: number): any {
        return this.http
            .get(`${this.resourceUrlExportRfq}/${id}`, { headers: new HttpHeaders({}), responseType: 'blob' })
            .map((response: Blob) => <Blob>response);
    }

    import(rnsFileUpload: IRnsFileUpload, file: File): Observable<HttpResponse<any>> {
        const formdata: FormData = new FormData();
        formdata.append('file', file);
        formdata.append('id', rnsFileUpload.id + '');
        return this.http.post(this.resourceUrlImport, formdata, { observe: 'response' });
    }

    importRfq(rnsFileUpload: IRnsFileUpload, file: File): Observable<HttpResponse<any>> {
        const formdata: FormData = new FormData();
        formdata.append('file', file);
        formdata.append('id', rnsFileUpload.id + '');
        return this.http.post(this.resourceUrlImportRfq, formdata, { observe: 'response' });
    }

    closed(id: number): Observable<EntityResponseType> {
        return this.http
            .get<RnsQuotation>(`${this.resourceUrl}-closed/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RnsQuotation = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    private convertArrayResponse(res: HttpResponse<RnsQuotation[]>): HttpResponse<RnsQuotation[]> {
        const jsonResponse: RnsQuotation[] = res.body;
        const body: RnsQuotation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to RnsQuotation.
     */
    private convertItemFromServer(rnsQuotation: RnsQuotation): RnsQuotation {
        const copy: RnsQuotation = Object.assign({}, rnsQuotation);

        copy.validity = this.dateUtils.convertDateTimeFromServer(rnsQuotation.validity);
        copy.auctionValidity = this.dateUtils.convertDateTimeFromServer(rnsQuotation.auctionValidity);
        copy.createdOn = this.dateUtils.convertDateTimeFromServer(rnsQuotation.createdOn);
        copy.date = this.dateUtils.convertDateTimeFromServer(rnsQuotation.date);
        copy.approvedDate = this.dateUtils.convertDateTimeFromServer(rnsQuotation.approvedDate);
        if (rnsQuotation.targetPcd) {
            copy.targetPcd = this.dateUtils.convertLocalDateFromServer(rnsQuotation.targetPcd);
        }
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(rnsQuotation: RnsQuotation): RnsQuotation {
        const copy: RnsQuotation = Object.assign({}, rnsQuotation);
        // copy.validity = this.datepipe.transform(rnsQuotation.validity, 'yyyy-MM-ddTHH:mm:ss');
        // copy.createdOn = this.dateUtils.toDate(rnsQuotation.createdOn);
        // copy.date = this.dateUtils.toDate(rnsQuotation.date);
        copy.validity = rnsQuotation.validity != null ? moment(rnsQuotation.validity) : null;
        copy.createdOn = rnsQuotation.createdOn != null ? moment(rnsQuotation.createdOn) : null;
        copy.date = rnsQuotation.date != null ? moment(rnsQuotation.date) : null;
        copy.approvedDate = rnsQuotation.approvedDate != null ? moment(rnsQuotation.approvedDate) : null;
        if (rnsQuotation.targetPcd) {
            copy.targetPcd = this.dateUtils.convertLocalDateToServer(rnsQuotation.targetPcd);
        }
        return copy;
    }

    private convertSearch(rnsQuotationSearch: RnsQuotationSearch): RnsQuotationSearch {
        const copy: RnsQuotationSearch = Object.assign({}, rnsQuotationSearch);
        return copy;
    }
}
