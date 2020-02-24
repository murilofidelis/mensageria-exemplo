package br.com.loja.consumidor.amq.binding;


import br.com.loja.consumidor.amq.source.VendaSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(VendaSource.class)
public class BindingConfiguration {
}
