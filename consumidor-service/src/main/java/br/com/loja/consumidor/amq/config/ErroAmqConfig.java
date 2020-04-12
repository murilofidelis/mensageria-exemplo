package br.com.loja.consumidor.amq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErroAmqConfig {

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder
                .directExchange("erro.exchange")
                .durable(true)
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(erroQueue())
                .to(directExchange())
                .with("erro.key")
                .noargs();
    }

    @Bean
    public Queue erroQueue() {
        return QueueBuilder
                .durable("erro.queue")
                .build();
    }
}
