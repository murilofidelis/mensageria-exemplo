package br.com.loja.consumidor.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class VendaDTO implements Serializable {

    private String codProduto;

    private String desProduto;

    private String marca;

    private BigDecimal preco;
}
