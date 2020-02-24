package br.com.loja.consumidor.web.rest;

import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("venda")
@RequiredArgsConstructor
public class VendaResource {

    private final VendaService service;

    @GetMapping
    public ResponseEntity<List<VendaDTO>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

}
