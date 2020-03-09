import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Subject } from 'rxjs';

import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';

@Injectable({ providedIn: 'root' })
export class SseService {

  private urlApi = `${environment.urlApi}/api/venda`;

  source: any;

  listenerObserver: Subject<any> = new Subject<any>();

  constructor() { }

  connect() {
    this.source = new EventSourcePolyfill(`${this.urlApi}/venda/subscribe`, {
      headers: {
        'connection': 'keep-alive', // Isso diz ao navegador para não fechar a conexão.
        'Authorization': 'my jwt token'
      },
      heartbeatTimeout: 500000 // 5 minutes else 45 seconds by default, it initiates another request
    });

    this.source.onopen = () => console.log('open');

    this.source.onerror = e => {
      if (e.readyState === EventSource.CLOSED) {
        console.log('close');
        this.connect();
      } else {
        console.log('ERRO:', e);
        this.source.close();
        this.connect();
      }
    };

    this.source.addEventListener('message', message => {
      if (message && message.data) {
        console.log('message', message);
        this.listenerObserver.next(JSON.parse(message.data));
      }
    });

  }

  closeConnection() {
    if (this.source) {
      this.source.close();
      console.log('CONEXÃO FEIXADA');
    }
  }

}
