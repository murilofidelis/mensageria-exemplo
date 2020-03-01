import { Component, OnInit, OnDestroy } from '@angular/core';

import { VendaService } from './services/venda.service';
import { SocketIoService } from './services/socket-io.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {


  totalVendas = 0;

  constructor(
    private vendaService: VendaService,
    private socketService: SocketIoService) { }

  ngOnInit(): void {
    this.buscaQuantidadedeVendas();
    this.ouveNotificacaoVenda();
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

    this.socketService.listenerObserver.subscribe(data => {
      if (data) {
        console.log(data);
        this.totalVendas = this.totalVendas + 1;
      }
    });
  }

  ngOnDestroy(): void {
    this.socketService.desconecta();
  }


}
