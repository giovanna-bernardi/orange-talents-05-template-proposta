package br.com.zupacademy.giovanna.proposta.cartao;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;
import br.com.zupacademy.giovanna.proposta.biometria.Biometria;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<Biometria> biometrias;

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

    public void associaBiometrias(List<Biometria> biometrias) {
        biometrias.forEach(b -> b.setDataAssociacao(LocalDateTime.now()));
        this.biometrias = new ArrayList<>();
        this.biometrias.addAll(biometrias);
    }


    public Long getId() {
        return id;
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
