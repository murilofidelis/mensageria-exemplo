package br.com.loja.consumidor.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tab_vendas", schema = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long codigo;

    @Column(name = "cod_produto")
    private String codProduto;

    @NotNull
    @Size(max = 250)
    @Column(name = "des_produto")
    private String desProduto;

    @Column(name = "des_marca")
    private String marca;

    @Column(name = "preco")
    private BigDecimal preco;


}
