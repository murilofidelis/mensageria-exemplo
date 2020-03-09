import { Component, OnInit, OnDestroy } from '@angular/core';

import { VendaService } from './services/venda.service';
import { SocketIoService } from './services/socket-io.service';
import { SseService } from './services/sse.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

  totalVendas = 0;

  constructor(
    private vendaService: VendaService,
    private sseService: SseService,
    private socketService: SocketIoService) { }

  ngOnInit(): void {
    this.buscaQuantidadedeVendas();
    //  this.ouveNotificacaoVenda();

    this.ouveNoticacaoSSE();
  }

  private buscaQuantidadedeVendas() {
    this.vendaService.getQuantiddeVendas()
      .subscribe(qtd => {
        if (qtd) {
          this.totalVendas = qtd;
        }
      });
  }

  private ouveNotificacaoVenda() {
    this.socketService.conecta();

    this.socketService.listenerObserver
      .subscribe(data => {
        if (data) {
          console.log(data);
          this.totalVendas = this.totalVendas + 1;
        }
      });
  }


  private ouveNoticacaoSSE() {
    this.sseService.connect();
    this.sseService.listenerObserver.subscribe((res: any[]) => {
      if (res && res.length > 0) {
        this.totalVendas = res.length;
      }
    });
  }

  close() {
    this.sseService.closeConnection();
  }

  ngOnDestroy(): void {
    this.sseService.closeConnection();
    this.socketService.desconecta();
  }


}
