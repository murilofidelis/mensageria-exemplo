package br.com.loja.consumidor.service;

import br.com.loja.consumidor.domain.dto.VendaDTO;

import java.util.List;

public interface VendaService {

    List<VendaDTO> getAll();

    Long getQuantiddeVendas();

}
