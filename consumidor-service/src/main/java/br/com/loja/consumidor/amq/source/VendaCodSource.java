package br.com.loja.consumidor.amq.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface VendaCodSource {

    @Output(Channels.VENDA_COD_CHANNEL)
    MessageChannel inputChannel();
}
