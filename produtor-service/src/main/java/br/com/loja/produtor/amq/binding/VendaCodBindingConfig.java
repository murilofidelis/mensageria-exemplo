package br.com.loja.produtor.amq.binding;


import br.com.loja.produtor.amq.source.VendaCodSource;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(VendaCodSource.class)
public class VendaCodBindingConfig {
}
