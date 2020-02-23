package br.com.loja.produtor.web.rest;

import br.com.loja.produtor.domain.dto.VendaDTO;
import br.com.loja.produtor.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("venda")
@RequiredArgsConstructor
public class VendaResource {

    private final VendaService service;

    @PostMapping
    public ResponseEntity<List<VendaDTO>> realizaVenda(@RequestBody VendaDTO dto) {
        List<VendaDTO> vendas = service.realizaVenda(dto);
        return new ResponseEntity<>(vendas, HttpStatus.CREATED);
    }
}
