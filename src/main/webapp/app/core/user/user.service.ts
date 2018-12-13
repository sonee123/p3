import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUser, User } from './user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
    private resourceUrl = SERVER_API_URL + 'api/users';
    private resourceUrlCurrent = SERVER_API_URL + 'api/current-users';

    constructor(private http: HttpClient) {}

    create(user: IUser): Observable<HttpResponse<IUser>> {
        return this.http.post<IUser>(this.resourceUrl, user, { observe: 'response' });
    }

    update(user: IUser): Observable<HttpResponse<IUser>> {
        return this.http.put<IUser>(this.resourceUrl, user, { observe: 'response' });
    }

    find(login: string): Observable<HttpResponse<IUser>> {
        return this.http.get<IUser>(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    query(req?: any): Observable<HttpResponse<IUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(login: string): Observable<HttpResponse<any>> {
        return this.http.delete(`${this.resourceUrl}/${login}`, { observe: 'response' });
    }

    authorities(): Observable<string[]> {
        return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities');
    }

    currentuser(req?: any): Observable<HttpResponse<IUser>> {
        return this.http.get<IUser>(this.resourceUrlCurrent, { observe: 'response' });
    }

    findpost(login: string): Observable<HttpResponse<IUser>> {
        const user = new User();
        user.login = login;
        return this.http.post(this.resourceUrl + '-email', user, { observe: 'response' });
    }

    vendorquery(req?: any): Observable<HttpResponse<IUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<IUser[]>(this.resourceUrl + '-vendor-all', { params: options, observe: 'response' });
    }

    queryvendor(search?: string, req?: any): Observable<HttpResponse<IUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<IUser[]>(this.resourceUrl + '-vendor/' + search, { params: options, observe: 'response' });
    }

    queryvendorfav(req?: any): Observable<HttpResponse<IUser[]>> {
        const options = createRequestOption(req);
        return this.http.get<IUser[]>(this.resourceUrl + '-vendor-favourite', { params: options, observe: 'response' });
    }

    deletevendor(login: string): Observable<HttpResponse<any>> {
        return this.http.delete(`${this.resourceUrl + '-vendor-delete'}/${login}`, { observe: 'response' });
    }

    authoritiesByType(type: string, allow: boolean): Observable<string[]> {
        return this.http.get<string[]>(SERVER_API_URL + 'api/users/authorities').map((res: String[]) => {
            const json = [];
            for (const jsonKey in res) {
                if (allow) {
                    if (type !== res[jsonKey]) {
                        json.push(res[jsonKey]);
                    }
                } else {
                    if (type === res[jsonKey]) {
                        json.push(res[jsonKey]);
                    }
                }
            }
            return <string[]>json;
        });
    }

    queryUser(search?: string, req?: any): Observable<HttpResponse<IUser[]>> {
        const user = new User();
        user.firstName = search;
        return this.http.post<IUser[]>(this.resourceUrl + '-search-users', user, { observe: 'response' });
    }
}
