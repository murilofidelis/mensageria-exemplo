package br.com.loja.consumidor.service.impl;

import br.com.loja.consumidor.amq.source.VendaSource;
import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.domain.mapper.VendaMapper;
import br.com.loja.consumidor.exception.AppException;
import br.com.loja.consumidor.repository.VendaRepository;
import br.com.loja.consumidor.service.VendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<VendaDTO> getAll() {
        return mapper.listDTO(repository.findAll());
    }

    /**
     * Esculta os eventos da fila e salva na base.
     *
     * @param dto
     */
    @Transactional
    @StreamListener(VendaSource.VENDA_CHANNEL)
    public void getVendaFila(VendaDTO dto) {
        log.info("RECEBENDO: {}", dto);
        if (Objects.nonNull(dto)) {
            repository.save(mapper.toEntity(dto));
        } else {
            throw new AppException("Dados da venda não informados");
        }
    }

}
