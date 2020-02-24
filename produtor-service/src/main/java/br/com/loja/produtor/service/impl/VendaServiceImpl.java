package br.com.loja.produtor.service.impl;

import br.com.loja.produtor.amq.source.Channels;
import br.com.loja.produtor.amq.source.VendaCodSource;
import br.com.loja.produtor.amq.source.VendaSource;
import br.com.loja.produtor.domain.Venda;
import br.com.loja.produtor.domain.dto.VendaDTO;
import br.com.loja.produtor.domain.dto.VendaProcessadaDTO;
import br.com.loja.produtor.domain.mapper.VendaMapper;
import br.com.loja.produtor.exception.AppException;
import br.com.loja.produtor.repository.VendaRepository;
import br.com.loja.produtor.service.VendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
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

    private final VendaSource source;
    private final VendaMapper mapper;
    private final VendaRepository repository;

    @Override
    public List<VendaDTO> realizaVenda(VendaDTO dto) {
        if (Objects.nonNull(dto)) {
            List<VendaDTO> vendas = new ArrayList<>();
            if (Objects.nonNull(dto.getQuantidade())) {
                for (int i = 0; i < dto.getQuantidade(); ++i) {
                    Venda vendaSalva = repository.save(mapper.toEntity(dto));
                    VendaDTO vendaDTO = mapper.toDTO(vendaSalva);
                    this.enviaVendaParaFila(vendaDTO);
                    vendas.add(vendaDTO);
                }
            }
            return vendas;
        } else {
            throw new AppException("Dados da venda não informados");
        }

    }

    private void enviaVendaParaFila(VendaDTO venda) {
        source.outputChannel()
                .send(MessageBuilder.withPayload(venda)
                        .setHeader("token", "xyx")
                        .build());
    }

    @Transactional
    @StreamListener(Channels.VENDA_COD_CHANNEL)
    public void recebeVendaProcessaada(VendaProcessadaDTO dto) {
        log.info("VENDA PROCESSADA, {}", dto);
        if (Objects.nonNull(dto) && Objects.nonNull(dto.getCodProduto())) {
            List<Venda> vendas = repository.findByCodProduto(dto.getCodProduto());
            vendas.forEach(venda -> {
                venda.setDataProcessamento(dto.getDataProcessamento());
                venda.setCodProcessamento(dto.getCodVendaProcessado());
                repository.save(venda);
            });
        }
    }

}
