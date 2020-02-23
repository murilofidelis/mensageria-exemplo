package br.com.loja.produtor.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class VendaDTO implements Serializable {

    private Long codigo;

    private String desProduto;

    private String marca;

    private BigDecimal preco;

    private Integer quantidade;
}
