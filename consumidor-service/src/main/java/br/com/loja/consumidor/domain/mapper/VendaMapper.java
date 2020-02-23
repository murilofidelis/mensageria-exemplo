package br.com.loja.consumidor.domain.mapper;

import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.domain.Venda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendaMapper extends AbstractMapper<Venda, VendaDTO> {

}
