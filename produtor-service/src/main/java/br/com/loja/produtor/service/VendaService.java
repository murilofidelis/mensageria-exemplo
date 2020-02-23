package br.com.loja.produtor.service;

import br.com.loja.produtor.domain.dto.VendaDTO;

import java.util.List;

public interface VendaService {
    List<VendaDTO> realizaVenda(VendaDTO dto);
}
