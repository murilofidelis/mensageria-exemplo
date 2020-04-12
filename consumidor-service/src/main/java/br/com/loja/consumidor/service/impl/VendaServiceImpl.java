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
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendaServiceImpl implements VendaService {

    @Qualifier("taskExecutor")
    private final TaskExecutor taskExecutor;
    private final VendaMapper mapper;
    private final VendaRepository repository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static HashMap<Integer, SseEmitter> userEmitters = new HashMap<>();

    private static final Long NUM_MAXIMO_TENTATIVAS = 2L;

    @Override
    public List<VendaDTO> getAll() {
        return mapper.listDTO(repository.findAll());
    }

    @Override
    public Long getQuantiddeVendas(final Integer idUsuario) {
        return repository.getQuantidade(idUsuario);
    }

    /**
     * Esculta os eventos da fila e salva na base.
     *
     * @param dto
     */
    @Transactional
    @StreamListener(Channels.VENDA_CHANNEL)
    @SendTo(Channels.VENDA_COD_CHANNEL)
    public VendaProcessadaDTO getVendaFila(VendaDTO dto, @Header(name = "x-death", required = false) Map<?, ?> death) {

        log.info("RECEBENDO: {}", dto);

        if (death != null && death.get("count") != null) {
            Long count = (Long) death.get("count");
            if (count >= NUM_MAXIMO_TENTATIVAS) {
                throw new ImmediateAcknowledgeAmqpException("NÚMERO MÁXIMO DE TENTATIVAS ATINGIDO: " + NUM_MAXIMO_TENTATIVAS);
            }
        }

        if (Objects.nonNull(dto)) {
            VendaDTO vendaSalvaDto = mapper.toDTO(repository.saveAndFlush(mapper.toEntity(dto)));
            VendaProcessadaDTO vendaProcessada = VendaProcessadaDTO.builder()
                    .codVendaProcessado(this.geraCodVenda())
                    .codProduto(dto.getCodProduto())
                    .dataProcessamento(LocalDateTime.now())
                    .build();

            this.notificaVendaViaSSE(vendaSalvaDto);
            //this.notificaVendaViaWebSocket(vendaProcessada);
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
    public void notificaVendaViaWebSocket(VendaProcessadaDTO venda) {
        simpMessagingTemplate.convertAndSend("/topic/venda", venda);
    }

    /**
     * Registra um Event source por usuario
     *
     * @param userId
     * @return
     */
    @Override
    public SseEmitter subscribeVenda(final Integer userId) {

        SseEmitter emitter = new SseEmitter(0L);

        taskExecutor.execute(() -> {

            emitter.onCompletion(() -> removeEmitterByUserId(userId));

            emitter.onTimeout(() -> removeEmitterByUserId(userId));

            emitter.onError(erro -> removeEmitterByUserId(userId));

            userEmitters.put(userId, emitter);

        });
        return emitter;
    }

    @Override
    public void unSubscribeVenda(final Integer idUser) {
        if (userEmitters.containsKey(idUser)) {
            userEmitters.get(idUser).complete();
            log.info("CONEXAO ENCERRADA, USER {}", idUser);
            log.info("CONEXÕES ATIVAS: {}", userEmitters.size());
        }
    }

    private void removeEmitterByUserId(final Integer userId) {
        userEmitters.remove(userId);
    }

    @Override
    public void logInfoConexoes() {
        if (!userEmitters.isEmpty()) {
            userEmitters.forEach((id, sseEmitter) -> log.info("ID USUARIO: {}", id));
        }
        log.info("TOTAL CONEXÕES REGISTRADAS: {}", userEmitters.size());
    }


    private void notificaVendaViaSSE(VendaDTO venda) {

        if (Objects.isNull(venda) || Objects.isNull(venda.getCodUsuario())) {
            return;
        }

        Integer idUser = venda.getCodUsuario();

        if (userEmitters.containsKey(idUser)) {
            try {
                SseEmitter emitter = userEmitters.get(idUser);
                emitter.send(venda, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                this.removeEmitterByUserId(idUser);
                log.error("ERRO AO NOTIFICAR VENDA: {}", e.getMessage());
            }
        }
    }
}
