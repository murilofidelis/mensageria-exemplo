package br.com.loja.consumidor.web.rest;

import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("venda")
@RequiredArgsConstructor
public class VendaResource {

    private final VendaService service;

    @PostMapping("/recebe")
    public ResponseEntity<VendaDTO> salvaVenda(@RequestBody VendaDTO dto) {
        VendaDTO venda = service.salvaVenda(dto);
        return new ResponseEntity<>(venda, HttpStatus.CREATED);
    }
}
