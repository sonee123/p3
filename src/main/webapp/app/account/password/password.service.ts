import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { IUser, User } from 'app/core';

@Injectable({ providedIn: 'root' })
export class PasswordService {
    constructor(private http: HttpClient) {}

    save(newPassword: string, currentPassword: string): Observable<any> {
        return this.http.post(SERVER_API_URL + 'api/account/change-password', { currentPassword, newPassword });
    }

    savepassword(user: User): Observable<HttpResponse<IUser>> {
        const copy = this.convert(user);
        return this.http
            .post<User>(SERVER_API_URL + 'api/account/change-user-password', copy, { observe: 'response' })
            .map((res: HttpResponse<IUser>) => this.convertResponse(res));
    }

    private convertResponse(res: HttpResponse<User>): HttpResponse<IUser> {
        const body: IUser = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }

    /**
     * Convert a returned JSON object to Auction.
     */
    private convertItemFromServer(user: User): User {
        const copy: User = Object.assign({}, user);
        return copy;
    }

    /**
     * Convert a Entry to a JSON which can be sent to the server.
     */
    private convert(user: User): User {
        const copy: User = Object.assign({}, user);
        return copy;
    }
}
