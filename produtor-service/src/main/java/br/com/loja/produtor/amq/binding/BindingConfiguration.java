package br.com.loja.produtor.amq.binding;

import br.com.loja.produtor.amq.source.VendaSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(VendaSource.class)
public class BindingConfiguration {
}
