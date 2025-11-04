package br.com.cadastrodeassociados.associado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    List<Associado> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Associado a WHERE " +
            "(:bairro IS NULL OR a.endereco LIKE %:bairro%) AND " +
            "(:escolaridade IS NULL OR a.escolaridade = :escolaridade)")
    List<Associado> findWithFilters(@Param("bairro") String bairro,
                                    @Param("escolaridade") String escolaridade);
}

