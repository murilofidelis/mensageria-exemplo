import { Injectable } from '@angular/core';

import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';

import { environment } from 'src/environments/environment';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class SocketIoService {

  stompClient = null;

  listenerObserver: Subject<any> = new Subject<any>();

  unSubscribe = new Subject<void>();

  private urlApi = `${environment.urlApi}/api/venda`;

  constructor() { }

  conecta() {

    const url = `${this.urlApi}/websocket`;
    const socket = new SockJS(url);

    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, () => {
      setTimeout(() => {
        this.ouveEventos();

      }, 1500);

    },
      () => this.reconect()
    );

  }

  desconecta() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
      this.stompClient = null;
    }

  }


  private reconect() {
    console.log('STOMP: Attempting connection');
    setTimeout(() => this.conecta(), 3000);
  }

  private ouveEventos() {
    this.stompClient
      .subscribe(`/topic/venda`,
        data => {
          this.listenerObserver.next(JSON.parse(data.body));
        }
      );

  }
}
