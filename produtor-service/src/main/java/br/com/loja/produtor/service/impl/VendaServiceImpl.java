package br.com.loja.produtor.service.impl;

import br.com.loja.produtor.domain.Venda;
import br.com.loja.produtor.domain.dto.VendaDTO;
import br.com.loja.produtor.domain.mapper.VendaMapper;
import br.com.loja.produtor.exception.AppException;
import br.com.loja.produtor.repository.VendaRepository;
import br.com.loja.produtor.service.VendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendaServiceImpl implements VendaService {

    private final VendaMapper mapper;
    private final VendaRepository repository;

    @Override
    public List<VendaDTO> realizaVenda(VendaDTO dto) {
        log.info("RECEBENDO: {}", dto);
        if (Objects.nonNull(dto)) {
            List<VendaDTO> vendas = new ArrayList<>();
            if (Objects.nonNull(dto.getQuantidade())) {
                for (int i = 0; i <= dto.getQuantidade(); i++) {
                    Venda vendaSalva = repository.save(mapper.toEntity(dto));
                    vendas.add(mapper.toDTO(vendaSalva));
                }
            }
            return vendas;
        } else {
            throw new AppException("Dados da venda não informados");
        }

    }

}
