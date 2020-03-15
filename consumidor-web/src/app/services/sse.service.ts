import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Subject } from 'rxjs';

import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class SseService {

  private _urlApi = `${environment.urlApi}/api/venda`;

  private _userId: any;

  private _source: any;

  listenerObserver: Subject<any> = new Subject<any>();

  constructor(private _http: HttpClient) {
    this._userId = localStorage.getItem('userID');
  }

  connect() {
    if (!this._userId) {
      return;
    }
    this._source = new EventSourcePolyfill(`${this._urlApi}/venda/subscribe/${this._userId}`, {
      headers: {
        'connection': 'keep-alive', // Isso diz ao navegador para não fechar a conexão.
        'Authorization': 'Bearer '
      },
      heartbeatTimeout: 500000 // 5 minutes else 45 seconds by default, it initiates another request
    });

    this._source.onopen = () => console.log('open SSE conection');

    this._source.onerror = e => {
      if (e.readyState === EventSource.CLOSED) {
        console.log('close');
        this.reconect();
      } else {
        console.log('ERRO:', e);
        this._source.close();
        this.reconect();
      }
    };

    this._source.addEventListener('message', message => {
      if (message && message.data) {
        this.listenerObserver.next(JSON.parse(message.data));
      }
    });

  }

  private reconect() {
    console.log('Reconectando...');
    setTimeout(() => this.connect(), 15000);
  }

  closeConnection() {
    this._http.delete(`${this._urlApi}/venda/unsubscribe/${this._userId}`)
      .subscribe(() => this.closeSource());
  }

  private closeSource() {
    if (this._source) {
      this._source.close();
      console.log('CONEXÃO FEIXADA');
    }
  }
}
