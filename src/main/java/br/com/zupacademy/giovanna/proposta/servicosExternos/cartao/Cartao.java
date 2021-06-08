package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String numeroCartao;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataEmissao;

    @NotBlank
    @Column(nullable = false)
    private String nomeTitular;

    @NotNull
    @OneToOne
    private Proposta proposta;
//    private List<Bloqueio> bloqueios;
//    private Vencimento vencimento;


    @Deprecated
    public Cartao() {
    }

    public Cartao(String numeroCartao,
                  LocalDateTime dataEmissao,
                  String nomeTitular, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.dataEmissao = dataEmissao;
        this.nomeTitular = nomeTitular;
        this.proposta = proposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }
}
