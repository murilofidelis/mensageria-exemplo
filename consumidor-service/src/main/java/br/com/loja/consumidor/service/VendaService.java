package br.com.loja.consumidor.service;

import br.com.loja.consumidor.domain.dto.VendaDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface VendaService {

    List<VendaDTO> getAll();

    Long getQuantiddeVendas(Integer idUsuario);

    SseEmitter subscribeVenda(Integer id);

    void unSubscribeVenda(Integer id);

    void logInfoConexoes();
}
