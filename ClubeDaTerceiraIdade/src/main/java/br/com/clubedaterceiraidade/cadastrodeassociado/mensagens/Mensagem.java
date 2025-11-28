package br.com.clubedaterceiraidade.cadastrodeassociado.mensagens;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String conteudo;

    private String destinatarios;

    private LocalDateTime dataEnvio = LocalDateTime.now();
}
