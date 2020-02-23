package br.com.loja.produtor.domain.mapper;

import br.com.loja.produtor.domain.Venda;
import br.com.loja.produtor.domain.dto.VendaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendaMapper extends AbstractMapper<Venda, VendaDTO> {

}
