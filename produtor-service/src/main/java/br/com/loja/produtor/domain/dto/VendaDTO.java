package br.com.loja.produtor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO implements Serializable {

    private Long id;

    private String codProduto;

    private String desProduto;

    private String marca;

    private BigDecimal preco;

    private Integer quantidade;

    private Integer codUsuario;
}
