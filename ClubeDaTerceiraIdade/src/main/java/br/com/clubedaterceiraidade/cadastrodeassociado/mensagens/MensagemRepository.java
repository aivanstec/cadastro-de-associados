package br.com.clubedaterceiraidade.cadastrodeassociado.mensagens;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}