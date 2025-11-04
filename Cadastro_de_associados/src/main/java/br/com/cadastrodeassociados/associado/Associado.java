package br.com.cadastrodeassociados.associado;

import br.com.cadastrodeassociados.cidade.Cidade;
import br.com.cadastrodeassociados.carteira.Carteirinha;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String rg;
    private String dataNascimento;

    private String telefone;
    private String escolaridade;
    private String email;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    private String endereco;

    @OneToOne(mappedBy = "associado", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carteirinha carteirinha;
}
