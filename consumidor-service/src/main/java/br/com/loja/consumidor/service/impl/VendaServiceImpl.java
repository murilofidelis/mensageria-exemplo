package br.com.loja.consumidor.service.impl;

import br.com.loja.consumidor.amq.source.Channels;
import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.domain.dto.VendaProcessadaDTO;
import br.com.loja.consumidor.domain.mapper.VendaMapper;
import br.com.loja.consumidor.exception.AppException;
import br.com.loja.consumidor.repository.VendaRepository;
import br.com.loja.consumidor.service.VendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendaServiceImpl implements VendaService {

    private final VendaMapper mapper;
    private final VendaRepository repository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public List<VendaDTO> getAll() {
        return mapper.listDTO(repository.findAll());
    }

    @Override
    public Long getQuantiddeVendas() {
        return repository.getQuantidade();
    }

    /**
     * Esculta os eventos da fila e salva na base.
     *
     * @param dto
     */
    @Transactional
    @StreamListener(Channels.VENDA_CHANNEL)
    @SendTo(Channels.VENDA_COD_CHANNEL)
    public VendaProcessadaDTO getVendaFila(VendaDTO dto) {
        log.info("RECEBENDO: {}", dto);
        if (Objects.nonNull(dto)) {
            repository.save(mapper.toEntity(dto));
            VendaProcessadaDTO vendaProcessada = new VendaProcessadaDTO();
            vendaProcessada.setCodVendaProcessado(this.geraCodVenda());
            vendaProcessada.setCodProduto(dto.getCodProduto());
            vendaProcessada.setDataProcessamento(LocalDateTime.now());
            this.notificaVendaWebSocket(vendaProcessada);
            return vendaProcessada;
        } else {
            throw new AppException("Dados da venda não informados");
        }
    }

    private String geraCodVenda() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        return new Random().ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

    /**
     * envia as vendas processadas para o websocket
     *
     * @param venda
     */
    public void notificaVendaWebSocket(VendaProcessadaDTO venda) {
        simpMessagingTemplate.convertAndSend("/topic/venda", venda);
    }

}
