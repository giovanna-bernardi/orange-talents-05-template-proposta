package br.com.zupacademy.giovanna.proposta.bloqueio;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    public enum StatusPedido {
        ACEITO, AGUARDANDO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String ipClient;

    @NotBlank
    private String userAgentClient;

    private LocalDateTime instante = LocalDateTime.now();

    @ManyToOne @NotNull
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String ipClient, String userAgentClient, Cartao cartao) {
        this.ipClient = ipClient;
        this.userAgentClient = userAgentClient;
        this.cartao = cartao;
    }
}
