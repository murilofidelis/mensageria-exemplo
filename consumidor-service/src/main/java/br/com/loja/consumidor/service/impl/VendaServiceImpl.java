package br.com.loja.consumidor.service.impl;

import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.domain.mapper.VendaMapper;
import br.com.loja.consumidor.exception.AppException;
import br.com.loja.consumidor.service.VendaService;
import br.com.loja.consumidor.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendaServiceImpl implements VendaService {

    private final VendaMapper mapper;
    private final VendaRepository repository;

    @Override
    @Transactional
    public VendaDTO salvaVenda(VendaDTO dto) {
        log.info("RECEBENDO: {}", dto);
        if (Objects.nonNull(dto)) {
            return mapper.toDTO(repository.save(mapper.toEntity(dto)));
        } else {
            throw new AppException("Dados da venda não informados");
        }

    }

}
