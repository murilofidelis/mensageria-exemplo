package br.com.loja.consumidor.web.rest;

import br.com.loja.consumidor.domain.dto.VendaDTO;
import br.com.loja.consumidor.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    @GetMapping("/quantidade/{idUsuario}")
    public ResponseEntity<Long> getQuantiddeVendas(@PathVariable Integer idUsuario) {
        return new ResponseEntity<>(service.getQuantiddeVendas(idUsuario), HttpStatus.OK);
    }

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Integer id) {
        return service.subscribeVenda(id);
    }

    @DeleteMapping("/unsubscribe/{id}")
    public void unSubscribe(@PathVariable Integer id) {
        service.unSubscribeVenda(id);
    }

}
