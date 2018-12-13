import { Injectable } from '@angular/core';
import { Observable, Observer, Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { AuthServerProvider } from '../../core/auth/auth-jwt.service';
import { WindowRef } from '../../core/tracker/window.service';
import { CSRFService } from '../../core/auth/csrf.service';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import { AuctionPauseDetails } from './auction-pause-details.model';

@Injectable()
export class AuctionPausedSocketService {
    stompClient = null;
    subscriber = null;
    connection: Promise<any>;
    connectedPromise: any;
    listener: Observable<any>;
    listenerObserver: Observer<any>;
    alreadyConnectedOnce = false;
    private subscription: Subscription;

    constructor(
        private router: Router,
        private authServerProvider: AuthServerProvider,
        private $window: WindowRef,
        // tslint:disable-next-line: no-unused-variable
        private csrfService: CSRFService
    ) {
        this.connection = this.createConnection();
        this.listener = this.createListener();
    }

    connect() {
        if (this.connectedPromise === null) {
            this.connection = this.createConnection();
        }
        // building absolute path so that websocket doesn't fail when deploying with a context path
        const loc = this.$window.nativeWindow.location;
        const host = loc.host.replace('9000', '8080');
        let url = '//' + host + loc.pathname + 'websocket';
        const authToken = this.authServerProvider.getToken();
        if (authToken) {
            url += '?access_token=' + authToken;
        }
        const socket = new SockJS(url);
        this.stompClient = Stomp.over(socket);
        const headers = {};
        console.log('url: ', url, authToken);
        this.stompClient.connect(headers, () => {
            console.log('success');
            // this.sendActivity();
            this.connectedPromise('success');
            this.connectedPromise = null;
            if (!this.alreadyConnectedOnce) {
                this.subscription = this.router.events.subscribe(event => {
                    // if (event instanceof NavigationEnd) {
                    //   this.sendActivity();
                    // }
                });
                this.alreadyConnectedOnce = true;
            }
        });
    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
            this.stompClient = null;
        }
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        this.alreadyConnectedOnce = false;
    }

    receive() {
        return this.listener;
    }

    sendActivity(auctionPauseDetails: AuctionPauseDetails, quotationId: number) {
        const copy: AuctionPauseDetails = Object.assign({}, auctionPauseDetails);
        const url = '/auction-paused-ws/' + quotationId;
        if (this.stompClient !== null && this.stompClient.connected) {
            this.stompClient.send(
                url, // destination
                JSON.stringify(copy), // body
                {} // header
            );
        }
    }

    sendReviseActivity(auctionPauseDetails: AuctionPauseDetails, quotationId: number) {
        const copy: AuctionPauseDetails = Object.assign({}, auctionPauseDetails);
        const url = '/auction-revised-ws/' + quotationId;
        if (this.stompClient !== null && this.stompClient.connected) {
            this.stompClient.send(
                url, // destination
                JSON.stringify(copy), // body
                {} // header
            );
        }
    }

    subscribe(quotationId: number) {
        this.connection.then(() => {
            this.subscriber = this.stompClient.subscribe('/topic/auction-paused-ws/' + quotationId, data => {
                this.listenerObserver.next(JSON.parse(data.body));
            });
        });
    }

    unsubscribe() {
        if (this.subscriber !== null) {
            this.subscriber.unsubscribe();
        }
        this.listener = this.createListener();
    }

    private createListener(): Observable<any> {
        return new Observable(observer => {
            this.listenerObserver = observer;
        });
    }

    private createConnection(): Promise<any> {
        return new Promise((resolve, reject) => {
            this.connect();
            this.connectedPromise = resolve;
        });
    }
}
