package br.com.loja.produtor.amq.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface VendaSource {

    @Output(Channels.VENDA_CHANNEL)
    MessageChannel outputChannel();
}
