package br.com.clubedaterceiraidade.cadastrodeassociado.carteira;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteirinhaRepository extends JpaRepository<Carteirinha, Long> {
    Optional<Carteirinha> findByNumeroCarteirinha(String numeroCarteirinha);
}