package br.com.zupacademy.giovanna.proposta.carteira;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String idSistemaExterno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarteira tipo;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(String idSistemaExterno,
                    TipoCarteira tipo, Cartao cartao) {
        this.idSistemaExterno = idSistemaExterno;
        this.tipo = tipo;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public TipoCarteira getTipo() {
        return tipo;
    }
}
