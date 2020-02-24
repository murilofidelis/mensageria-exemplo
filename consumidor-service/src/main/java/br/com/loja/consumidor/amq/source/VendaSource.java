package br.com.loja.consumidor.amq.source;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface VendaSource {

    String VENDA_CHANNEL = "vendaChannel";

    @Input(VENDA_CHANNEL)
    SubscribableChannel inputChannel();
}
