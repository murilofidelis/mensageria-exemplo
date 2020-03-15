package br.com.loja.consumidor.repository;

import br.com.loja.consumidor.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT count(v.codigo) FROM Venda v WHERE v.codUsuario = :idUsuario")
    Long getQuantidade(@Param("idUsuario") Integer id);
}
