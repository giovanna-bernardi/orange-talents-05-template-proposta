package br.com.zupacademy.giovanna.proposta.cartao;

import br.com.zupacademy.giovanna.proposta.bloqueio.Bloqueio;
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

    enum StatusCartao {
        ATIVO, BLOQUEADO
    }

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

    @Enumerated(EnumType.STRING)
    private StatusCartao status;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<Biometria> biometrias;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<Bloqueio> bloqueios = new ArrayList<>();


    @Deprecated
    public Cartao() {
    }

    public Cartao(String numeroCartao,
                  LocalDateTime dataEmissao,
                  String nomeTitular, Proposta proposta) {
        this.status = StatusCartao.ATIVO;
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

    public boolean estaBloqueado() {
        return status.equals(StatusCartao.BLOQUEADO);
    }

    public void bloqueia(String ipClient, String userAgentClient){
        this.status = StatusCartao.BLOQUEADO;
        adicionaBloqueio(new Bloqueio(ipClient, userAgentClient, this));
    }

    private void adicionaBloqueio(Bloqueio bloqueio) {
        this.bloqueios.add(bloqueio);
    }

}
