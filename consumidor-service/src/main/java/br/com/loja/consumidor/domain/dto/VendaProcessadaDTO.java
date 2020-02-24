package br.com.loja.consumidor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaProcessadaDTO implements Serializable {

    private String codVendaProcessado;
    private LocalDateTime dataProcessamento;
    private String codProduto;

}
