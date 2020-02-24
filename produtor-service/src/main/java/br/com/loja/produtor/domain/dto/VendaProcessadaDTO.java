package br.com.loja.produtor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VendaProcessadaDTO implements Serializable {

    private String codVendaProcessado;
    private String codProduto;
    private LocalDateTime dataProcessamento;

}
