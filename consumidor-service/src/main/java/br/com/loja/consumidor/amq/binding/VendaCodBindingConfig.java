package br.com.loja.consumidor.amq.binding;

import br.com.loja.consumidor.amq.source.VendaCodSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(VendaCodSource.class)
public class VendaCodBindingConfig {
}
