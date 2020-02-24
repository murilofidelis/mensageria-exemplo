package br.com.loja.produtor.amq.source;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface VendaCodSource {

    @Input(Channels.VENDA_COD_CHANNEL)
    SubscribableChannel inputChannel();
}
