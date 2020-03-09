package br.com.loja.consumidor.task;

import br.com.loja.consumidor.service.VendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificaVendaTask {

    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Autowired
    private VendaService service;

    @Scheduled(cron = "*/10 * * * * *", zone = TIME_ZONE)
    public void notificaVenda() {
        service.notificaVenda();
    }
}
